package br.com.mioto.cloud.bo.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.bo.CriticalityBO;
import br.com.mioto.cloud.bo.ResponseTimeBO;
import br.com.mioto.cloud.dao.ResponseTimeDAO;
import br.com.mioto.cloud.vo.CriticalityVO;
import br.com.mioto.cloud.vo.ResponseTime;

/**
 * The Class ResponseTimeBOImpl.
 */
@Component
public class ResponseTimeBOImpl implements ResponseTimeBO {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ResponseTimeBOImpl.class);

    /** The response time DAO. */
    @Autowired
    private ResponseTimeDAO responseTimeDAO;

    /** The criticality BO. */
    @Autowired
    private CriticalityBO criticalityBO;

    /**
     * Gets the all response times.
     *
     * @return the all response times
     * @throws SQLException the SQL exception
     */
    @Override
    public List<ResponseTime> getAllResponseTimes() throws SQLException {

        final List<ResponseTime> responseTimeList = new ArrayList<ResponseTime>();
        final Map<String, Double> responseTimeMap = responseTimeDAO.getAverageTime("1");
        final Map<String, Double> responseTimeMapSevenDays = responseTimeDAO.getAverageTime("7");
        final Map<String, Double> responseTimeMap30Days = responseTimeDAO.getAverageTime("30");

        for (final Entry<String, Double> entry : responseTimeMap.entrySet()) {
            final ResponseTime responseTime = new ResponseTime();
            responseTime.setAverage(responseTimeMap.get(entry.getKey()));
            responseTime.setAverageLastSevenDays(responseTimeMapSevenDays.get(entry.getKey()));
            responseTime.setAverageLastThirtyDays(responseTimeMap30Days.get(entry.getKey()));
            responseTimeList.add(responseTime);
        }

        Collections.sort(responseTimeList, Collections.reverseOrder());

        if(responseTimeList.size() > 0) {
            final ResponseTime responseTime = responseTimeList.get(0);
            checkCriticality(responseTime);
        }

        return responseTimeList;

    }

    /**
     * Check criticality.
     *
     * @param responseTime the response time
     * @throws SQLException the SQL exception
     */
    private void checkCriticality(final ResponseTime responseTime) throws SQLException {
        final Integer criticalityFactor = this.calculateCriticalityFactor(responseTime.getAverage());
        final CriticalityVO vo = criticalityBO.populate(responseTime.getProject(), criticalityFactor, responseTime.getAverage().toString(), "response-time");
        log.info("Criticality: {}", vo);
        criticalityBO.saveCriticality(vo);
    }

    /**
     * Calculate criticality factor.
     *
     * @param value the value
     * @return the integer
     */
    public Integer calculateCriticalityFactor(Double value) {

        if((value > 0) && (value <= 0.5)) {
            return 1;

        }else if((value > 0.5) && (value <= 1.0)) {
            return 2;

        }else if((value > 1.0) && (value <= 1.5)) {
            return 3;

        }else if((value > 1.5) && (value <= 2.0)) {
            return 4;
        }

        return 5;
    }




}

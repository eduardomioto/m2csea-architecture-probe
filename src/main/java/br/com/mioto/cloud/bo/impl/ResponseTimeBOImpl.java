package br.com.mioto.cloud.bo.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.bo.ResponseTimeBO;
import br.com.mioto.cloud.dao.ResponseTimeDAO;
import br.com.mioto.cloud.vo.ResponseTime;

@Component
public class ResponseTimeBOImpl implements ResponseTimeBO {

    private static final Logger log = LoggerFactory.getLogger(ResponseTimeBOImpl.class);

    @Autowired
    private ResponseTimeDAO ResponseTimeDAO;

    @Override
    public List<ResponseTime> getAllResponseTimes() throws SQLException {

        final List<ResponseTime> responseTimeList = ResponseTimeDAO.getAverageTime();

//        final List<ResponseTime> meanTimeList = ResponseTimeDAO.getMeanTime();
//
//        for (final ResponseTime responseTime : responseTimeList) {
//
//            for (final ResponseTime responseTime2 : meanTimeList) {
//                if(responseTime.getProject().equals(responseTime2.getProject())) {
//                    responseTime.setMean(responseTime2.getMean());
//                }
//            }
//        }

        Collections.sort(responseTimeList, Collections.reverseOrder());

        return responseTimeList;

    }




}

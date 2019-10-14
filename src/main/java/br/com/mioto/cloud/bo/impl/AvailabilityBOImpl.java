package br.com.mioto.cloud.bo.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.bo.AvailabilityBO;
import br.com.mioto.cloud.bo.CriticalityBO;
import br.com.mioto.cloud.commons.HttpCommons;
import br.com.mioto.cloud.integration.ConsulIntegration;
import br.com.mioto.cloud.vo.ConsulHealthcheck;
import br.com.mioto.cloud.vo.CriticalityVO;

/**
 * The Class AvailabilityBOImpl.
 */
@Component
public class AvailabilityBOImpl implements AvailabilityBO {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(AvailabilityBOImpl.class);

    /** The criticality BO. */
    @Autowired
    private CriticalityBO criticalityBO;

    /**
     * Gets the all resource comsuption.
     *
     * @return the all resource comsuption
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws JSONException the JSON exception
     * @throws SQLException the SQL exception
     */
    @Override
    public List<ConsulHealthcheck> getAllHealthchecks() throws IOException, ParseException, org.apache.http.ParseException, JSONException, java.text.ParseException, SQLException {

        final List<ConsulHealthcheck> healthcheckList = new ArrayList<ConsulHealthcheck>();

        final String microserviceListResponse = HttpCommons.callHttp(ConsulIntegration.treatConsulURL(),"GET");

        final JSONParser parser = new JSONParser();
        final org.json.simple.JSONArray json = (org.json.simple.JSONArray) parser.parse(microserviceListResponse);

        for (int i = 0; i < json.size(); i++) {
            final org.json.simple.JSONObject jsonObject = (JSONObject) json.get(i);

            final String name = (String) jsonObject.get("Name");
            final Long checksPassing =  (Long) jsonObject.get("ChecksPassing");
            final Long checksWarning =   (Long) jsonObject.get("ChecksWarning");
            final Long checksCritical =   (Long) jsonObject.get("ChecksCritical");

            if(name.contains("microservice-")) {
                final ConsulHealthcheck consulHealthcheck = new ConsulHealthcheck();
                consulHealthcheck.setChecksCritical(checksCritical);
                consulHealthcheck.setChecksPassing(checksPassing);
                consulHealthcheck.setChecksWarning(checksWarning);
                consulHealthcheck.setName(name);
                healthcheckList.add(consulHealthcheck);
            }

        }

        for (final ConsulHealthcheck healthcheck : healthcheckList) {
            log.info(healthcheck.toString());
        }

        Collections.sort(healthcheckList, Collections.reverseOrder());

        if(healthcheckList.size() > 0) {
            final ConsulHealthcheck healthcheck = healthcheckList.get(0);
            checkCriticality(healthcheck);
        }

        return healthcheckList;
    }



    /**
     * Check criticality.
     *
     * @param healthcheck the healthcheck
     * @throws SQLException the SQL exception
     */
    private void checkCriticality(final ConsulHealthcheck healthcheck) throws SQLException {

        final long critical = healthcheck.getChecksCritical();
        healthcheck.getChecksPassing();
        healthcheck.getChecksWarning();

        final Integer criticalityFactor = this.calculateCriticalityFactor( critical);
        final CriticalityVO vo = criticalityBO.populate(healthcheck.getName(), criticalityFactor, String.valueOf(critical), "availability");
        criticalityBO.saveCriticality(vo);
    }

    /**
     * Calculate criticality factor.
     *
     * @param value the value
     * @return the integer
     */
    public Integer calculateCriticalityFactor(long value) {

        if((value > 0) && (value <= 1)) {
            return 1;

        }else if((value > 1) && (value <= 2)) {
            return 2;

        }else if((value > 2) && (value <= 3)) {
            return 3;

        }else if((value > 3) && (value <= 4)) {
            return 4;
        }

        return 5;
    }

}

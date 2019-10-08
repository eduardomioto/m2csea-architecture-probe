package br.com.mioto.cloud.bo.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.bo.ConsulBO;
import br.com.mioto.cloud.commons.HttpCommons;
import br.com.mioto.cloud.integration.ConsulIntegration;
import br.com.mioto.cloud.vo.ConsulHealthcheck;

@Component
public class ConsulBOImpl implements ConsulBO {

    private static final Logger log = LoggerFactory.getLogger(ConsulBOImpl.class);

    /**
     * Gets the all resource comsuption.
     *
     * @return the all resource comsuption
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws JSONException the JSON exception
     */
    @Override
    public List<ConsulHealthcheck> getAllHealthchecks() throws IOException, ParseException, org.apache.http.ParseException, JSONException, java.text.ParseException {

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

        return healthcheckList;
    }



}

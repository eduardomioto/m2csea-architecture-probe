package br.com.mioto.cloud.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.commons.HttpCommons;
import br.com.mioto.cloud.vo.ConsulHealthcheck;

@Component
public class ConsulIntegration {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ConsulIntegration.class);

    public static String treatConsulURL(){
        final StringBuilder consulURL = new StringBuilder("http://consul:8500/v1/internal/ui/services?dc=dc1&token=");
        log.info("consulURL: {}", consulURL);
        return consulURL.toString();
    }

    public static void main(String[] args) {

        try {

            final List<ConsulHealthcheck> computationalResourcesList = new ArrayList<ConsulHealthcheck>();

            final String microserviceListResponse = HttpCommons.callHttp(ConsulIntegration.treatConsulURL(),"GET");

            final JSONParser parser = new JSONParser();
            final org.json.simple.JSONArray json = (org.json.simple.JSONArray) parser.parse(microserviceListResponse);

            for (int i = 0; i < json.size(); i++) {
                final org.json.simple.JSONObject jsonObject = (JSONObject) json.get(i);

                final String name = (String) jsonObject.get("Name");
                final Long checksPassing =  (Long) jsonObject.get("ChecksPassing");
                final Long checksWarning =   (Long) jsonObject.get("ChecksWarning");
                final Long checksCritical =   (Long) jsonObject.get("ChecksCritical");

                final ConsulHealthcheck consulHealthcheck = new ConsulHealthcheck();
                consulHealthcheck.setChecksCritical(checksCritical);
                consulHealthcheck.setChecksPassing(checksPassing);
                consulHealthcheck.setChecksWarning(checksWarning);
                consulHealthcheck.setName(name);
                computationalResourcesList.add(consulHealthcheck);
            }

            for (final ConsulHealthcheck computationalResources : computationalResourcesList) {
                log.info(computationalResources.toString());
            }


        } catch (ParseException | JSONException  | org.json.simple.parser.ParseException | IOException e) {
            log.error("Error: ", e);
        }
    }

    public static List<String> getSkipList() {
        final List<String> skipMicroservices = new ArrayList<String>();
        skipMicroservices.add("consul");
        return skipMicroservices;
    }



}

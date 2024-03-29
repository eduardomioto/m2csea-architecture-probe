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
import br.com.mioto.cloud.vo.ConsulStatus;

@Component
public class ConsulIntegration {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ConsulIntegration.class);

    public static String treatConsulURL(String status){
        final StringBuilder consulURL =  new StringBuilder("http://localhost:8500/v1/health/state/" + status);
        log.info("consulURL: {}", consulURL);
        return consulURL.toString();
    }


    public static void main(String[] args) {

        try {

            final List<ConsulStatus> statusList = new ArrayList<ConsulStatus>();

            final String microserviceListResponse = HttpCommons.callHttp(ConsulIntegration.treatConsulURL("passing"),"GET");

            final JSONParser parser = new JSONParser();
            final org.json.simple.JSONArray json = (org.json.simple.JSONArray) parser.parse(microserviceListResponse);

            for (int i = 0; i < json.size(); i++) {
                final org.json.simple.JSONObject jsonObject = (JSONObject) json.get(i);

                String name = (String) jsonObject.get("Name");
                name = name.replace("Service '", "");
                name = name.replace("' check", "");

                final String status =  (String) jsonObject.get("Status");

                final ConsulStatus consulHealthcheck = new ConsulStatus();
                consulHealthcheck.setName(name);
                consulHealthcheck.setStatus(status);

                statusList.add(consulHealthcheck);
            }

            for (final ConsulStatus computationalResources : statusList) {
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

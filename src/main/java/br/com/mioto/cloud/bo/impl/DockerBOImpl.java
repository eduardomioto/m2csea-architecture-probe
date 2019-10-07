package br.com.mioto.cloud.bo.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.bo.DockerBO;
import br.com.mioto.cloud.commons.HttpCommons;
import br.com.mioto.cloud.integration.DockerIntegration;
import br.com.mioto.cloud.vo.ComputationalResources;

@Component
public class DockerBOImpl implements DockerBO {

    private static final Logger log = LoggerFactory.getLogger(DockerBOImpl.class);

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
    public List<ComputationalResources> getAllResourceComsuption() throws IOException, ParseException, org.apache.http.ParseException, JSONException, java.text.ParseException {

        final List<ComputationalResources> computationalResourcesList = new ArrayList<ComputationalResources>();

        final String microserviceListResponse = HttpCommons.callHttp(DockerIntegration.treatDockerGetContainerURL("running"), "GET");

        final JSONParser parser = new JSONParser();
        final org.json.simple.JSONArray json = (org.json.simple.JSONArray) parser.parse(microserviceListResponse);

        final List<String> microserviceList = new ArrayList<String>();
        for (int i = 0; i < json.size(); i++) {
            final org.json.simple.JSONObject jsonObject = (JSONObject) json.get(i);

            final org.json.simple.JSONArray names = (JSONArray) jsonObject.get("Names");
            for (int j = 0; j < names.size(); j++) {
                final String microserviceNameRaw = (String) names.get(0);

                if(!DockerIntegration.getSkipList().contains(microserviceNameRaw)) {
                    final String microserviceName = microserviceNameRaw.replace("/", "");
                    microserviceList.add(microserviceName);
                }
            }
        }

        for (final String microservice : microserviceList) {
            final String response = HttpCommons.callHttp(DockerIntegration.treatDockerTopURL(microservice), "GET");
            final List<ComputationalResources> partialList = DockerIntegration.extractInfoFromContainerJSON(response, microservice);
            computationalResourcesList.addAll(partialList);
        }

        for (final ComputationalResources computationalResources : computationalResourcesList) {
            log.info(computationalResources.toString());
        }

        return computationalResourcesList;
    }

}

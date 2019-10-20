package br.com.mioto.cloud.bo.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.bo.CriticalityBO;
import br.com.mioto.cloud.bo.DockerBO;
import br.com.mioto.cloud.commons.HttpCommons;
import br.com.mioto.cloud.commons.ProbeUtils;
import br.com.mioto.cloud.integration.DockerIntegration;
import br.com.mioto.cloud.vo.ComputationalResources;
import br.com.mioto.cloud.vo.CriticalityVO;

/**
 * The Class DockerBOImpl.
 */
@Component
public class DockerBOImpl implements DockerBO {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(DockerBOImpl.class);

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
    public List<ComputationalResources> getAllResourceComsuption() throws IOException, ParseException, org.apache.http.ParseException, JSONException, java.text.ParseException, SQLException {

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
        Collections.sort(computationalResourcesList, Collections.reverseOrder());

        if(computationalResourcesList.size() > 0) {
            final ComputationalResources computationalResources = computationalResourcesList.get(0);
            checkCriticality(computationalResources);

        }


        return computationalResourcesList;
    }


    /**
     * Check criticality.
     *
     * @param computationalResources the computational resources
     * @throws SQLException the SQL exception
     */
    private void checkCriticality(final ComputationalResources computationalResources) throws SQLException {

        Integer criticalityFactor = 0;

        final Integer criticalityFactorCPU = this.calculateCriticalityFactor(computationalResources.getCpu());
        final Integer criticalityFactorRAM = this.calculateCriticalityFactor(computationalResources.getRam());

        if(criticalityFactorCPU > criticalityFactorRAM) {
            criticalityFactor = criticalityFactorCPU;
        }else {
            criticalityFactor = criticalityFactorRAM;
        }

        final String microservice = ProbeUtils.normalizeMicroserviceName(computationalResources.getMicroservice());

        final String value = "CPU: " + criticalityFactorCPU + " | RAM: " + criticalityFactorRAM;
        final CriticalityVO vo = criticalityBO.populate(microservice, criticalityFactor, value, "computational-resource-usage");
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

        if((value > 0) && (value <= 20)) {
            return 1;

        }else if((value > 20) && (value <= 40)) {
            return 2;

        }else if((value > 40) && (value <= 60)) {
            return 3;

        }else if((value > 60) && (value <= 80)) {
            return 4;
        }

        return 5;
    }


}

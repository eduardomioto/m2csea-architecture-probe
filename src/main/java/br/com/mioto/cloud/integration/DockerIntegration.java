package br.com.mioto.cloud.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.commons.HttpCommons;
import br.com.mioto.cloud.vo.ComputationalResources;

// https://docs.docker.com/engine/api/v1.24/#31-containers
/**
 * The Class OpenWeatherMapIntegration.
 */
@Component
public class DockerIntegration {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(DockerIntegration.class);

    //http://localhost:2376/v1.24/containers/architecture_probe/top?ps_args=aux
    public static String treatDockerTopURL(String container){
        final StringBuilder dockerTopURL = new StringBuilder("http://docker:2376/v1.24/containers/");
        dockerTopURL.append(container);
        dockerTopURL.append("/top?ps_args=aux");
        log.info("dockerURL: {}", dockerTopURL);
        return dockerTopURL.toString();
    }

    public static String treatDockerGetContainerURL(String status){
        final StringBuilder dockerGetAllContainersURL = new StringBuilder("http://localhost:2376/v1.24/containers/json?all=0&before=8dfafdbc3a40&size=1&filters={%22status%22:[%22");
        dockerGetAllContainersURL.append(status);
        dockerGetAllContainersURL.append("%22]}");
        log.info("dockerURL: {}", dockerGetAllContainersURL);
        return dockerGetAllContainersURL.toString();
    }

    public static void main(String[] args) {

        try {

            final String microserviceListResponse = HttpCommons.callHttp(DockerIntegration.treatDockerGetContainerURL("running"), "GET");

            final JSONParser parser = new JSONParser();
            final org.json.simple.JSONArray json = (org.json.simple.JSONArray) parser.parse(microserviceListResponse);

            final List<String> microserviceList = new ArrayList<String>();
            for (int i = 0; i < json.size(); i++) {
                final org.json.simple.JSONObject jsonObject = (JSONObject) json.get(i);

                final org.json.simple.JSONArray names = (JSONArray) jsonObject.get("Names");
                for (int j = 0; j < names.size(); j++) {
                    final String microserviceNameRaw = (String) names.get(0);

                    if(!getSkipList().contains(microserviceNameRaw)) {
                        final String microserviceName = microserviceNameRaw.replace("/", "");
                        microserviceList.add(microserviceName);
                    }
                }
            }

            final List<ComputationalResources> computationalResourcesList = new ArrayList<ComputationalResources>();
            for (final String microservice : microserviceList) {
                final String response = HttpCommons.callHttp(DockerIntegration.treatDockerTopURL(microservice), "GET");
                final List<ComputationalResources> partialList = extractInfoFromContainerJSON(response, microservice);
                computationalResourcesList.addAll(partialList);
            }

            for (final ComputationalResources computationalResources : computationalResourcesList) {
                System.out.println(computationalResources);
            }



        } catch (ParseException | JSONException | java.text.ParseException | org.json.simple.parser.ParseException | IOException e) {
            log.error("Error: ", e);
        }
    }

    public static List<String> getSkipList() {
        final List<String> skipMicroservices = new ArrayList<String>();
        skipMicroservices.add("/visualizer");
        skipMicroservices.add("/finder");
        skipMicroservices.add("/prometheus");
        skipMicroservices.add("/consul");
        skipMicroservices.add("/sonarqube");
        skipMicroservices.add("/zipkin");
        skipMicroservices.add("/postgres");
        skipMicroservices.add("/neo4j");
        skipMicroservices.add("/telegraf");
        skipMicroservices.add("/architecture_probe");
        return skipMicroservices;
    }

    public static List<ComputationalResources> extractInfoFromContainerJSON(String response, String microservice) throws ParseException, java.text.ParseException, org.json.simple.parser.ParseException, JSONException {

        final List<ComputationalResources> listInvididualDayResponse = new ArrayList<ComputationalResources>();

        final JSONParser parser = new JSONParser();
        final org.json.simple.JSONObject json = (org.json.simple.JSONObject) parser.parse(response);

        Double cpuUsagePercentage = 0.0;
        Double memoryUsagePercentage = 0.0;

        if(json.containsKey("Processes")) {
            final JSONArray list = (JSONArray) json.get("Processes");

            for (int i = 0; i < list.size(); i++) {

                final JSONArray resourceEntry = (JSONArray) list.get(i);

                //                "Titles": [
                //                           "USER",
                //                           "PID",
                //                           "%CPU",
                //                           "%MEM",
                //                           "VSZ",
                //                           "RSS",
                //                           "TTY",
                //                           "STAT",
                //                           "START",
                //                           "TIME",
                //                           "COMMAND"
                //                       ]
                for (int j = 0; j <= resourceEntry.size(); j++) {
                    if(j == 2) {
                        final String cpuStr = (String) resourceEntry.get(j);
                        cpuUsagePercentage+= Double.valueOf(cpuStr);
                    }
                    if(j == 3) {
                        final String ramStr = (String) resourceEntry.get(j);
                        memoryUsagePercentage+= Double.valueOf(ramStr);
                    }
                }

            }
            final ComputationalResources resourcesUsage = new ComputationalResources();
            resourcesUsage.setCpu(cpuUsagePercentage);
            //resourcesUsage.setIo(io);
            resourcesUsage.setMicroservice(microservice);
            //resourcesUsage.setNetwork(network);
            resourcesUsage.setRam(memoryUsagePercentage);

            listInvididualDayResponse.add(resourcesUsage);

        }

        return listInvididualDayResponse;
    }

}

package br.com.mioto.cloud.integration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.vo.ComputationalResources;
import br.com.mioto.cloud.vo.SonarIssues;

// https://docs.docker.com/engine/api/v1.24/#31-containers
/**
 * The Class OpenWeatherMapIntegration.
 */
@Component
public class DockerIntegration {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(DockerIntegration.class);

    /** The sdf. */
    private final SimpleDateFormat sdf = new SimpleDateFormat();

    //http://localhost:2376/v1.24/containers/architecture_probe/top?ps_args=aux
    public static String treatDockerTopURL(String container){
        final StringBuilder dockerTopURL = new StringBuilder("http://localhost:2376/v1.24/containers/");
        dockerTopURL.append(container);
        dockerTopURL.append("/top?ps_args=aux");
        log.info("sonarURL: {}", dockerTopURL);
        return dockerTopURL.toString();
    }

    //
    public static String treatDockerGetContainerURL(String status){
        final StringBuilder dockerGetAllContainersURL = new StringBuilder("http://localhost:2376/v1.24/containers/json?all=0&before=8dfafdbc3a40&size=1&filters={%22status%22:[%22");
        dockerGetAllContainersURL.append(status);
        dockerGetAllContainersURL.append("running%22]}\");");
        log.info("sonarURL: {}", dockerGetAllContainersURL);
        return dockerGetAllContainersURL.toString();
    }

    public static List<SonarIssues> extractInfoFromJSON(String response) throws ParseException, java.text.ParseException, org.json.simple.parser.ParseException, JSONException {

        final List<SonarIssues> listInvididualDayResponse = new ArrayList<SonarIssues>();

        final JSONParser parser = new JSONParser();
        final org.json.simple.JSONObject json = (org.json.simple.JSONObject) parser.parse(response);

        if(json.containsKey("issues")) {
            final JSONArray list = (JSONArray) json.get("issues");

            for (int i = 0; i < list.size(); i++) {

                final SonarIssues sonarIssue = new SonarIssues();
                final org.json.simple.JSONObject object = (org.json.simple.JSONObject) parser.parse(list.get(i).toString());

                sonarIssue.setDebt((String) object.get("debit"));
                sonarIssue.setEffort((String) object.get("effort"));
                sonarIssue.setMessage((String) object.get("message"));
                sonarIssue.setProject((String) object.get("project"));
                sonarIssue.setSeverity((String) object.get("severity"));
                sonarIssue.setStatus((String) object.get("status"));
                sonarIssue.setType((String) object.get("type"));

                sonarIssue.setDebitInMinutes(convertToMinutes(sonarIssue.getDebt()));
                sonarIssue.setEfforInMinutes(convertToMinutes(sonarIssue.getEffort()));

                listInvididualDayResponse.add(sonarIssue);
            }

        }

        return listInvididualDayResponse;
    }

    public static List<ComputationalResources> extractInfoFromContainerJSON(String response) throws ParseException, java.text.ParseException, org.json.simple.parser.ParseException, JSONException {

        final List<ComputationalResources> listInvididualDayResponse = new ArrayList<ComputationalResources>();

        final JSONParser parser = new JSONParser();
        final org.json.simple.JSONObject json = (org.json.simple.JSONObject) parser.parse(response);

        Double cpuUsagePercentage = 0.0;
        Double memoryUsagePercentage = 0.0;

        if(json.containsKey("Processes")) {
            final JSONArray list = (JSONArray) json.get("Processes");

            for (int i = 0; i < list.size(); i++) {

                final ComputationalResources resourcesUsage = new ComputationalResources();
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
                final int loop = 1;
                for (int j = 0; j < resourceEntry.size(); j++) {
                    if(loop == 3) {
                        cpuUsagePercentage+= (Double) resourceEntry.get(j);
                    }
                    if(loop == 4) {
                        memoryUsagePercentage+= (Double) resourceEntry.get(j);
                    }
                }

                listInvididualDayResponse.add(resourcesUsage);
            }

        }

        return listInvididualDayResponse;
    }

    private static Double convertToMinutes(String sonarIssue) {
        if(sonarIssue != null) {
            final String base = sonarIssue.replaceAll("\\d", "");
            final Double number = Double.valueOf(sonarIssue.replaceAll("\\D", ""));
            Double numberInMinutes = 0.0;
            switch (base) {
                case "h":
                    numberInMinutes = new Double(number * 60);
                    break;
                case "d":
                    numberInMinutes = new Double(number * 60 * 24);
                    break;
                case "min":
                    numberInMinutes = new Double(number);
                    break;
                default:
                    break;
            }
            return numberInMinutes;
        }
        return null;
    }

}

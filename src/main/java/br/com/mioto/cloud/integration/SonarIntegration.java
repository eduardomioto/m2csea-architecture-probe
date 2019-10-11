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

import br.com.mioto.cloud.vo.SonarIssues;

/**
 * The Class OpenWeatherMapIntegration.
 */
@Component
public class SonarIntegration {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(SonarIntegration.class);

    /** The sdf. */
    private final SimpleDateFormat sdf = new SimpleDateFormat();

    public static String treatSonarURL(){
        final StringBuilder sonarURL = new StringBuilder("http://sonarqube:9000/api/issues/search");
        log.info("sonarURL: {}", sonarURL);
        return sonarURL.toString();
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

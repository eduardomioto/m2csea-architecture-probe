package br.com.mioto.cloud.bo.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.bo.SonarBO;
import br.com.mioto.cloud.commons.HttpCommons;
import br.com.mioto.cloud.integration.SonarIntegration;
import br.com.mioto.cloud.vo.AgregattedSonarIssues;
import br.com.mioto.cloud.vo.SonarIssues;

@Component
public class SonarBOImpl implements SonarBO {

    private static final Logger log = LoggerFactory.getLogger(SonarBOImpl.class);


    @Override
    public List<SonarIssues> getAllIssues() throws SQLException, IOException, ParseException, java.text.ParseException, org.json.simple.parser.ParseException, JSONException {

        final String response = HttpCommons.callHttp(SonarIntegration.treatSonarURL(), "GET");
        log.debug("response: {}", response);
        return  SonarIntegration.extractInfoFromJSON(response);

    }

    @Override
    public List<AgregattedSonarIssues> getIssuesAggregatedByServices() throws SQLException, IOException, ParseException, java.text.ParseException, org.json.simple.parser.ParseException, JSONException {

        final String response = HttpCommons.callHttp(SonarIntegration.treatSonarURL(), "GET");
        log.debug("response: {}", response);

        final List<SonarIssues> sonarIssues = SonarIntegration.extractInfoFromJSON(response);


        final HashMap<String, AgregattedSonarIssues> map = new HashMap<String, AgregattedSonarIssues>();

        for (final SonarIssues issue : sonarIssues) {
            final AgregattedSonarIssues aggIssue = new AgregattedSonarIssues();
            aggIssue.setDebitInMinutes(issue.getDebitInMinutes());
            aggIssue.setEfforInMinutes(issue.getEfforInMinutes());
            aggIssue.setProject(issue.getProject());

            if(map.containsKey(issue.getProject())) {
                final AgregattedSonarIssues agg = map.get(issue.getProject());
                if(agg.getDebitInMinutes() != null) {
                    agg.setDebitInMinutes(agg.getDebitInMinutes() + issue.getDebitInMinutes());
                }else {
                    agg.setDebitInMinutes(issue.getDebitInMinutes());
                }

                if(agg.getEfforInMinutes() != null) {
                    agg.setEfforInMinutes(agg.getEfforInMinutes() + issue.getEfforInMinutes());
                }else {
                    agg.setEfforInMinutes(issue.getEfforInMinutes());
                }
                map.put(issue.getProject(), agg);
            }else {
                map.put(issue.getProject(), aggIssue);
            }
        }

        final List<AgregattedSonarIssues> agregattedSonarIssuesList = new ArrayList<AgregattedSonarIssues>(map.values());
        Collections.sort(agregattedSonarIssuesList, Collections.reverseOrder());
        return agregattedSonarIssuesList;
    }

    public static void main(String[] args) {

    }

}

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.bo.CriticalityBO;
import br.com.mioto.cloud.bo.SonarBO;
import br.com.mioto.cloud.commons.HttpCommons;
import br.com.mioto.cloud.commons.ProbeUtils;
import br.com.mioto.cloud.dao.CoverageDAO;
import br.com.mioto.cloud.integration.SonarIntegration;
import br.com.mioto.cloud.vo.AgregattedSonarIssues;
import br.com.mioto.cloud.vo.CriticalityVO;
import br.com.mioto.cloud.vo.SonarIssues;
import br.com.mioto.cloud.vo.UnitTestCoverage;

/**
 * The Class SonarBOImpl.
 */
@Component
public class SonarBOImpl implements SonarBO {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(SonarBOImpl.class);

    /** The criticality BO. */
    @Autowired
    private CriticalityBO criticalityBO;

    @Autowired
    private CoverageDAO coverageDAO;

    /**
     * Gets the all issues.
     *
     * @return the all issues
     * @throws SQLException the SQL exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws JSONException the JSON exception
     */
    @Override
    public List<SonarIssues> getAllIssues() throws SQLException, IOException, ParseException, java.text.ParseException, org.json.simple.parser.ParseException, JSONException {

        final String response = HttpCommons.callHttp(SonarIntegration.treatSonarURL(), "GET");
        log.debug("response: {}", response);
        return  SonarIntegration.extractInfoFromJSON(response);

    }

    /**
     * Gets the issues aggregated by services.
     *
     * @return the issues aggregated by services
     * @throws SQLException the SQL exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws JSONException the JSON exception
     */
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

            if(!getSkipList().contains(issue.getProject())){
                final String projectName = issue.getProject().replace("br.com.mioto.cloud.mc2pd:", "");
                aggIssue.setProject(projectName);

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
        }

        final List<AgregattedSonarIssues> list = new ArrayList<AgregattedSonarIssues>(map.values());

        Collections.sort(list, Collections.reverseOrder());

        if(criticalityBO.hasChangeConfig()) {
            if(list.size() > 0) {
                list.remove(0);
                Collections.sort(list, Collections.reverseOrder());
            }
        }

        if(list.size() > 0) {
            final AgregattedSonarIssues issue = list.get(0);
            checkCriticality(issue);
        }

        return list;
    }

    /**
     * Gets the skip list.
     *
     * @return the skip list
     */
    public static List<String> getSkipList() {
        final List<String> skipMicroservices = new ArrayList<String>();
        skipMicroservices.add("br.com.mioto.cloud:finder");
        skipMicroservices.add("br.com.mioto.cloud:specialist-opinion");
        skipMicroservices.add("br.com.mioto.cloud:architecture-probe");
        return skipMicroservices;
    }

    /**
     * Check criticality.
     *
     * @param aregattedSonarIssues the aregatted sonar issues
     * @throws SQLException the SQL exception
     */
    private void checkCriticality(final AgregattedSonarIssues aregattedSonarIssues) throws SQLException {

        final Double effort = aregattedSonarIssues.getEfforInMinutes();
        final Integer criticalityFactor = this.calculateCriticalityFactor(effort);

        final String microservice = ProbeUtils.normalizeMicroserviceName(aregattedSonarIssues.getProject());
        final CriticalityVO vo = criticalityBO.populate(microservice, criticalityFactor, effort.toString(), "tech-debit");
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

        if((value > 0) && (value <= 120)) {
            return 1;

        }else if((value > 120) && (value <= 240)) {
            return 2;

        }else if((value > 240) && (value <= 480)) {
            return 3;

        }else if((value > 480) && (value <= 960)) {
            return 4;
        }

        return 5;
    }

    @Override
    public List<UnitTestCoverage> getUnitTestCoverage() throws SQLException {

        final List<UnitTestCoverage> list =  coverageDAO.getUnitTestCoverage();
        Collections.sort(list);

        if(list.size() > 0) {
            final UnitTestCoverage issue = list.get(0);
            checkCriticalityUnitTestCoverage(issue);
        }

        return list;
    }

    /**
     * Check criticality.
     *
     * @param aregattedSonarIssues the aregatted sonar issues
     * @throws SQLException the SQL exception
     */
    private void checkCriticalityUnitTestCoverage(final UnitTestCoverage unitTestCoverage) throws SQLException {

        final Double effort = unitTestCoverage.getCoverage();
        final Integer criticalityFactor = this.calculateCriticalityFactorUnitTestCoverage(effort);

        final String microservice = ProbeUtils.normalizeMicroserviceName(unitTestCoverage.getMicroservice());
        final CriticalityVO vo = criticalityBO.populate(microservice, criticalityFactor, effort.toString(), "unit-test-coverage");
        log.info("Criticality: {}", vo);
        criticalityBO.saveCriticality(vo);
    }

    /**
     * Calculate criticality factor.
     *
     * @param value the value
     * @return the integer
     */
    public Integer calculateCriticalityFactorUnitTestCoverage(Double value) {

        if((value > 0) && (value <= 20)) {
            return 5;

        }else if((value > 20) && (value <= 40)) {
            return 4;

        }else if((value > 40) && (value <= 60)) {
            return 3;

        }else if((value > 60) && (value <= 80)) {
            return 2;
        }
        return 1;
    }
}

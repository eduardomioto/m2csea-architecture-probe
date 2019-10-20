package br.com.mioto.cloud.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.mioto.cloud.bo.SonarBO;
import br.com.mioto.cloud.vo.AgregattedSonarIssues;
import br.com.mioto.cloud.vo.SonarIssues;
import br.com.mioto.cloud.vo.UnitTestCoverage;

@CrossOrigin
@RestController
public class SonarController {

    private static final Logger log = LoggerFactory.getLogger(SonarController.class);

    @Autowired
    private SonarBO sonarBO;

    @RequestMapping(value = "/microservices/issues/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<SonarIssues>> getComputacionalResources() {
        log.info("SonarController >> getComputacionalResources");
        List<SonarIssues> issuesList = new ArrayList<>();

        try {
            issuesList = sonarBO.getAllIssues();


        } catch (final Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return new ResponseEntity<List<SonarIssues>>(issuesList, HttpStatus.OK);
    }

    @RequestMapping(value = "/microservices/issues/grouped", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<AgregattedSonarIssues>> grouped() {
        log.info("SonarController >> grouped");
        List<AgregattedSonarIssues> issuesList = new ArrayList<>();

        try {
            issuesList = sonarBO.getIssuesAggregatedByServices();


        } catch (final Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return new ResponseEntity<List<AgregattedSonarIssues>>(issuesList, HttpStatus.OK);
    }

    @RequestMapping(value = "/microservices/unitTestCoverage/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UnitTestCoverage>> getUnitTestCoverage() {
        log.info("SonarController >> getUnitTestCoverage");
        List<UnitTestCoverage> issuesList = new ArrayList<>();

        try {
            issuesList = sonarBO.getUnitTestCoverage();


        } catch (final Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return new ResponseEntity<List<UnitTestCoverage>>(issuesList, HttpStatus.OK);
    }
}

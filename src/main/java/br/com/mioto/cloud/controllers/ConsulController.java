package br.com.mioto.cloud.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.mioto.cloud.bo.AvailabilityBO;
import br.com.mioto.cloud.vo.ConsulStatus;

@CrossOrigin
@RestController
public class ConsulController {

    private static final Logger log = LoggerFactory.getLogger(ConsulController.class);

    @Autowired
    private AvailabilityBO consulBO;

    @RequestMapping(value = "/microservices/healthchecks/days/{days}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ConsulStatus>> getHealthchecks(@PathVariable("days") String days) {
        log.info("ConsulController >> getHealthchecks");
        List<ConsulStatus> healthchecks = new ArrayList<>();

        try {
            healthchecks = consulBO.getAllHealthchecks(days);
        } catch (final Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return new ResponseEntity<List<ConsulStatus>>(healthchecks, HttpStatus.OK);
    }


}

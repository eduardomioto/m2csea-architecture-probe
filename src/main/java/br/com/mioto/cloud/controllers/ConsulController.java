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

import br.com.mioto.cloud.bo.ConsulBO;
import br.com.mioto.cloud.vo.ConsulHealthcheck;

@CrossOrigin
@RestController
public class ConsulController {

    private static final Logger log = LoggerFactory.getLogger(ConsulController.class);

    @Autowired
    private ConsulBO consulBO;

    @RequestMapping(value = "/microservices/healthchecks/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ConsulHealthcheck>> getComputacionalResources() {
        log.info("SonarController >> getComputacionalResources");
        List<ConsulHealthcheck> healthchecks = new ArrayList<>();

        try {
            healthchecks = consulBO.getAllHealthchecks();
        } catch (final Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return new ResponseEntity<List<ConsulHealthcheck>>(healthchecks, HttpStatus.OK);
    }


}

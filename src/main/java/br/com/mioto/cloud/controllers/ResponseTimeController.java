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

import br.com.mioto.cloud.bo.ResponseTimeBO;
import br.com.mioto.cloud.vo.ResponseTime;

@CrossOrigin
@RestController
public class ResponseTimeController {

    private static final Logger log = LoggerFactory.getLogger(ResponseTimeController.class);

    @Autowired
    private ResponseTimeBO responseTimeBO;

    @RequestMapping(value = "/microservices/responseTime/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ResponseTime>> getResponseTime() {
        log.info("ResponseTimeController >> getResponseTime");
        List<ResponseTime> responseTime = new ArrayList<>();

        try {
            responseTime = responseTimeBO.getAllResponseTimes();
        } catch (final Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return new ResponseEntity<List<ResponseTime>>(responseTime, HttpStatus.OK);
    }


}

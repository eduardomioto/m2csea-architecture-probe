package br.com.mioto.cloud.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.mioto.cloud.bo.DockerApiBO;
import br.com.mioto.cloud.vo.UserVision;

@CrossOrigin
@RestController
public class DockerApiController {

    private static final Logger log = LoggerFactory.getLogger(DockerApiController.class);

    @Autowired
    private DockerApiBO userVisionBO;

    @RequestMapping(value = "/microservices/vision/user/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserVision>> getUserVision() {
        log.info("UserVisionController >> getUserVision");
        List<UserVision> listMicroservices = new ArrayList<>();
        
        try {
            listMicroservices = userVisionBO.getAllUserVision();

           
        } catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return new ResponseEntity<List<UserVision>>(listMicroservices, HttpStatus.OK);
    }

    @RequestMapping(value = "/microservices/vision/user/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity  postUserVision(@RequestBody UserVision userVision) {
        log.info("UserVisionController >> postUserVision");
        log.info("userVision: {}", userVision);
        try {
            userVisionBO.storeUserVision(userVision);
        } catch (SQLException e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @RequestMapping(value = "/microservices/vision/user/", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity  updateUserVision(@RequestBody UserVision userVision) {
        log.info("UserVisionController >> updateUserVision");
        log.info("userVision: {}", userVision);
        try {
            userVisionBO.updateUserVision(userVision);
        } catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }
}

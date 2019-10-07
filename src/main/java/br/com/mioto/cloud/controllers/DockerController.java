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

import br.com.mioto.cloud.bo.DockerBO;
import br.com.mioto.cloud.vo.ComputationalResources;

@CrossOrigin
@RestController
public class DockerController {

    private static final Logger log = LoggerFactory.getLogger(DockerController.class);

    @Autowired
    private DockerBO dockerBO;

    @RequestMapping(value = "/microservices/resourceUsage/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ComputationalResources>> getComputacionalResources() {
        log.info("SonarController >> getComputacionalResources");
        List<ComputationalResources> computacionalResourceUsage = new ArrayList<>();

        try {
            computacionalResourceUsage = dockerBO.getAllResourceComsuption();


        } catch (final Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return new ResponseEntity<List<ComputationalResources>>(computacionalResourceUsage, HttpStatus.OK);
    }


}

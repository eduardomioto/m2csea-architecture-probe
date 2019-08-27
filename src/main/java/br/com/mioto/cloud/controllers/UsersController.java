package br.com.mioto.cloud.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mioto.cloud.dao.repository.UsersRepository;

@CrossOrigin
@RestController
public class UsersController {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    private UsersRepository repository;

    UsersController(UsersRepository usersRepository) {
        this.repository = usersRepository;
    }
    
    @GetMapping
    public List findAll(){
       return repository.findAll();
    }
    
   
}

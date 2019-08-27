package br.com.mioto.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.mioto.cloud.dao.repository.UsersRepository;
import br.com.mioto.cloud.entity.Users;

@SpringBootApplication
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Bean
    public CommandLineRunner demo(UsersRepository repository) {
        return (args) -> {
            // save a couple of customers
         
            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Users customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

           
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            //  log.info(bauer.toString());
            // }
            log.info("");
        };
    }

}
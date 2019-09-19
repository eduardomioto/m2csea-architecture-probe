package br.com.mioto.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableConfigurationProperties
@EnableFeignClients
@Slf4j
@SpringBootApplication
public class ArchitectureProbe {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(ArchitectureProbe.class, args);
    }
}

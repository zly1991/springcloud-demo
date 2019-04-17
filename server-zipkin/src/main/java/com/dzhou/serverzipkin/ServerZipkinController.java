package com.dzhou.serverzipkin;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/zipkin")
public class ServerZipkinController {
    private static final Logger LOG = Logger.getLogger(ServerZipkinController.class.getName());
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    @RequestMapping("/hello")
    public String callHome(){
        return restTemplate.getForObject("http://EUREKA-CLIENT/hi?name=燕子", String.class);
    }

}

package com.dzhou.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class HelloService {
    @Autowired
    public RestTemplate template;
    public String sayHi(){
        //这个地方不用加locahost 端口直接服务名
       return template.getForObject("http://EUREKA-CLIENT/hi?name=燕子",String.class);
    }
}

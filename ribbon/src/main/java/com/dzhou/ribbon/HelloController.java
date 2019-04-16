package com.dzhou.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/ribbon")
@RequestMapping("/ribbon")
public class HelloController {
    @Autowired
    public HelloService service;
    @RequestMapping("/hi")
    public String Hello(){
        return  service.sayHi();
    }
}

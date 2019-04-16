package com.dzhou.feign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloFeignController {
    public static int num = 0;
    @Autowired
    public HelloFeighService service;

    @RequestMapping("/hi")
    @HystrixCommand(fallbackMethod = "returnError")
    public String sayHello(@RequestParam("name") String name) {
        num++;
        if (num % 2 == 0) {
            System.out.print(1 / 0);
        }
        return this.service.sayHello(name);
    }

    @RequestMapping("/buy")
    public void buy() {
        this.service.buy();
    }

    public String returnError(String name) {
        return "熔断了" + name;
    }
}

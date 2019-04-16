package com.dzhou.feign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(serviceId = "EUREKA-CLIENT")
public interface HelloFeighService {
    @RequestMapping("/hi")
    public String sayHello(@RequestParam(value = "name") String name);

    @RequestMapping("/redis/buys")
    public void buy();
}

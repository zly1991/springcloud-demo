package com.dzhou.eurekaclient;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;

@RestController
@RequestMapping("/redis")
public class RedisController {
    static int number = 200000;
    static int successnum = 0;
//    public RabbitTemplate  rabbitTemplate;
    @Autowired
    public RedisUtil redisUtil;
    @Autowired
    public StringRedisTemplate template;
    @Autowired
    public JmsTemplate jmsTemplate;

    Destination destination = new ActiveMQQueue("baoziqueue");
    @RequestMapping("/get")
    public String getValue(@RequestParam("key") String key) {
        return this.template.opsForValue().get(key);
    }

    @RequestMapping(value = "/set"   )
    public String setValue(@RequestParam("val") String val) {
        this.template.opsForValue().set("test", val);
        return "success";
    }

    @RequestMapping("/reset")
    public String reset(@RequestParam("val") String val) {
        number = 200000;
        return "success";
    }

    @RequestMapping("/buys")
    public synchronized void sbuyBaoZi() {
        template.boundValueOps("test").increment(-1);
        successnum++;
    }

    @RequestMapping("/buy")
    public void buyBaoZi() {
        if (redisUtil.lock("buy")) {
            template.boundValueOps("test").increment(-1);
            redisUtil.unlock("buy");
            successnum++;
        }
    }
    @JmsListener(destination ="baoziqueue")
    public void buyByMqListener(String text){
        if(text.equals("1")){
            template.boundValueOps("test").increment(-1);
            redisUtil.unlock("buy");
            successnum++;
        }
    }
    @RequestMapping("/buymq")
    public void buyByMq(){
        jmsTemplate.convertAndSend(destination,1);
    }
    @RequestMapping("/now")
    public String nowNumber() {
        return "现在有" + this.template.opsForValue().get("test") + "成功下单人数有" + successnum;
    }
}

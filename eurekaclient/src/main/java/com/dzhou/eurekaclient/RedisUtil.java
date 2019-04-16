package com.dzhou.eurekaclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
    @Autowired
    public   StringRedisTemplate template;

    public   boolean lock(String key) {
        long now = System.currentTimeMillis();
        //2秒应该够一个请求了
        long expire = now + 2 * 1000; //过期时间加上2s
        if (template.opsForValue().setIfAbsent(key, expire + "")) {
            return true;
        }
        String value=template.opsForValue().get(key);//当前库里的值
        //是否过期，过期的话大家就来抢这个锁,避免死锁
        if(value!=null&&Long.parseLong(value)<System.currentTimeMillis()){
                  //赋予新的值，然后返回原来的值
            // 只有一个线程会拿到锁，后面的线程可能会set值进去，但是不会返回true
             String oldvalue=    template.opsForValue().getAndSet(key,expire+"");
             if(oldvalue!=null&&oldvalue.equals(value)){
                 return true;
             }
        }

        return false;

    }
    public   void unlock(String key) {
        try {
            if(template.opsForValue().get(key)!=null){
                template.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            System.out.print("线程出错");
        }
    }
}

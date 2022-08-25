package com.example.groupbuy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.List;


@Service
public class RedisService extends CachingConfigurerSupport {

//    @Autowired
//    private JedisPool jedisPool;

    Logger logger = LoggerFactory.getLogger(RedisService.class);

    private Jedis redis = null;

    // 这里Bean注入的时候会报错：直接调用使用 @Bean 注解的方法。请改用依赖项注入。
    @Autowired
    public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "localhost", 6379, 5000);
        return jedisPool;
    }

    /**
     * 获取对象
     */
//    @Resource
    public <T> T get(String key,Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = redisPoolFactory().getResource();
            String sval = jedis.get(key);
            //将String转换为Bean
            T t = stringToBean(sval,clazz);
            return t;
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 设置对象
     */
    @SuppressWarnings("SpringConfigurationProxyMethods")
    public <T> boolean set(String key, T value){
        Jedis jedis = null;
        try {
            jedis = redisPoolFactory().getResource();
            //将Bean转换为String
            String s = beanToString(value);
            if(s == null || s.length() <= 0) {
                return false;
            }
            jedis.set(key, s);
            return true;
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 设置对象,含过期时间(单位：秒)
     */
    public <T> boolean set(String key,T value,int expireTime){
        Jedis jedis = null;
        try {
            jedis = redisPoolFactory().getResource();
            //将Bean转换为String
            String s = beanToString(value);
            if(s == null || s.length() <= 0) {
                return false;
            }
            if(expireTime <= 0) {
                //有效期：代表不过期
                jedis.set(key, s);
            }else {
                jedis.setex(key, expireTime, s);
            }
            return true;
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 减少值
     */
    public <T> Long decr(String key){
        Jedis jedis = null;
        try {
            jedis = redisPoolFactory().getResource();
            //返回value减1后的值
            return jedis.decr(key);
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 增加值
     */
    public <T> Long incr(String key){
        Jedis jedis = null;
        try {
            jedis = redisPoolFactory().getResource();
            //返回value加1后的值
            return jedis.incr(key);
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 检查key是否存在
     */
    public <T> boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis =  redisPoolFactory().getResource();
            return jedis.exists(key);
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 将字符串转换为Bean对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String str,Class<T> clazz) {
        if(str == null || str.length() == 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return ((T) Integer.valueOf(str));
        }else if(clazz == String.class) {
            return (T) str;
        }else if(clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        }else if(clazz == List.class) {
            return JSON.toJavaObject(JSONArray.parseArray(str), clazz);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    /**
     * 将Bean对象转换为字符串类型
     */
    public static <T> String beanToString(T value) {
        if(value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }



}

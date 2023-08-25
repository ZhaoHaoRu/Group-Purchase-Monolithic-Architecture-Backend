package com.example.groupbuy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class MQConfig {

    //Direct模式
    public static final String QUEUE="queue";

    /**
     * Direct模式
     */
    @Bean
    public Queue queue() {
        //名称，是否持久化
        return new Queue(QUEUE,true);
    }

}

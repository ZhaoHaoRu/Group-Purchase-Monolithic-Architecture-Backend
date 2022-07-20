package com.example.groupbuy.mq;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.config.MQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//生产者
@Service
public class MQSender {

    //@Autowired
    @Resource
    AmqpTemplate amqpTemplate;

    //Direct模式
    public void send(JSONObject msg) {
        //第一个参数队列的名字，第二个参数发出的信息
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }

}

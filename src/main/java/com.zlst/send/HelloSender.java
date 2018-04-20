package com.zlst.send;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by songcj on 2018/4/9.
 */
@Component
public class HelloSender {
    @Autowired
    private AmqpTemplate template;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        template.convertAndSend("queue","hello,rabbit~");
    }

    public void send(String queueName, Serializable serializable){
        template.convertAndSend(queueName,serializable);
    }

    public void sendByRabbitTemplate(){
        rabbitTemplate.convertAndSend("queue","hello,rabbit~");
    }

    public void sendByRabbitTemplate(String queueName, Serializable serializable){
        rabbitTemplate.convertAndSend(queueName,serializable);
    }

}

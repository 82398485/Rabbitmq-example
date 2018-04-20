package com.zlst.receive;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by songcj on 2018/4/9.
 */
@Component
public class HelloReceive {

    @RabbitListener(queues = "queue") //监听器指定的 Queue
    public void process(String str){
        System.out.println("Receive:"+str);
    }

}

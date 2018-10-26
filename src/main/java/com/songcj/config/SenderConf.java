package com.songcj.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by songcj on 2018/4/9.
 */
@Configuration
public class SenderConf {

    @Bean
    public Queue queue(){
        return new Queue("queue",false, false, false, null);
    }

}

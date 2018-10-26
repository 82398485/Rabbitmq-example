package com.songcj.receive;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 网关日志消息原生监听
 * @author chenguoxiang
 * @create 2018-02-12 9:46
 **/
@Deprecated
@Component
public class RabbitmqNativeListener {

    private static Logger log = LoggerFactory.getLogger(RabbitmqNativeListener.class);

    private RabbitmqNativeListener(){
    }

    /**
     * 网关日志原生监听
     * @param ctx
     * @throws Exception
     */
    public static void zuulListener(ConfigurableApplicationContext ctx) throws IOException,TimeoutException{
        log.info("into native listener ... ");
        //ReqRecordInfoMessage mess = ctx.getBean(ReqRecordInfoMessage.class);
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ地址
        factory.setHost(ctx.getEnvironment().getProperty("spring.rabbitmq.host"));
        //设置RabbitMQ相关信息
        factory.setUsername(ctx.getEnvironment().getProperty("spring.rabbitmq.username"));
        factory.setPassword(ctx.getEnvironment().getProperty("spring.rabbitmq.password"));
        factory.setPort(Integer.valueOf(ctx.getEnvironment().getProperty("spring.rabbitmq.port")));
        String queueName = ctx.getEnvironment().getProperty("gateway.log.queue");
        queueName = "zuul";
        //创建一个新的连接
        Channel channel;
        Consumer consumer;
        Connection connection = factory.newConnection();
        //创建一个通道
        channel = connection.createChannel();

        //声明要关注的队列
        //ueueDeclare（名字，是否持久化，独占的queue， 不使用时是否自动删除，其他参数）
        channel.queueDeclare(queueName, false, false, false, null);
        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                if (message.matches("^\\{.*}$")) {
                    //BaseMessage payload = JSONObject.parseObject(message, BaseMessage.class);
                    //mess.procMsg(payload);
                    log.info(message);
                } else {
                    log.error("接收到异常字符串:{}", message);
                }
            }
        };
        //自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume(queueName, true, consumer);
    }

}

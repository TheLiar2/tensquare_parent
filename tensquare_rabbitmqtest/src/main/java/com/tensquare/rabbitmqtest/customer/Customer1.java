package com.tensquare.rabbitmqtest.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xiaokuli
 * @date 2019/5/28 - 17:02
 */
@Component
@RabbitListener(queues = "itcast")
public class Customer1 {

    @RabbitHandler
    public void showMsg(String msg){
        System.out.println("8099:itcast接收到消息:"+msg);
    }
}

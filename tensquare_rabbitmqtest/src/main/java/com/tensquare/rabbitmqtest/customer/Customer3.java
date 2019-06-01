package com.tensquare.rabbitmqtest.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xiaokuli
 * @date 2019/5/28 - 17:02
 */
@Component
@RabbitListener(queues = "itcastbaima")
public class Customer3 {
    
    @RabbitHandler
    public void showMsg(String msg){
        System.out.println("customer3接收到消息:"+msg);
    }
}

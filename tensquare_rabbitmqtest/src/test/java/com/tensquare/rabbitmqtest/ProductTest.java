package com.tensquare.rabbitmqtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiaokuli
 * @date 2019/5/28 - 16:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMqTestApplication.class)
public class ProductTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //直接模式测试  customer1
    @Test
    public void testMsg(){
        rabbitTemplate.convertAndSend("itcast","我要红包");
    }

    //分裂模式测试 customer2 customer3
    @Test
    public void testMsg2(){
        rabbitTemplate.convertAndSend("theliar","","分裂模式测试");
    }

    //主题模式测试 customer2 customer3
    @Test
    public void testMsg3(){
        rabbitTemplate.convertAndSend("topic","gan.abc","主题模式测试");
    }
}

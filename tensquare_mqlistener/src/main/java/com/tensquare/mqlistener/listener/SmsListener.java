package com.tensquare.mqlistener.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.tensquare.mqlistener.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author xiaokuli
 * @date 2019/5/28 - 23:35
 */
/*
* 短信监听类*/
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String template_code;

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;


    /*发送短信*/
    @RabbitHandler
    public void sendSms(Map<String,String> message){
        String mobile = message.get("mobile");
        String code = message.get("code");
        System.out.println("手机号："+message.get("mobile"));
        System.out.println("验证码："+message.get("code"));
        try {
            //发送短信
            SendSmsResponse sendSmsResponse = smsUtil.sendSms(mobile, template_code, sign_name, "{\"code\":\"" + code + "\"}");
            System.out.println(sendSmsResponse);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}

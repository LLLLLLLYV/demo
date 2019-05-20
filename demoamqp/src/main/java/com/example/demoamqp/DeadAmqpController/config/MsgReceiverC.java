package com.example.demoamqp.DeadAmqpController.config;

import com.example.demoamqp.DeadAmqpController.bean.User;
import com.example.demoamqp.DeadAmqpController.service.impl.CacheServiceImpl;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component

public class MsgReceiverC {
    @Autowired
    private CacheServiceImpl cacheService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     *
     * @param content
     * @param channel
     * @param tag  当一个消费者向 RabbitMQ 注册后，会建立起一个 Channel ，RabbitMQ 会用 basic.deliver                   方法向消费者推送消息，这个方法携带了一个 delivery tag， 它代表了 RabbitMQ 向该 Channel                         投递的这条消息的唯一标识 ID，是一个单调递增的正整数，delivery tag 的范围仅限于 Channel
     */
    @RabbitListener(queues="DEAD_QUEUE")
    public void process(User content,Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        logger.info("tag： " + tag);
        logger.info("接收处理队列DEAD_QUEUE当中的消息： " + content);

            try {
                content = cacheService.queryUserById(content.getId());
                Boolean status = content.getStatus();
                if(null!=status &&true==status){
                    //消息的确认
                    channel.basicAck(tag,false);
                    System.out.println(tag+"------------消息支付成功了");
                }else{
                    /**
                     * basicReject 只能拒绝一条消息
                     *
                     * basicNack  能拒绝0 到 n条消息
                     */
                    //消息的拒绝
                    channel.basicReject(tag,false);
                    System.out.println(tag+"------------消息支付失败了");
                }
            } catch (IOException e) {

            }


        /*byte[] body = content.getBody();
        String s = new String(body);*/

    }
}

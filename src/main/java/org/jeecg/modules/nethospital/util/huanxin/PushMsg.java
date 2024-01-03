package org.jeecg.modules.nethospital.util.huanxin;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author abc
 * @version 1.0
 * @date 2024/1/2 10:13
 */
public class PushMsg {

    public static void main(String[] args) throws Exception{
        new PushMsg().systemSinglePush("您有新的系统消息", "您有新的系统消息");
    }

    //系统推送--个人
    public void systemSinglePush(String title, String content) throws Exception{
        MqPushSingle pushSingle = new MqPushSingle();

        //设置推送内容
        Map<String, Object> msg = new HashMap<>();
        msg.put("title", title);
        msg.put("sub_title", "系统消息");
        msg.put("content", content);

        //设置图标，震动，响铃
        Map<String, Object> easemob = Maps.newConcurrentMap();
        easemob.put("style", 0);
        easemob.put("sound", 1);
        easemob.put("vibrate", 1);
        easemob.put("needNotification", false);
//        easemob.put("iconUrl", giftConfig.getShopUrl());
        msg.put("easemob", easemob);

        List<String> targets = Lists.newArrayList();
        targets.add("xd0cba0e176458f0850ec05bb4c740c8f");
        targets.add("xd2dc9751cf20be247b89697d78c21f82");
        pushSingle.setTargets(targets);
        pushSingle.setPushMessage(msg);
        pushSingle.setStrategy(0);


        ConnectionFactory connectionFactory = getConnectionFactory();
        Connection newConnection = null;
        Channel createChannel = null;
        try {
            newConnection = connectionFactory.newConnection();
            createChannel = newConnection.createChannel();
            /**
             * 声明一个队列。
             * 参数一：队列名称
             * 参数二：是否持久化
             * 参数三：是否排外  如果排外则这个队列只允许有一个消费者
             * 参数四：是否自动删除队列，如果为true表示没有消息也没有消费者连接自动删除队列
             * 参数五：队列的附加属性
             * 注意：
             * 1.声明队列时，如果已经存在则放弃声明，如果不存在则会声明一个新队列；
             * 2.队列名可以任意取值，但需要与消息接收者一致。
             * 3.下面的代码可有可无，一定在发送消息前确认队列名称已经存在RabbitMQ中，否则消息会发送失败。
             */
//            createChannel.queueDeclare("myQueue", true, false, false,null);

            String message = JSON.toJSONString(pushSingle);
            /**
             * 发送消息到MQ
             * 参数一：交换机名称，为""表示不用交换机
             * 参数二:为队列名称或者routingKey.当指定了交换机就是routingKey
             * 参数三：消息的属性信息
             * 参数四：消息内容的字节数组
             */
            createChannel.basicPublish("", "push.single", null, message.getBytes("utf-8"));

            System.out.println("消息发送成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (createChannel != null) {
                createChannel.close();
            }
            if (newConnection != null) {
                newConnection.close();
            }
        }

//        rabbitTemplate.convertAndSend(HicodeMqConstant.QUEUE_PUSH_SINGLE, pushSingle);
    }


    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("jeecg-boot-rabbitmq");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        return factory;
    }
}

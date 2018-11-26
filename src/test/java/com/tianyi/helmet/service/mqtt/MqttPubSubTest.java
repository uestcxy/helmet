package com.tianyi.helmet.service.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.mqtt.MqttBackendConsumer;
import com.tianyi.helmet.server.service.mqtt.MqttClientService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by liuhanc on 2017/9/23.
 */
public class MqttPubSubTest extends BaseServiceTest {

    @Autowired
    MqttClientService mqttClientService;
    @Autowired
    MqttBackendConsumer mqttBackendConsumer;

//    String topic = "test/liuhan";
//    String clientId = "client@liuhan";


//    class MyThread implements Runnable {
//
//
//        @Override
//        public void run() {
//            UUID clientId = UUID.randomUUID();
//
//            System.out.println("--------------1--------" + clientId);
//            String topicName = "/heartbeat/helmet";
//            MqttClient client = mqttClientService.createClient(clientId + "@pub", null);
//            System.out.println("--------------2--------" + clientId);
//            MqttMessage message = new MqttMessage();
//            message.setQos(1);
//            message.setRetained(true);
//            System.out.println("--------------3--------" + clientId);
//            for (int i = 0; i < 100; i++) {
//                System.out.println("--------------4--------" + clientId);
//
//                String payLoadString = "【测试消息.批次=" + i + ",发布topic=" + topicName + ",发布time=" + LocalDateTime.now().toString() + ",发布客户端Id=" + clientId + "@publish】";
//                message.setPayload(payLoadString.getBytes());
//
//                mqttClientService.testPublishMessage(client, topicName, message);
//                System.out.println("发布消息完毕.topic=" + topicName + ",发布客户端id=" + clientId + "@publish,内容=" + payLoadString);
//                System.out.println("--------------5--------" + clientId);
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                }
//            }
//
//        }
//    }


    @Test
    public void testPublishMessage() throws MqttException {
        String clientId = "client@xiayuan";
        String topicName = "/device/helmet";
        MqttMessage message = new MqttMessage();
        message.setQos(1);
        message.setRetained(true);
        String payLoadString = "{\"imei\":\"a8f1e7e0-8d09-4668-9fe1-ec57bc018cb0\",\"version\":\"v0.1\",\"categoryId\":1}";
        message.setPayload(payLoadString.getBytes());

        mqttClientService.publishMessage(topicName, message);
        System.out.println("发布消息完毕.topic=" + topicName + ",发布客户端id=" + clientId + "@publish,内容=" + payLoadString);
    }

    @Test
    public void testSubscribeMessage() {
        //"/sensor/helmet/", "/gps/helmet/", "/state/helmet/",
        String topicName = "/heartbeat/helmet";
        //MqttClient client1 = mqttClientService.createClient("client@liuhan@sub",null);

        //测试订阅
        ScheduledFuture future1 = mqttClientService.subscribeMessage(topicName, this::testConsumeMsg);
        //
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
        }
    }

    @Test
    public void testMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "helmet1036");
        map.put("type", 1);
        map.put("timeslot", 3000);
        map.put("alltime", "false");

        JSONObject json = new JSONObject(map);

        System.out.println(json.toString());
    }

    public void testConsumeMsg(String topicName, MqttMessage msg) {
        System.out.println("队列:" + topicName + ",收到消息=" + msg);
    }
}



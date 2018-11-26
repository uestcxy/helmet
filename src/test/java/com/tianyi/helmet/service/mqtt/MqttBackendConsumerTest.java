package com.tianyi.helmet.service.mqtt;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.mqtt.MqttBackendConsumer;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2017/10/26.
 */
public class
MqttBackendConsumerTest extends BaseServiceTest {
    @Autowired
    MqttBackendConsumer mqttBackendConsumer;

    @Test
    public void testConsume(){
        MqttMessage msg = new MqttMessage();
        String json = "{\n" +
                "\"id\":\"helmet0030\",\n" +
                "\"time\":1509002612456,\n" +
                "\"lat\":12.123456,\n" +
                "\"lon\":-34.654321,\n" +
                "\"concert\":1,\n" +
                "\"relax\":2,\n" +
                "\"xa\":-22.3345,\n" +
                "\"ya\":1.1234,\n" +
                "\"za\":-14.000987,\n" +
                "\"xg\":918.003001,\n" +
                "\"yg\":12848.032001,\n" +
                "\"zg\":7946.000402,\n" +
                "\"battery\":2,\n" +
                "\"runtime\":75 \n" +
                "}";
        System.out.println("json="+json);
        msg.setPayload(json.getBytes());
//        mqttBackendConsumer.consumeMessage("/sensor/helmet/helmet0020",msg);
    }
    
    @Test
    public void testOperaLogConsume(){
        MqttMessage msg = new MqttMessage();
        String json = "{\n" +
                "\"id\":\"helmet0030\",\n" +
                "\"time\":1509002612456,\n" +
                "\"content\":\"ad那是你的看似疯狂拉升你发撒减肥啦\" \n" +
                "}";
        System.out.println("json="+json);
        msg.setPayload(json.getBytes());
        mqttBackendConsumer.processOperLog("/sensor/helmet/helmet0020",msg);
    }
    
    
}

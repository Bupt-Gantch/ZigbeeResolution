package com.bupt.ZigbeeResolution.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class DataMessageClient {

    public static MqttClient client;

    static{
        try{
            client = new MqttClient(Config.HOST, "data", new MemoryPersistence());
            client.setCallback(new DataMessageCallBack());
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static synchronized void publishData(String token,String data) throws  Exception{
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(token);
        options.setConnectionTimeout(10);
        client.connect(options);
        MqttMessage msg = new MqttMessage(data.getBytes());
        msg.setRetained(false);
        msg.setQos(0);
        client.publish(Config.datatopic, msg);
        client.disconnect();
        System.out.println("数据发布成功");
    }

    public static synchronized void publishAttribute(String token,String data) throws  Exception{
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(token);
        options.setConnectionTimeout(10);
        client.connect(options);

        MqttMessage msg = new MqttMessage(data.getBytes());
        msg.setRetained(false);
        msg.setQos(0);
        client.publish(Config.attributetopic, msg);
        client.disconnect();
        System.out.println("属性发布成功");
    }

    public static synchronized void publishResponse(String token, int requestId, String data) throws Exception {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(token);
        options.setConnectionTimeout(10);
        client.connect(options);

        MqttMessage msg = new MqttMessage(data.getBytes());
        msg.setRetained(false);
        msg.setQos(0);
        client.publish(Config.RPC_RESPONSE_TOPIC + String.valueOf(requestId), msg);
        client.disconnect();
        System.out.println("指令返回发布成功");
    }
}

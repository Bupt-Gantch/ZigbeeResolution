package com.bupt.ZigbeeResolution.mqtt;

import com.bupt.ZigbeeResolution.service.GatewayGroupService;
import com.bupt.ZigbeeResolution.transform.TransportHandler;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.Charset;

/**
 * Created by zy on 2018/5/16.
 */

public class RpcMqttClient {

//    public static String rpcToken = "gbNJ8K5a0Hggwd66vHqn";
    private MqttClient rpcMqtt;
    private String gatewayName;
    private String token;
    private GatewayGroupService gatewayGroupService;

    public RpcMqttClient(String gatewayName , String token, GatewayGroupService gatewayGroupService){
        this.gatewayName = gatewayName;
        this.token = token;
        this.gatewayGroupService = gatewayGroupService;
    }

    public boolean init() {
        if(gatewayGroupService.getGatewayGroup(gatewayName)!=null){
            System.out.println(String.format("初始化mqtt客户端, 网关名:%s", gatewayName));
            try{
                if(rpcMqtt!=null){
                    rpcMqtt.close();
                }
                rpcMqtt = null;
                rpcMqtt = new MqttClient(Config.HOST,"receiveRPC",new MemoryPersistence());
                TransportHandler.mqttMap.put(gatewayName,rpcMqtt);
                MqttConnectOptions optionforRpcMqtt = new MqttConnectOptions();
                optionforRpcMqtt.setCleanSession(true);
                optionforRpcMqtt.setConnectionTimeout(5);
                optionforRpcMqtt.setKeepAliveInterval(20);
                optionforRpcMqtt.setUserName(token);
                rpcMqtt.setCallback(new RpcMessageCallBack(rpcMqtt, token, gatewayGroupService, gatewayName));
                rpcMqtt.connect(optionforRpcMqtt);
                System.out.println(String.format("mqtt客户端连接成功，server:%s, token:%s", Config.HOST, token));
                rpcMqtt.subscribe(Config.RPC_TOPIC,0);
                System.out.println(String.format("mqtt客户端订阅成功, topic:%s", Config.RPC_TOPIC));
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }else{
            System.err.println(String.format("网关[%s]已下线", gatewayName));
        }
        return true;
    }

    public void publicResponce(Integer requestId,String data) throws Exception{
        String topic = Config.RPC_RESPONSE_TOPIC + requestId;
        MqttMessage msg = new MqttMessage();
        msg.setPayload(data.getBytes(Charset.forName("utf-8")));
        if(rpcMqtt.isConnected()){
            rpcMqtt.publish(topic, msg);
        }
    }

    public MqttClient getMqttClient(){
        return rpcMqtt;
    }
}

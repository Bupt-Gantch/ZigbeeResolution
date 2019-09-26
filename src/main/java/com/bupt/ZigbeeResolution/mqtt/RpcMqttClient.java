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
//    public static String RPC_TOPIC = "v1/devices/me/rpc/request/+";
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
                rpcMqtt.subscribe(Config.RPC_TOPIC,0);
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }else{
            System.out.println("网关已下线");
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

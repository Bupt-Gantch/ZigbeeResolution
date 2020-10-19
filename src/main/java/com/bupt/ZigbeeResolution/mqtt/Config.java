package com.bupt.ZigbeeResolution.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Config {
    @Value("${app.mqtt.host}")
    private String mqtt_host;
    @Value("${app.mqtt.port}")
    private String mqtt_port;
    
//    public static String HOST = "tcp://47.105.120.203:30011";   // 30011是k8s的端口，对应1883
    public static String HOST = "tcp://47.105.120.203:1883";
//    public static String HOST;
//    public static String RPC_DEVICE_ACCESSTOKEN = "ViWeZop4Jp4tvvEtwu6Z";
    public static String RPC_TOPIC = "v1/devices/me/rpc/request/+";
    public static String datatopic = "v1/devices/me/telemetry";
    public static String attributetopic = "v1/devices/me/attributes";
    public static String RPC_RESPONSE_TOPIC = "v1/devices/me/rpc/response/";
}

package com.bupt.ZigbeeResolution.mqtt;

public class Config {
    public static String HOST = "tcp://47.105.120.203:30011";
//    public static String HOST = "tcp://127.0.0.1:1884";
//    public static String RPC_DEVICE_ACCESSTOKEN = "ViWeZop4Jp4tvvEtwu6Z";
    public static String RPC_TOPIC = "v1/devices/me/rpc/request/+";
    public static String datatopic = "v1/devices/me/telemetry";
    public static String attributetopic = "v1/devices/me/attributes";
    public static String RPC_RESPONSE_TOPIC = "v1/devices/me/rpc/response/";
}

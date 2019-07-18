package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class GatewayGroup
{
    private String name;
    private String ip;
    private String channel;

    public GatewayGroup(String name, String ip, String channel){
        this.name = name;
        this.ip = ip;
        this.channel = channel;
    }
}

package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class Device {
    String shortAddress;
    byte Endpoint;
    String profileId;
    String deviceId;
    Boolean state;
    String name;
    byte onlineState;
    String IEEE;
    String snid;
    String zoneType;
    double electric;
    byte[] recentState;

    public Device(){}

    public String toString(){
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"shortAddress\":")
                .append("\""+shortAddress+"\"").append(",");
        sb.append("\"Endpoint\":")
                .append("\""+Endpoint+"\"").append(",");
        sb.append("\"profileId\":")
                .append("\""+profileId+"\"").append(",");
        sb.append("\"deviceId\":")
                .append("\""+deviceId+"\"").append(",");
        sb.append("\"state\":")
                .append("\""+state+"\"").append(",");
        sb.append("\"name\":")
                .append("\""+name+"\"").append(",");
        sb.append("\"onlineState\":")
                .append("\""+onlineState+"\"").append(",");
        sb.append("\"IEEE\":")
                .append("\""+IEEE+"\"").append(",");
        sb.append("\"snid\":")
                .append("\""+snid+"\"").append(",");
        sb.append("\"zoneType\":")
                .append("\""+zoneType+"\"").append(",");
        sb.append("\"electric\":")
                .append("\""+electric+"\"");
        sb.append('}');
        return sb.toString();
    }
}

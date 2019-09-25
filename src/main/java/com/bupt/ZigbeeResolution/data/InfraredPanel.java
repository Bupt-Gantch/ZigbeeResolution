package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class InfraredPanel {
    String deviceId;
    Integer panelId;
    String name;
    Integer type;

    @Override
    public String toString() {
        return "InfraredPanel{" +
                "deviceId='" + deviceId + '\'' +
                ", panelId='" + panelId + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}

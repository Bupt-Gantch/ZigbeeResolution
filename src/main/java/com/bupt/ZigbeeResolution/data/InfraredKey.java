package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class InfraredKey {

    Integer panelId;
    Integer keyId;
    Integer number;
    String name;

    public InfraredKey(){}

    @Override
    public String toString() {
        return "InfraredKey{" +
                "panelId=" + panelId +
                ", keyId=" + keyId +
                ", number=" + number +
                ", name='" + name + '\'' +
                '}';
    }
}

package com.bupt.ZigbeeResolution.data;

import lombok.Data;

/**
 * desciption:遥控面板：功能名
 */

@Data
public class Learn {
    String panelName;
    String name;

    @Override
    public String toString() {
        return "Learn{" +
                "panelName='" + panelName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

package com.bupt.ZigbeeResolution.data;

import lombok.Data;

/**
 * desciption:遥控面板：功能名
 */

@Data
public class Learn {
    String name;
    Integer key;
    Integer panelId;
    Integer buttonId;

    @Override
    public String toString() {
        return "Learn{" +
                "name='" + name + '\'' +
                ", key=" + key +
                ", panelId=" + panelId +
                ", buttonId=" + buttonId +
                '}';
    }
}

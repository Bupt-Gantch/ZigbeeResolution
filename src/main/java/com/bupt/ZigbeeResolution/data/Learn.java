package com.bupt.ZigbeeResolution.data;

import lombok.Data;

/**
 * desciption:遥控面板：功能名
 */

@Data
public class Learn {
<<<<<<< HEAD
    // 遥控面板名称（机顶盒，空调，DVD，...）
    public String panelName;
    // 按键名称（开关，静音...）
    public String name;
    // 按键功能编号
    public Integer number;
=======
    String name;
    Integer key;
    Integer panelId;
    Integer buttonId;
>>>>>>> devpeng

    @Override
    public String toString() {
        return "Learn{" +
<<<<<<< HEAD
                "panelName='" + panelName + '\'' +
                ", name='" + name + '\'' +
=======
                "name='" + name + '\'' +
                ", key=" + key +
                ", panelId=" + panelId +
                ", buttonId=" + buttonId +
>>>>>>> devpeng
                '}';
    }
}
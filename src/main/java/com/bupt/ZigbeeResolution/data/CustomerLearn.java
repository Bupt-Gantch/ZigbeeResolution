package com.bupt.ZigbeeResolution.data;

import lombok.Data;

/**
 * desciption:用户学习了的功能
 */

@Data
public class CustomerLearn {
    String panelName;
    String name;

    @Override
    public String toString() {
        return "CustomerLearn{" +
                "panelName='" + panelName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

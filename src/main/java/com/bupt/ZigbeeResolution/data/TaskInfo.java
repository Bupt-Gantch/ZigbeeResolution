package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class TaskInfo {
    String weekWorkMode;
    String hour;
    String minute;
    String second;
    String type;
    String value;
    Integer nameLen;
    String name;

    @Override
    public String toString() {
        return "TaskInfo{" +
                "weekWorkMode:\"" + weekWorkMode + "\"" +
                ", hour:\"" + hour + '\"' +
                ", minute:\"" + minute + '\"' +
                ", second:\"" + second + '\"' +
                ", type:\"" + type + '\"' +
                ", value:\"" + value + '\"' +
                '}';
    }
}

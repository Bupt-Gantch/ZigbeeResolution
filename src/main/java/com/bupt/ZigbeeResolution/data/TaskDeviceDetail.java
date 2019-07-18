package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class TaskDeviceDetail extends Task{
    String shortAddress;
    String deviceId;
    byte endPoint;
    byte condition1;
    int data1;
    byte condition2;
    int data2;

    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"taskType\":")
                .append("\"" + taskType + "\"").append(",");
        sb.append("\"taskId\":")
                .append("\"" + taskId + "\"").append(",");
        sb.append("\"taskName\":")
                .append("\"" + taskName + "\"").append(",");
        sb.append("\"isAlarm\":")
                .append("\"" + isAlarm + "\"").append(",");
        sb.append("\"isAble\":")
                .append("\"" + isAble + "\"").append(",");
        sb.append("\"shortAddress\":")
                .append("\"" + shortAddress + "\"").append(",");
        sb.append("\"deviceId\":")
                .append("\"" + deviceId + "\"").append(",");
        sb.append("\"endPoint\":")
                .append("\"" + endPoint + "\"").append(",");
        sb.append("\"condition1\":")
                .append("\"" + condition1 + "\"").append(",");
        sb.append("\"data1\":")
                .append("\"" + data1 + "\"").append(",");
        sb.append("\"condition2\":")
                .append("\"" + condition2 + "\"").append(",");
        sb.append("\"data2\":")
                .append("\"" + data2 + "\"");
        sb.append('}');
        return sb.toString();
    }
}

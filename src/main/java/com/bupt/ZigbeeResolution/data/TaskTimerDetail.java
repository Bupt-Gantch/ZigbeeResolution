package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class TaskTimerDetail extends Task {
    private int day;
    private int hour;
    private int minute;
    private int second;

    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"taskType\":").append("\"" + taskType + "\"").append(",");
        sb.append("\"taskId\":").append("\"" + taskId + "\"").append(",");
        sb.append("\"taskName\":").append("\"" + taskName + "\"").append(",");
        sb.append("\"isAlarm\":").append("\"" + isAlarm + "\"").append(",");
        sb.append("\"isAble\":").append("\"" + isAble + "\"").append(",");
        sb.append("\"day\":").append("\"" + day + "\"").append(",");
        sb.append("\"hour\":").append("\"" + hour + "\"").append(",");
        sb.append("\"minute\":").append("\"" + minute + "\"").append(",");
        sb.append("\"second\":").append("\"" + second + "\"").append(",");
        sb.append('}');
        return sb.toString();
    }
}

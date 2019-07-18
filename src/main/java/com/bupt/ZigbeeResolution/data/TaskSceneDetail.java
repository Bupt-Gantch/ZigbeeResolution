package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class TaskSceneDetail extends Task {
    String sceneId;

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
        sb.append("\"sceneId\":")
                .append("\"" + sceneId + "\"");
        sb.append('}');
        return sb.toString();
    }
}

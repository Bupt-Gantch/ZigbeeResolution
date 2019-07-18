package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class Group {
    String groupId;
    String groupName;

    public Group(){}

    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"groupId\":")
                .append("\"" + groupId + "\"").append(",");
        sb.append("\"groupName\":")
                .append("\"" + groupName + "\"");
        sb.append('}');
        return sb.toString();
    }


}

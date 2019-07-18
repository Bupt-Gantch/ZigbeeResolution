package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class Gateway {
    String version;
    String snid;
    String username;
    String password;
    Integer deviceNumber;
    Integer groupNumber;
    Integer timerNumber;
    Integer sceneNumber;
    Integer missionNumber;
    String compileVersionNumber;

    public Gateway(){}

    public String toString(){
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"version\":")
                .append("\""+version+"\"").append(",");
        sb.append("\"snid\":")
                .append("\""+snid+"\"").append(",");
        sb.append("\"username\":")
                .append("\""+username+"\"").append(",");
        sb.append("\"password\":")
                .append("\""+password+"\"").append(",");
        sb.append("\"deviceNumber\":")
                .append("\""+deviceNumber+"\"").append(",");
        sb.append("\"groupNumber\":")
                .append("\""+groupNumber+"\"").append(",");
        sb.append("\"timerNumber\":")
                .append("\""+timerNumber+"\"").append(",");
        sb.append("\"sceneNumber\":")
                .append("\""+sceneNumber+"\"").append(",");
        sb.append("\"missionNumber\":")
                .append("\""+missionNumber+"\"").append(",");
        sb.append("\"compileVersionNumber\":")
                .append("\""+compileVersionNumber+"\"");
        sb.append('}');
        return sb.toString();
    }
}

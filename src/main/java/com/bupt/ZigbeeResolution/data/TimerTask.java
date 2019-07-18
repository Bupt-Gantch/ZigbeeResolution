package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class TimerTask {
    int taskId;
    int addressMode;
    String shortAddress;
    byte endPoint;
    int day;
    int hour;
    int minute;
    int second;
    byte taskMode;
    byte data1;
    byte data2;

    public TimerTask(){}

    public String toString(){
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"taskId\":")
                .append("\""+taskId+"\"").append(",");
        sb.append("\"addressMode\":")
                .append("\""+addressMode+"\"").append(",");
        sb.append("\"shortAddress\":")
                .append("\""+shortAddress+"\"").append(",");
        sb.append("\"endPoint\":")
                .append("\""+endPoint+"\"").append(",");
        sb.append("\"day\":")
                .append("\""+day+"\"").append(",");
        sb.append("\"hour\":")
                .append("\""+hour+"\"").append(",");
        sb.append("\"minute\":")
                .append("\""+minute+"\"").append(",");
        sb.append("\"second\":")
                .append("\""+second+"\"").append(",");
        sb.append("\"taskMode\":")
                .append("\""+taskMode+"\"").append(",");
        sb.append("\"data1\":")
                .append("\""+data1+"\"").append(",");
        sb.append("\"data2\":")
                .append("\""+data2+"\"");
        sb.append('}');
        return sb.toString();
    }

}

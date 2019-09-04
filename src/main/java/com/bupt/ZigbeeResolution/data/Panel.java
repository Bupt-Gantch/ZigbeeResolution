package com.bupt.ZigbeeResolution.data;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Panel {
    Integer id;
    String name;
    Integer type;
    Timestamp timestamp;

    public Panel(){}

    @Override
    public String toString() {
        return "Panel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", timestamp=" + timestamp +
                '}';
    }
}

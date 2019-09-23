package com.bupt.ZigbeeResolution.data;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Panel {
    Integer id;
    String name;
    Integer type;
    Timestamp timestamp;
    Integer condition;

    public Panel(){}

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"name\":\"" + name + '\"' +
                ", \"type\":\"" + type + '\"' +
                ", \"timestamp\":\"" + timestamp + '\"' +
                ", \"condition\":\"" + condition + '\"' +
                '}';
    }
}

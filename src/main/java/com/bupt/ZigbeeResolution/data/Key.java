package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class Key {
    Integer id;
    Integer number;
    Integer key;
    String name;

    public Key(){}

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"number\":\"" + number + '\"' +
                ", \"key\":\"" + key + '\"' +
                ", \"name\":\"" + name + '\"' +
                '}';
    }
}

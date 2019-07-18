package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String userName;
    private String passwd;
    private String wechatName;
    private Integer role;

    public User(String userName, String passwd){
        this.userName = userName;
        this.passwd = passwd;
    }
}

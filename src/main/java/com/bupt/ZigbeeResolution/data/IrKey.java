package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class IrKey {
    public Integer id;
    public Integer key;
    public Integer device_type;
    public String name;
    public String infrared_id;
    public Integer remote_control_id;

    public IrKey(){}
}

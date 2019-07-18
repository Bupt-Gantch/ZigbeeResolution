package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class SceneDevice {
    Integer sceneDevice_id;
    Integer scene_id;
    String deviceId;
    Integer data1;
    Integer data2;
    Integer data3;
    Integer data4;

    public SceneDevice(Integer scene_id,String deviceId, Integer data1, Integer data2, Integer data3, Integer data4){
        this.scene_id = scene_id;
        this.deviceId = deviceId;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.data4 = data4;
    }

    public SceneDevice(Integer sceneDevice_id,Integer scene_id,String deviceId, Integer data1, Integer data2, Integer data3, Integer data4){
        this.sceneDevice_id =sceneDevice_id;
        this.scene_id = scene_id;
        this.deviceId = deviceId;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.data4 = data4;
    }
}

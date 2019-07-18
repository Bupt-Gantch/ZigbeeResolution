package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class SceneSelectorRelation {
    private Integer id;
    private String sceneSelectorId;
    private Integer bindType;
    private Integer scene_id;
    private String deviceId;

    public SceneSelectorRelation(String sceneSelectorId, Integer bindType, Integer scene_id, String deviceId){
        this.sceneSelectorId = sceneSelectorId;
        this.bindType = bindType;
        this.scene_id = scene_id;
        this.deviceId = deviceId;
    }

    public SceneSelectorRelation(Integer id,String sceneSelectorId, Integer bindType, Integer scene_id, String deviceId){
        this.id = id;
        this.sceneSelectorId = sceneSelectorId;
        this.bindType = bindType;
        this.scene_id = scene_id;
        this.deviceId = deviceId;
    }

    public SceneSelectorRelation(String sceneSelectorId, Integer bindType, String deviceId){
        this.sceneSelectorId = sceneSelectorId;
        this.bindType = bindType;
        this.deviceId = deviceId;
    }

    public SceneSelectorRelation(String sceneSelectorId, Integer bindType, Integer scene_id){
        this.sceneSelectorId = sceneSelectorId;
        this.bindType = bindType;
        this.scene_id = scene_id;
    }
}

package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class Scene {
    Integer scene_id;
    String sceneId;
    String sceneName;
    String sceneNickName;
    int sceneNumber;
    Integer customerId;
    String sceneSelectorId;
    String gatewayName;

    public Scene(){}

    public Scene(String sceneName, String sceneNickName, Integer customerId){
        this.sceneName = sceneName;
        this.sceneNickName = sceneNickName;
        this.customerId =customerId;
    }

    public Scene(Integer scene_id, String sceneId, String sceneName, String sceneNickName,Integer sceneNumber, Integer customerId, String sceneSelectorId, String gatewayName){
        this.scene_id = scene_id;
        this.sceneId = sceneId;
        this.sceneName = sceneName;
        this.sceneNickName = sceneNickName;
        this.sceneNumber =sceneNumber;
        this.customerId =customerId;
        this.sceneSelectorId = sceneSelectorId;
        this.gatewayName = gatewayName;
    }



    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"sceneId\":")
                .append("\"" + sceneId + "\"").append(",");
        sb.append("\"sceneName\":")
                .append("\"" + sceneName + "\"").append(",");
        sb.append("\"sceneNumber\":")
                .append("\"" + sceneNumber + "\"");
        sb.append('}');
        return sb.toString();
    }
}

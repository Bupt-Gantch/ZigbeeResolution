package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.Scene;
import com.bupt.ZigbeeResolution.mapper.SceneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneService {

    @Autowired
    private SceneMapper sceneMapper;

    public Boolean addSceneOnlySceneName(Scene scene){
        Integer i = sceneMapper.addSceneOnlySceneName(scene);
        return i==1;
    }

    public Scene getSceneBySceneName(String sceneNickName){
        return sceneMapper.getSceneByName(sceneNickName);
    }

    public Boolean updateScene(Scene scene){
        Integer i = sceneMapper.updateScene(scene);
        return i==1;
    }

    public List<Scene> getSceneByCustomerId(Integer customerId){
        return sceneMapper.getSceneByCustomerId(customerId);
    }

    public Scene getSceneBySceneId(Integer scene_id){
        return sceneMapper.getSceneBySceneId(scene_id);
    }

    public Boolean deleteSceneBySceneId(Integer scene_id){
        Integer i = sceneMapper.deleteSceneBySceneId(scene_id);
        return i==1;
    }

    public Boolean updateSceneSelector(String sceneSelectorId, Integer scene_id){
        Integer i = sceneMapper.updateSceneSelector(sceneSelectorId,scene_id);
        return  i==1;
    }

    public Boolean updateGatewayName(String gatewayName, Integer scene_id){
        Integer i = sceneMapper.updateGatewayName(gatewayName, scene_id);
        return  i == 1;
    }

    public List<Scene> getSceneByGateway(String gatewayName){return  sceneMapper.getSceneByGateway(gatewayName);}

    public Scene getSceneByGatewayAndSceneId(String gatewayName, String sceneId){return sceneMapper.getSceneByGatewayAndSceneId(gatewayName, sceneId);}

    public Integer getSceneNumber(){
        return sceneMapper.getSceneNumber();
    }
}

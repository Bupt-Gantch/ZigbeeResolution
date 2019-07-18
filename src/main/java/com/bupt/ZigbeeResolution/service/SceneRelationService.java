package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.mapper.SceneRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneRelationService {

    @Autowired
    private SceneRelationMapper sceneRelationMapper;

    public Boolean addSceneRelation(Integer main_scene_id, Integer side_scene_id){
        Integer i = sceneRelationMapper.addSceneRelation(main_scene_id, side_scene_id);
        return i==1;
    }

    public List<Integer> getSceneRelation(Integer main_scene_id){
        return sceneRelationMapper.getRelatedSceneId(main_scene_id);
    }

    public Boolean removeSceneRelation(Integer main_scene_id){
        Integer i =  sceneRelationMapper.removeRelation(main_scene_id);
        return i==1;
    }
}

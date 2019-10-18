package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.SceneSelectorRelation;
import com.bupt.ZigbeeResolution.mapper.SceneSelectorRelationMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneSelectorRelationService {

    @Autowired
    private SceneSelectorRelationMapper sceneSelectorRelationMapper;

    public Boolean addBindScene(SceneSelectorRelation sceneSelectorRelation){
        return sceneSelectorRelationMapper.addBindScene(sceneSelectorRelation)==1;
    }

    public Boolean addBindDevice(SceneSelectorRelation sceneSelectorRelation){
        return sceneSelectorRelationMapper.addBindDevice(sceneSelectorRelation)==1;
    }

    public List<SceneSelectorRelation> getBindInfoBySceneSelectorId(String sceneSelectorId){
        return sceneSelectorRelationMapper.getBindInfoBySceneSelectorId(sceneSelectorId);
    }

    public SceneSelectorRelation getBindInfoBySceneSelectorIdAndDeviceId(String sceneSelectorId, String deviceId){
        return sceneSelectorRelationMapper.getBindInfoBySceneSelectorIdAndDeviceId(sceneSelectorId, deviceId);
    }

    public Integer getBindTypeBySceneSelectorId(String sceneSelectorId){
        return sceneSelectorRelationMapper.getBindTypeBySceneSelectorId(sceneSelectorId);
    }

    public Boolean deleteBindInfo(String sceneSelectorId, String deviceId){
        return sceneSelectorRelationMapper.deleteBindInfo(sceneSelectorId,deviceId)==0;
    }

    public Boolean deleteBindInfoBySceneSelector(String sceneSelectorId){
        return sceneSelectorRelationMapper.deleteBindInfoBySceneSelector(sceneSelectorId)==0;
    }

    public Boolean deleteBindInfoByDeviceId(String deviceId){
        return sceneSelectorRelationMapper.deleteBindInfoByDeviceId(deviceId)==0;
    }

    public List<SceneSelectorRelation> getBindInfoByDeviceId(String deviceId) {
        return sceneSelectorRelationMapper.getBindInfoByDeviceId(deviceId);
    }
}

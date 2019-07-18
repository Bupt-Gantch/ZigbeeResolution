package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.SceneDevice;
import com.bupt.ZigbeeResolution.mapper.SceneDeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneDeviceService {

    @Autowired
    private SceneDeviceMapper sceneDeviceMapper;

    public Boolean addSceneDevice(SceneDevice sceneDevice){
        Integer i = sceneDeviceMapper.addSceneDevice(sceneDevice);
        return i==1;
    }

    public List<SceneDevice> getSceneDevice(Integer scene_id){
        return sceneDeviceMapper.getSceneDeviceBySceneId(scene_id);
    }

    public Boolean deleteSceneDeviceBySceneId(Integer scene_id){
        Integer i =  sceneDeviceMapper.deleteScenenDeviceBySceneId(scene_id);
        return i==1;
    }

    public SceneDevice getSceneDeviceBySceneIdAndDeviceId(Integer scene_id, String deviceId){
        return sceneDeviceMapper.getSceneDeviceBySceneIdAndDeviceId(scene_id, deviceId);
    }

    public Boolean updateSceneDevice(SceneDevice sceneDevice){
        Integer i = sceneDeviceMapper.updateSceneDevice(sceneDevice);
        return  i==1;
    }
}

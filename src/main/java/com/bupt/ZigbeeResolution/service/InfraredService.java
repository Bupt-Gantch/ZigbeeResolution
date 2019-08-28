package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.Learn;
import com.bupt.ZigbeeResolution.mapper.InfraredMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InfraredService {

    @Resource
    InfraredMapper irMapper;

    public Integer findKey(String deviceId, Integer key) {
        return irMapper.select_by_key_deviceId(deviceId, key);
    }

    public void addKey(String deviceId, Integer key, String name, Integer matchType, Integer customerId, Integer buttonId,
                       Integer panelId, Integer state) {
        irMapper.insert(deviceId, key, name, matchType, customerId, buttonId, panelId, state);
    }

    public Integer get_maxkey_of_airCondition(String deviceId) {
        return irMapper.select_maxnum_of_airCondition(deviceId);
    }

    public Integer get_maxkey_of_non_airConditon(String deviceId) {
        return irMapper.select_maxnum_of_non_airCondition(deviceId);
    }

    public void deleteKey(String deviceId, Integer key) {
        irMapper.delete_a_key(deviceId, key);
    }

    public void deleteAllKey(String deviceId) {
        irMapper.delete_all_key(deviceId);
    }

    public void updateState(String deviceId, Integer key) {
        irMapper.updateStateByResult(deviceId, key);
    }

    public List<Learn> getCustomerAllLearns(String deviceId, Integer customerId) {
        List<Learn> learns = irMapper.selectCustomerAllLearns(deviceId, customerId);
        return learns;
    }

    public List<Learn> getCustomerPanelLearn(String deviceId, Integer customerId, Integer panelId) {
        List<Learn> keyNames = irMapper.selectCustomerPanelLearn(deviceId, customerId, panelId);
        return keyNames;
    }

    public List<Learn> getDevicePanelLearn(String deviceId, Integer panelId){
        List<Learn> deviceLearns = irMapper.selectDevicePanelLearn(deviceId,panelId);
        return  deviceLearns;
    }

    public List<Learn> getDeviceAllLearns(String deviceId){
        List<Learn> allLearns = irMapper.selectDeviceAllLearns(deviceId);
        return allLearns;
    }

    public Learn get_a_learn(String deviceId, Integer panelId, Integer buttonId){
        Learn learn = irMapper.select_a_learn(deviceId,panelId,buttonId);
        return learn;
    }

    public void delPanel(String deviceId, Integer panelId){
        irMapper.delPanel(deviceId,panelId);
    }

    public void delPanels(String deviceId,List<Integer> panelIds){
        irMapper.delPanels(deviceId,panelIds);
    }
}

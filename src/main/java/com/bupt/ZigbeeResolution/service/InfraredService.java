package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.InfraredKey;
import com.bupt.ZigbeeResolution.data.InfraredPanel;
import com.bupt.ZigbeeResolution.data.Learn;
import com.bupt.ZigbeeResolution.mapper.InfraredMapper;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfraredService {

    @Autowired
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

    public List<Learn> getCustomerLearns(String deviceId, Integer customerId) {
        List<Learn> learns = irMapper.selectCustomerLearn(deviceId, customerId);
        return learns;
    }

    public List<String> getKeyNames(String deviceId, Integer customerId, Integer panelId) {
        List<String> keyNames = irMapper.selectKeyNames(deviceId, customerId, panelId);
        return keyNames;
    }

    public List<String> getDeviceLearns(String deviceId, Integer panelId){
        List<String> deviceLearns = irMapper.selectDevicelearns(deviceId,panelId);
        return  deviceLearns;
    }

    public List<Learn> getAllLearns(String deviceId){
        List<Learn> allLearns = irMapper.selectAllLearns(deviceId);
        return allLearns;
    }


    /**
     * @author zhs
     * @time 2019/8/21
     */

    public List<InfraredPanel> getPanels(String deviceId){

        List<InfraredPanel> panels = null;
        if (deviceId.equals("")) {
            return null;
        }

        try {
            panels = irMapper.select_all_panels(deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return panels;
    }

    public InfraredPanel getPanel(String deviceId, Integer panelId) {
        InfraredPanel panel = irMapper.select_panel(deviceId, panelId);
        return panel;
    }

    public int addPanel(JsonObject data) {

        String deviceId = data.get("deviceId").getAsString();
        if (deviceId.equals("")){
            return -1;
        }
        String name = data.get("name").getAsString();
        Integer type = data.get("type").getAsInt();

        InfraredPanel panel = new InfraredPanel();
        panel.setDeviceId(deviceId);
        panel.setName(name);
        panel.setType(type);

        int insert  = irMapper.insert_panel(panel);

        return insert;
    }

    public int deletePanels(String deviceId) {

        if (deviceId.equals("")) {
            return -1;
        }

        int delete = 0;
        try {
            List<InfraredPanel> panels = irMapper.select_all_panels(deviceId);
            for (InfraredPanel panel : panels) {
                irMapper.delete_all_keys(panel.getPanelId());
            }
            delete = irMapper.delete_all_panels(deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delete;
    }

    public int deletePanel(String deviceId, Integer panelId) {

        if (deviceId.equals("") || panelId == null) {
            return -1;
        }

        int delete = 0;
        try {
            irMapper.delete_all_keys(panelId);
            delete = irMapper.delete_panel(deviceId, panelId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delete;
    }

    public int updatePanel(JsonObject data) {

        String deviceId = data.get("deviceId").getAsString();
        Integer panelId = data.get("panelId").getAsInt();
        String name = data.get("name").getAsString();
        Integer type = data.get("type").getAsInt();

        if (deviceId.equals("") || panelId == null) {
            return -1;
        }

        InfraredPanel cachePanel = irMapper.select_panel(deviceId, panelId);
        if (cachePanel == null) {
            return -1;
        }

        InfraredPanel panel = new InfraredPanel();
        panel.setDeviceId(deviceId);
        panel.setPanelId(panelId);
        panel.setName(name);
        panel.setType(type);

        int update = irMapper.update_panel(panel);
        return update;
    }

    public List<InfraredKey> getKeys(Integer panelId) {

        if (panelId == null) {
            return null;
        }

        List<InfraredKey> keys = null;
        try{
            keys = irMapper.select_all_keys(panelId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keys;
    }

    public InfraredKey addKey(JsonObject data) {

        Integer panelId = data.get("panelId").getAsInt();
        Integer number = data.get("number").getAsInt();
        String name = data.get("name").getAsString();

        InfraredKey key = new InfraredKey();
        key.setPanelId(panelId);
        key.setNumber(number);
        key.setName(name);

        int insert = irMapper.insert_key(key);
        if (insert > 0) {
            return key;
        }
        return null;
    }

    public int deleteKeys(Integer panelId) {

        if (panelId == null) {
            return -1;
        }
        int delete = irMapper.delete_all_keys(panelId);
        return delete;
    }

    public int deleteKey(Integer panelId, Integer keyId) {

        if (panelId == null) {
            return -1;
        }
        int delete = irMapper.delete_key(panelId, keyId);
        return delete;
    }

    public int updateKey(JsonObject data) {
        Integer panelId = data.get("panelId").getAsInt();
        Integer keyId = data.get("keyId").getAsInt();
        Integer number = data.get("number").getAsInt();
        String name = data.get("name").getAsString();

        InfraredKey key = new InfraredKey();
        key.setPanelId(panelId);
        key.setKeyId(keyId);
        key.setNumber(number);
        key.setName(name);

        int update = irMapper.update_key(key);
        return update;
    }

}

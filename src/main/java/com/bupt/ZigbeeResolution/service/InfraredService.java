package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.AirConditionKey;
import com.bupt.ZigbeeResolution.data.Key;
import com.bupt.ZigbeeResolution.data.Learn;
import com.bupt.ZigbeeResolution.data.Panel;
import com.bupt.ZigbeeResolution.mapper.InfraredMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
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
        return irMapper.elect_maxkey_of_airCondition(deviceId);
    }

    public Integer get_maxkey_of_non_airConditon(String deviceId) {
        return irMapper.select_maxkey_of_non_airCondition(deviceId);
    }

    public Integer get_maxkey(String deviceId){
        return irMapper.select_maxkey(deviceId);
    }

//    public void deleteKey(String deviceId, Integer key) {
//        irMapper.delete_a_key(deviceId, key);
//    }

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

    // =================================================================================
    // =================================================================================
    // =================================================================================

    public Panel findPanel(Integer id){
        if (id < 0 ) {
            return null;
        }
        Panel p = irMapper.select_panel_by_id(id);
        return p;
    }

    public List<Panel> findPanels(String deviceId, Integer sort) {
        if ("".equals(deviceId)) {
            return null;
        }

        return irMapper.select_panels_by_deviceId(deviceId, sort);

    }

    public int deletePanel(Integer panelId){
        if (findPanel(panelId) == null) {
            return 0;
        }
        return irMapper.delete_panel_by_id(panelId);
    }

    public int deletePanel(String deviceId, Integer id) {
        if ("".equals(deviceId)) {
            return 0;
        }

        if (irMapper.delete_panel_relation(deviceId, id) != 0){
            List<Key> ks = irMapper.select_keys_by_panelId(id);
            if (deletePanel(id) != 0){
                if (ks != null){
                    int delete = 0;
                    for (Key k : ks){
                        if (k.getId() != null) {
                            deleteKey(id, k.getId());
                            delete ++;
                        }
                    }
                    return delete;
                }
            }
        }
        return 0;

    }

    public int deletePanels(String deviceId){
        if ("".equals(deviceId)) {
            return 0;
        }
        List<Panel> ps = irMapper.select_panels_by_deviceId(deviceId, 0);
        if (null == ps) {
            return 0;
        }
        int delete = 0;
        for (Panel p: ps) {
            try {
                deletePanel(deviceId, p.getId());
                delete += 1;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return delete;
    }

    public int addPanel(String deviceId, JsonObject data) {
        if("".equals(deviceId)) {
            return 0;
        }

        String name = data.get("name").getAsString();
        Integer type = data.get("type").getAsInt();
        Timestamp time = new Timestamp(new Date().getTime());

        Panel p = new Panel();
        p.setName(name);
        p.setTimestamp(time);
        p.setType(type);
        p.setCondition(13);

        if (0== irMapper.insert_panel(p)){
            return 0;
        }
        int insert = irMapper.insert_panel_relation(deviceId, p.getId());
        if (insert == 0) {
            return 0;
        }
        return p.getId();
    }

    public int updatePanel(Integer panelId, JsonObject data) {
        if(panelId == null) {
            return 0;
        }

        Panel oldp = irMapper.select_panel_by_id(panelId);
        if (null == oldp){
            return 0;
        }

        String name = data.get("name").getAsString();
        Integer type = data.get("type").getAsInt();
        Timestamp time = new Timestamp(new Date().getTime());

        Panel p = new Panel();
        p.setId(panelId);
        p.setName(name);
        p.setTimestamp(time);
        p.setType(type);

        return irMapper.update_panel(p);

    }

    public Key findAKey(Integer keyId) {
        return irMapper.select_key_by_id(keyId);
    }

    public Key findAKey(Integer panelId, Integer key){
        return irMapper.select_key_by_panelId_key(panelId, key);
    }

    public List<Key> findKeys(Integer id){
        if (id < 0 || null == findPanel(id)) {
            return null;
        }
        return irMapper.select_keys_by_panelId(id);
    }

    public int addAKey(Integer panelId, Integer number, Integer key, String name){
        if (findPanel(panelId) == null) {
            return 0;
        }
        Key k = new Key();
        k.setKey(key);
        k.setName(name);
        k.setNumber(number);

        try{
            irMapper.insert_key(k);
            if(k.getId() == null){
                return 0;
            }
            return irMapper.insert_panel_key_relation(panelId, k.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deletePanelKeyRelation(Integer panelId, Integer keyId){
        return irMapper.delete_panel_key_relation(panelId, keyId);
    }

    public int deleteKey(Integer keyId){
        return irMapper.delete_key_by_id(keyId);
    }

    public int deleteKey(Integer panelId, Integer keyId){
        int delete = 0;
        delete = deletePanelKeyRelation(panelId,keyId);
        delete = deleteKey(keyId);
        return delete;

    }

    public int deleteKey(String deviceId, Integer key){
        Pair<Integer, Integer> relation = irMapper.select_key_by_deviceId_key(deviceId, key);
        if (relation == null) {
            return 0;
        }
        return deleteKey(relation.getKey(),relation.getValue());

    }

    public int deleteKeys(Integer id) {
        if (null == findPanel(id)) {
            return 0;
        }

        int delete = 0;
        List<Key> ks = irMapper.select_keys_by_panelId(id);
        for (Key k : ks) {
            try {
                deleteKey(id, k.getId());
                delete += 1;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return delete;
    }

    public int updateKey(Integer id, JsonObject data){
//        if (findAKey(id) == null) {
//            return 0;
//        }
//        Integer number = data.get("number").getAsInt();
//        Integer key = data.get("key").getAsInt();
        String name = data.get("name").getAsString();

//        Key k = new Key();
//        k.setId(id);
//        k.setNumber(number);
//        k.setName(name);
//        k.setKey(key);

        return updateKeyName(id, name);
    }

    public int updateKeyName(Integer keyId, String name){
        return irMapper.update_keyName(keyId, name);
    }

    public int getAirConditionPresetKey(String power, String mode, String windLevel, String windDirectin, String tem) {
        return irMapper.select_key_of_AirCondition(power, mode, windLevel, windDirectin, tem);
    }

    public int updatePanelCondition(Integer panelId, Integer condition){
        try{
            return irMapper.update_panel_condition(panelId, condition);
        } catch (JsonParseException e){
            System.err.println("condition is not json string");
            return 0;
        }
    }


    public AirConditionKey getAirConditionKeyAttributes(Integer id){
        return irMapper.select_airconditionKey_attributes(id);
    }

}

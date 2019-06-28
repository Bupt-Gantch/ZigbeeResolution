package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.IrKey;
import com.bupt.ZigbeeResolution.data.IrRemoteControl;
import com.bupt.ZigbeeResolution.mapper.IrControlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IrService {

    @Autowired
    IrControlMapper irControlMapper;

    public List<IrRemoteControl> getAllRemoteControl(String infraredId)throws Exception{
        return irControlMapper.getAllRemoteControlByCustomerid(infraredId);
    }

    public List<IrKey> getKeys(Integer remoteControlId) throws Exception{
        List<IrKey> irKeys = new ArrayList<>();

        // 获取遥控器下所有按键id
        IrRemoteControl irRemoteControl = irControlMapper.getAllKeys(remoteControlId);
        String presetKey = irRemoteControl.getPreset_key();
        String customKey = irRemoteControl.getCustom_key();

        // 合并预设的按键和用户自定义按键 id
        String keyStr = new String();
        if (!customKey.equals("") && !presetKey.equals("")) {
            keyStr = presetKey + "," + customKey;
        } else if(!customKey.equals("") && customKey != null) {
            keyStr = customKey;
        } else if(!presetKey.equals("") && customKey != null) {
            keyStr = presetKey;
        } else {  // an remote control without any key
            return irKeys;
        }

        // transform String to Array
        String[] keyStrArray = keyStr.split(",");
        // get key info one by one
        for(int i = 0; i<keyStrArray.length; i++) {
            Integer keyId = Integer.parseInt(keyStrArray[i]);
            IrKey irKey = irControlMapper.getAKeyById(keyId);
            if(irKey != null)
                irKeys.add(irKey);
        }
        return irKeys;
    }

    public Integer getMaxKey(Integer device_type) throws Exception{
        return irControlMapper.getMaxKey(device_type);
    }

    public Integer learn(Integer device_type, String name, String infraredId, Integer remoteControlId) throws Exception{
        IrKey irKey = new IrKey();
        irKey.setDevice_type(device_type);
        irKey.setName(name);
        irKey.setInfrared_id(infraredId);
        irKey.setRemote_control_id(remoteControlId);

        if (device_type == 1) {  // air condition
            if (irControlMapper.getKey(603, device_type) == null){
                irKey.setKey(603);
                irControlMapper.addInitKey(irKey);
            } else {
                irControlMapper.addAirConditionKey(irKey);
            }
        } else { // not air condition
            if (irControlMapper.getKey(44, device_type) == null){
                irKey.setKey(44);
                irControlMapper.addInitKey(irKey);
            } else {
                irControlMapper.addKey(irKey);
            }
        }
        // return the new key value to control
        return irKey.getKey();
    }

    public void createNewRemoteControl(IrRemoteControl rc) throws Exception{
        irControlMapper.addRemoteControl(rc);
    }

    public void removeAllKeys(Integer remoteControlId) throws Exception{
        // get all custom keys
        String customKeysStr = irControlMapper.getAllKeys(remoteControlId).getCustom_key();
        String[] keyIdStrArray = customKeysStr.split(",");
        // delete all custom keys in ir_key
        for(String keyIdStr: keyIdStrArray){
            try {
                Integer keyId = Integer.parseInt(keyIdStr);
                removeKey(keyId);
            } catch (Exception e) {
                System.out.println(String.format("ibatis exception [delete custom key] id: %s", keyIdStr));
            }
        }
    }

    public void removeKey(Integer keyId)throws Exception{
        if (keyId > 43)  // custom keys number begins at 44
            irControlMapper.removeKey(keyId);
    }

    public void removeAllRemoteControl(String infraredId) throws Exception{
        List<Integer> remoteControlIds = irControlMapper.getAllRemoteControlIdByCustomerid(infraredId);
        for (Integer id : remoteControlIds) {
            try {
                removeRemoteControl(id);
            } catch (Exception e) {
                System.out.println(String.format("ibatis exception [delete remote control] id: %d", id));
            }
        }
    }

    public void removeRemoteControl(Integer remoteControlId) throws Exception{
        if (remoteControlId >4) { // 预设4种通用的遥控器
            // delete all custom key in remote control
            removeAllKeys(remoteControlId);
            // delete remote control
            irControlMapper.removeIrRemoteControlById(remoteControlId);
        }
    }

    public void renameKey(Integer keyId, String name) throws Exception{
        irControlMapper.renameKey(keyId, name);
    }

    public void renameRemoteControl(Integer remoteControlId, String name) throws Exception{
        irControlMapper.renameRemoteControl(remoteControlId, name);
    }

    public void updateRemoteControlKeys(Integer remoteControlId, String custom_keys) throws Exception{
        irControlMapper.updateRemoteControlCustomKeys(remoteControlId, custom_keys);
    }
}

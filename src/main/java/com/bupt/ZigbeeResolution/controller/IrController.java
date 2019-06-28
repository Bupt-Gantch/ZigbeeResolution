package com.bupt.ZigbeeResolution.controller;

import com.bupt.ZigbeeResolution.data.IrKey;
import com.bupt.ZigbeeResolution.data.IrRemoteControl;
import com.bupt.ZigbeeResolution.service.IrService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/infrared")
public class IrController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IrService irService;

    @RequestMapping(value = "/get/remoteControl/all/{infraredId}", method = RequestMethod.GET)
    List<IrRemoteControl> getAllRemoteControl(@PathVariable("infraredId")String infraredId){
        List<IrRemoteControl> irRemoteControls= new ArrayList<>();

        try{
            irRemoteControls = irService.getAllRemoteControl(infraredId);
        } catch(Exception e){
            logger.error("ibatis Exception:" + e.getMessage());
        }
        return irRemoteControls;
    }

    @RequestMapping(value = "/get/key/all/{remoteControlId}", method = RequestMethod.GET)
    List<IrKey> getKeys(@PathVariable("remoteControlId") Integer remoteControlId){
        List<IrKey> irKeys = new ArrayList<>();

        try{
            irKeys = irService.getKeys(remoteControlId);
        } catch(Exception e){
            logger.error("ibatis exception:" + e.getMessage());
        }
        return irKeys;
    }

    @RequestMapping(value = "/update/key/{remoteControlId}", method = RequestMethod.POST)
    void updateKeys(@PathVariable("remoteControlId")Integer remoteControlId, @RequestBody String body){
        JsonObject json = (JsonObject)new JsonParser().parse(body);
        String keyStr = json.get("keys").getAsString();

        try {
            irService.updateRemoteControlKeys(remoteControlId, keyStr);
        } catch (Exception e) {
            logger.error("ibatis exception:" + e.getMessage());
        }
    }

    @RequestMapping(value = "/remove/remoteControl/all/{infraredId}", method = RequestMethod.DELETE)
    void removeAllRemoteControl(@PathVariable("infraredId")String infraredId){

        try {
            irService.removeAllRemoteControl(infraredId);
        } catch (Exception e) {
            logger.error("ibatis exception:" + e.getMessage());
        }
    }

    @RequestMapping(value = "/remove/remoteControl/{remoteControlId}", method = RequestMethod.DELETE)
    void removeRemoteControl(@PathVariable("remoteControlId")Integer remoteControlId){

        try {
            irService.removeRemoteControl(remoteControlId);
        } catch (Exception e) {
            logger.error("ibatis exception:" + e.getMessage());
        }
    }

    @RequestMapping(value = "/remove/key/all/{remoteControlId}", method = RequestMethod.DELETE)
    void removeAllKeys(@PathVariable("remoteControlId")Integer remoteControlId){

        try {
            irService.removeAllKeys(remoteControlId);
        } catch (Exception e) {
            logger.error("ibatis exception:" + e.getMessage());
        }
    }

    @RequestMapping(value = "/remove/key/{keyId}", method = RequestMethod.DELETE)
    void removeKey(@PathVariable("keyId")Integer keyId){

        try {
            irService.removeKey(keyId);
        } catch (Exception e) {
            logger.error("ibatis exception:" + e.getMessage());
        }
    }

    @RequestMapping(value = "/rename/remoteControl/{remoteControlId}", method = RequestMethod.POST)
    void renameRemoteControl(@PathVariable("remoteControlId")Integer remoteControlId, @RequestBody String body){
        JsonObject json = (JsonObject)new JsonParser().parse(body);
        String name = json.get("name").getAsString();

        try {
            irService.renameRemoteControl(remoteControlId, name);
        } catch (Exception e) {
            logger.error("ibatis exception:" + e.getMessage());
        }
    }

    @RequestMapping(value = "/rename/key/{keyId}", method = RequestMethod.POST)
    void renameKey(@PathVariable("keyId")Integer keyId, @RequestBody String body){
        JsonObject json = (JsonObject)new JsonParser().parse(body);
        String name = json.get("name").getAsString();

        try {
            irService.renameKey(keyId, name);
        } catch (Exception e) {
            logger.error("ibatis exception:" + e.getMessage());
        }
    }

    @RequestMapping(value = "/add/key/learn", method = RequestMethod.POST)
    Integer learnKey(@RequestBody String body){
        JsonObject json = (JsonObject)new JsonParser().parse(body);
        Integer device_type = json.get("device_type").getAsInt();
        String name = json.get("name").getAsString();
        String infrared_id = json.get("infraredId").getAsString();
        Integer remoteControlId = json.get("remoteControlId").getAsInt();

        Integer key = -1;
        try {
            key = irService.learn(device_type, name, infrared_id, remoteControlId);
        } catch (Exception e) {
            logger.error("ibatis exception:" + e.getMessage());
        }
        return key;
    }

    @RequestMapping(value = "/add/remoteControl/save", method = RequestMethod.POST)
    void createRemoteControl(@RequestBody String body){
        JsonObject json = (JsonObject)new JsonParser().parse(body);

        String infraredId = json.get("infraredId").getAsString();
        String name = json.get("name").getAsString();
        Integer device_type = json.get("device_type").getAsInt();
        Integer secondary_type = json.get("secondary_type").getAsInt();
        String custom_key = json.get("custom_key").getAsString();
        IrRemoteControl irRemoteControl = new IrRemoteControl();
        irRemoteControl.setInfrared_id(infraredId);
        irRemoteControl.setName(name);
        irRemoteControl.setDevice_type(device_type);
        irRemoteControl.setSecondary_type(secondary_type);
        irRemoteControl.setCustom_key(custom_key);
        try {
            irService.createNewRemoteControl(irRemoteControl);
        } catch (Exception e) {
            logger.error("ibatis exception:" + e.getMessage());
        }
    }
}

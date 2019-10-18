package com.bupt.ZigbeeResolution.controller;

import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
import com.bupt.ZigbeeResolution.data.GatewayGroup;
import com.bupt.ZigbeeResolution.data.SceneSelectorRelation;
import com.bupt.ZigbeeResolution.method.GatewayMethod;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.GatewayGroupService;
import com.bupt.ZigbeeResolution.service.SceneSelectorRelationService;
import com.bupt.ZigbeeResolution.utils.Operation;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Operation(name="物接入")
@RestController
@RequestMapping("api/v1/device")
public class DeviceController {

    @Autowired
    private DeviceTokenRelationService deviceTokenRelationService;

    @Autowired
    private GatewayGroupService gatewayGroupService;

    @Autowired
    private SceneSelectorRelationService sceneSelectorRelationService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(name="删除设备")
    @RequestMapping(value = "/deleteDevice/{deviceId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteDevice(@PathVariable("deviceId")String deviceId ){
        DeviceTokenRelation singleDeviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
        if (singleDeviceTokenRelation == null) {
            logger.warn("device %s not exists", deviceId);
            return "error";
        }

        GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(singleDeviceTokenRelation.getGatewayName());
        GatewayMethod gatewayMethod = new GatewayMethodImpl();
        if(gatewayGroup == null || gatewayGroup.getIp()==null){
            logger.warn("Gateway " + singleDeviceTokenRelation.getGatewayName() + " is offline");
//            return "error";
        }

        Device device = new Device();
        device.setShortAddress(singleDeviceTokenRelation.getShortAddress());
        device.setEndpoint(singleDeviceTokenRelation.getEndPoint().byteValue());
        device.setIEEE(singleDeviceTokenRelation.getIEEE());

        // 删除设备
        gatewayMethod.deleteDevice(device, gatewayGroup.getIp());

        // 删除场景
        List<DeviceTokenRelation> allDevices = deviceTokenRelationService.getRelationByIEEE(singleDeviceTokenRelation.getIEEE());
        for(DeviceTokenRelation eachDevice: allDevices){
            if(sceneSelectorRelationService.getBindInfoByDeviceId(eachDevice.getUuid()).size() == 0 ){
                continue;
            }
            if(sceneSelectorRelationService.deleteBindInfoByDeviceId(eachDevice.getUuid())){
                logger.error("database operation exception: fail to delete record in senceSelectorRelation");
                return "error";
            }
        }

        // 删除设备token
        if(!deviceTokenRelationService.deleteDeviceByIEEE(singleDeviceTokenRelation.getIEEE())){
            logger.error("database operation exception: fail to delete record in deviceTokenRelation");
            return "error";
        }

        logger.info("delete device successfully.");
        return "success";
    }

    @Operation(name="设备入网")
    @RequestMapping(value = "/addNewDevice/{gatewayName}", method = RequestMethod.GET)
    @ResponseBody
    public String addMewDevice(@PathVariable("gatewayName") String gatewayName){
        String[] gateway = gatewayName.split("_");
        String gatewayNumber = gateway[1];

        GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(gatewayNumber);

        if(gatewayGroup.getIp()==null){
            logger.warn("Gateway " + gatewayName + " is offline");
//            return "error";
        }
        GatewayMethod gatewayMethod = new GatewayMethodImpl();
        gatewayMethod.permitDeviceJoinTheGateway(gatewayGroup.getIp());

        logger.info("success");
        return "success";
    }

    @Operation(name="场景绑定设备")
    @RequestMapping(value = "/sceneSelectorBindDevice", method = RequestMethod.POST)
    @ResponseBody
    public String sceneSelectorBindDevice(@RequestBody String deviceInfo){
        int i=0;
        JsonObject jsonObject = (JsonObject)new JsonParser().parse(deviceInfo);
        String sceneSelectorId = jsonObject.get("sceneSelectorId").getAsString();

        Integer bindType = sceneSelectorRelationService.getBindTypeBySceneSelectorId(sceneSelectorId);
        if(bindType!=null){
            if(bindType==1){
                try {
                    sceneSelectorRelationService.deleteBindInfoBySceneSelector(sceneSelectorId);
                } catch (Exception e){
                    logger.error("database operation exception: fail to delete record in sceneSelectorRelation");
                    e.printStackTrace();
                    return "error";
                }
            }
        }

        DeviceTokenRelation sceneSelectorTokenRelation = deviceTokenRelationService.getRelationByUuid(sceneSelectorId);
        if (sceneSelectorTokenRelation == null) {
            logger.warn("sceneSelector %s not exists", sceneSelectorId);
            return "error";
        }

        GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(sceneSelectorTokenRelation.getGatewayName());
        if(gatewayGroup.getIp()==null){
            logger.warn("Gateway "+ sceneSelectorTokenRelation.getGatewayName() +" is offline");
//            return "error";
        }

        Device sceneSelector = new Device();
        sceneSelector.setShortAddress(sceneSelectorTokenRelation.getShortAddress());
        sceneSelector.setEndpoint(sceneSelectorTokenRelation.getEndPoint().byteValue());
        sceneSelector.setIEEE(sceneSelectorTokenRelation.getIEEE());

        JsonArray jsonArray = jsonObject.getAsJsonArray("deviceId");
        for(JsonElement jsonElement:jsonArray){
            String deviceId = jsonElement.getAsString();
            DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
            Device device = new Device();
            device.setEndpoint(deviceTokenRelation.getEndPoint().byteValue());
            device.setIEEE(deviceTokenRelation.getIEEE());
            if(!deviceTokenRelation.getGatewayName().equals(sceneSelectorTokenRelation.getGatewayName())){
                i++;
            }else{
                SceneSelectorRelation sceneSelectorRelation = sceneSelectorRelationService.getBindInfoBySceneSelectorIdAndDeviceId(sceneSelectorId,deviceId);
                if(sceneSelectorRelation==null){
                    SceneSelectorRelation newSceneSelectorRelation = new SceneSelectorRelation(sceneSelectorId,2,deviceId);
                    sceneSelectorRelationService.addBindDevice(newSceneSelectorRelation);
                }
                GatewayMethod gatewayMethod = new GatewayMethodImpl();
                gatewayMethod.setSwitchBindDevice(sceneSelector,device,gatewayGroup.getIp());
            }
        }
        if(i!=0){
            logger.warn("%d devices are not in the same gateway as the scene selector");
            return "error";
        }
        return "success";
    }

    @Operation(name="获取场景绑定的设备")
    @RequestMapping(value = "/getSceneSelectorBind/{sceneSelectorId}", method = RequestMethod.GET)
    @ResponseBody
    public String getSceneSelectorBind(@PathVariable("sceneSelectorId") String sceneSelectorId){
        List<SceneSelectorRelation> sceneSelectorRelations = sceneSelectorRelationService.getBindInfoBySceneSelectorId(sceneSelectorId);
        JsonObject jsonObject = new JsonObject();

        if(sceneSelectorRelations == null || sceneSelectorRelations.size()==0){
            logger.warn("scene binds no device yet.");
            return jsonObject.toString();
        }

        if(sceneSelectorRelations.get(0).getBindType()==1){
            jsonObject.addProperty("bindType","scene");
            jsonObject.addProperty("scene_id", sceneSelectorRelations.get(0).getScene_id());

        }else if(sceneSelectorRelations.get(0).getBindType()==2){
            jsonObject.addProperty("bindType","device");
            JsonArray jsonArray = new JsonArray();
            for(SceneSelectorRelation sceneSelectorRelation:sceneSelectorRelations){
                jsonArray.add(sceneSelectorRelation.getDeviceId());
            }
            jsonObject.add("deviceIds",jsonArray);
        }

        return jsonObject.toString();
    }

    @Operation(name="删除场景已经绑定的设备")
    @RequestMapping(value = "/deleteSceneSelectorBind", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteSceneSelector(@RequestBody String deviceInfo){
        JsonObject jsonObject = (JsonObject)new JsonParser().parse(deviceInfo);
        String sceneSelectorId = jsonObject.get("sceneSelectorId").getAsString();

        DeviceTokenRelation sceneSelectorTokenRelation = deviceTokenRelationService.getRelationByUuid(sceneSelectorId);
        if (sceneSelectorTokenRelation == null){
            logger.warn("sceneSelector %s not exists", sceneSelectorId);
            return "error";
        }

        GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(sceneSelectorTokenRelation.getGatewayName());
        if(gatewayGroup.getIp()==null){
            logger.warn("Gateway "+sceneSelectorTokenRelation.getGatewayName()+" is offline");
//            return "error";
        }

        Device sceneSelector = new Device();
        sceneSelector.setShortAddress(sceneSelectorTokenRelation.getShortAddress());
        sceneSelector.setEndpoint(sceneSelectorTokenRelation.getEndPoint().byteValue());
        sceneSelector.setIEEE(sceneSelectorTokenRelation.getIEEE());

        JsonArray jsonArray = jsonObject.getAsJsonArray("deviceId");
        for(JsonElement jsonElement:jsonArray) {
            String deviceId = jsonElement.getAsString();
            DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
            Device device = new Device();
            device.setEndpoint(deviceTokenRelation.getEndPoint().byteValue());
            device.setIEEE(deviceTokenRelation.getIEEE());
            GatewayMethod gatewayMethod = new GatewayMethodImpl();
            gatewayMethod.cancelBindOfSwitchAndDevice(sceneSelector, device, gatewayGroup.getIp());
            if(sceneSelectorRelationService.deleteBindInfo(sceneSelectorId,deviceId)){
                logger.error("database operation exception: fail to delete record in sceneSelectorRelation");
                return "error";
            }
        }
        return "success";
    }

    @Operation(name="测试")
    @RequestMapping(value = "/test/{gatewayName}", method = RequestMethod.GET)
    @ResponseBody
    public String test(@PathVariable("gatewayName") String gatewayName){
        int i = 1;
        if(i==1){
            //在此试验是否写入数据库
            logger.error("测试错误");
        }
        logger.info("测试信息");
        logger.warn("测试警告");
        return "success";
    }
}

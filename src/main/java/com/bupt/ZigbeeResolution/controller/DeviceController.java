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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/deleteDevice/{deviceId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteDevice(@PathVariable("deviceId")String deviceId ){
        DeviceTokenRelation singleDeviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);

        Device device = new Device();
        device.setShortAddress(singleDeviceTokenRelation.getShortAddress());
        device.setEndpoint(singleDeviceTokenRelation.getEndPoint().byteValue());
        device.setIEEE(singleDeviceTokenRelation.getIEEE());

        GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(singleDeviceTokenRelation.getGatewayName());
        GatewayMethod gatewayMethod = new GatewayMethodImpl();
        if(gatewayGroup.getIp()==null){
            logger.warn("Gateway[%s] is offline", singleDeviceTokenRelation.getGatewayName());
            //return "error";
        }
        gatewayMethod.deleteDevice(device, gatewayGroup.getIp());

        List<DeviceTokenRelation> allDevices = deviceTokenRelationService.getRelationByIEEE(singleDeviceTokenRelation.getIEEE());
        for(DeviceTokenRelation eachDevice: allDevices){
            if(sceneSelectorRelationService.deleteBindInfoByDeviceId(eachDevice.getUuid())){
                logger.error("database operation exception: fail to delete record in senceSelectorRelation");
                //return "some error happen in delete sceneSelector relation";
            }
        }

        if(deviceTokenRelationService.deleteDeviceByIEEE(singleDeviceTokenRelation.getIEEE())){
            logger.error("database operation exception: fail to delete record in deviceTokenRelation");
            //return "error";
        }
        logger.info("delete device successfully.");
        return "success";
    }

    @RequestMapping(value = "/addNewDevice/{gatewayName}", method = RequestMethod.GET)
    @ResponseBody
    public String addMewDevice(@PathVariable("gatewayName") String gatewayName){
        String[] gateway = gatewayName.split("_");
        String gatewayNumber = gateway[1];

        GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(gatewayNumber);

        if(gatewayGroup.getIp()==null){
            logger.warn("Gateway[%s] is offline", gatewayName);
            return "error|Gateway Offline";
        }
        GatewayMethod gatewayMethod = new GatewayMethodImpl();
        gatewayMethod.permitDeviceJoinTheGateway(gatewayGroup.getIp());

        return "success";
    }

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
                    System.err.println(e.getMessage());
                }
            }
        }

        DeviceTokenRelation sceneSelectorTokenRelation = deviceTokenRelationService.getRelationByUuid(sceneSelectorId);
        Device sceneSelector = new Device();
        sceneSelector.setShortAddress(sceneSelectorTokenRelation.getShortAddress());
        sceneSelector.setEndpoint(sceneSelectorTokenRelation.getEndPoint().byteValue());
        sceneSelector.setIEEE(sceneSelectorTokenRelation.getIEEE());
        GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(sceneSelectorTokenRelation.getGatewayName());
        if(gatewayGroup.getIp()==null){
            logger.warn("Gateway[%s] is offline", sceneSelectorTokenRelation.getGatewayName());
            return "error:gateway offline";
        }

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
            return "error:"+i+"devices are not in the same gateway as the scene selector";
        }
        return "success";
    }

    @RequestMapping(value = "/getSceneSelectorBind/{sceneSelectorId}", method = RequestMethod.GET)
    @ResponseBody
    public String getSceneSelectorBind(@PathVariable("sceneSelectorId") String sceneSelectorId){
        List<SceneSelectorRelation> sceneSelectorRelations = sceneSelectorRelationService.getBindInfoBySceneSelectorId(sceneSelectorId);
        JsonObject jsonObject = new JsonObject();
        if(sceneSelectorRelations.size()==0){
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

    @RequestMapping(value = "/deleteSceneSelectorBind", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteSceneSelector(@RequestBody String deviceInfo){
        JsonObject jsonObject = (JsonObject)new JsonParser().parse(deviceInfo);
        String sceneSelectorId = jsonObject.get("sceneSelectorId").getAsString();
        DeviceTokenRelation sceneSelectorTokenRelation = deviceTokenRelationService.getRelationByUuid(sceneSelectorId);
        Device sceneSelector = new Device();
        sceneSelector.setShortAddress(sceneSelectorTokenRelation.getShortAddress());
        sceneSelector.setEndpoint(sceneSelectorTokenRelation.getEndPoint().byteValue());
        sceneSelector.setIEEE(sceneSelectorTokenRelation.getIEEE());
        GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(sceneSelectorTokenRelation.getGatewayName());
        if(gatewayGroup.getIp()==null){
            logger.warn("Gateway[%s] is offline", sceneSelectorTokenRelation.getGatewayName());
            return "error:gateway offline";
        }

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
                return "error|数据库删除失败";
            }
        }
        return "success";
    }
}

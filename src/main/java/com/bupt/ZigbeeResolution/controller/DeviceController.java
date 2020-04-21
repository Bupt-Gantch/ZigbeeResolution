package com.bupt.ZigbeeResolution.controller;

import com.bupt.ZigbeeResolution.data.*;
import com.bupt.ZigbeeResolution.method.GatewayMethod;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;
import com.bupt.ZigbeeResolution.service.*;
import com.bupt.ZigbeeResolution.transform.TransportHandler;
import com.bupt.ZigbeeResolution.utils.Operation;
import com.google.gson.*;
import org.eclipse.paho.client.mqttv3.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Operation(name = "物接入")
@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {

    @Autowired
    private DeviceTokenRelationService deviceTokenRelationService;

    @Autowired
    private GatewayGroupService gatewayGroupService;

    @Autowired
    private SceneSelectorRelationService sceneSelectorRelationService;

    @Autowired
    private SceneDeviceService sceneDeviceService;

    @Autowired
    private SceneService sceneService;

    @Autowired
    private InfraredService infraredService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(name = "删除设备")
    @RequestMapping(value = "/deleteDevice/{deviceId}", method = RequestMethod.DELETE)
    @Transactional
    public String deleteDevice(@PathVariable("deviceId") String deviceId) {


        try {
            DeviceTokenRelation singleDeviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
            System.out.println("singleDeviceTokenRelation = " + singleDeviceTokenRelation);
            if (singleDeviceTokenRelation == null) {
                logger.warn(String.format("device %s does not exists", deviceId));
                return "error";
            }

            GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(singleDeviceTokenRelation.getGatewayName());
            GatewayMethod gatewayMethod = new GatewayMethodImpl();
            if (gatewayGroup == null || Strings.isEmpty(gatewayGroup.getIp())) {
                logger.warn("Gateway " + singleDeviceTokenRelation.getGatewayName() + " is offline");
                return "error";
            }

            Device device = new Device();
            device.setShortAddress(singleDeviceTokenRelation.getShortAddress());
            device.setEndpoint(singleDeviceTokenRelation.getEndPoint().byteValue());
            device.setIEEE(singleDeviceTokenRelation.getIEEE());

            // 下发删除设备指令
            gatewayMethod.deleteDevice(device, gatewayGroup.getIp());

            List<DeviceTokenRelation> allDevices = deviceTokenRelationService.getRelationByIEEE(singleDeviceTokenRelation.getIEEE());
            for (DeviceTokenRelation eachDevice : allDevices) {
                String type = eachDevice.getType();
                // 红外宝、传感器、情景开关 不关联规则和场景
                if (type.equals("sceneSelector")) {
                    sceneSelectorRelationService.deleteBindInfoByDeviceOrSelector(eachDevice.getUuid());
                    logger.info("****************删除情景开关成功*******************");
                    continue;
                } else if (type.equals("newInfrared")) {
                    infraredService.deletePanels(eachDevice.getUuid());
                    logger.info("****************删除红外宝学习面板成功*******************");
                    continue;
                } else if (type.equals("IASZone") || type.equals("PM2.5")) {
                    logger.info("****************传感器*******************");
                    continue;
                }

                List<SceneDevice> sceneDevices = sceneDeviceService.getSceneDevicesByDeviced(eachDevice.getUuid());
                List<Integer> scene_ids = new ArrayList<>();
                for (SceneDevice sceneDevice : sceneDevices) {
                    scene_ids.add(sceneDevice.getScene_id());
                }

                for (Integer scene_id : scene_ids) {
                    Device device2 = new Device();
                    device2.setShortAddress("FFFF");
                    device2.setEndpoint((byte) 0xFF);

                    Scene scene = sceneService.getSceneBySceneId(scene_id);
                    if (scene == null) {
                        logger.error(String.format("scene %s does not exist", scene_id));
                    }

                        String ip = gatewayGroup.getIp();
                        gatewayMethod.deleteSceneMember(scene, device, ip);

                    //删除场景设备
                    if (!sceneDeviceService.deleteSceneDeviceBySceneId(scene_id)) {
                        logger.error("database operation exception: fail to delete record in sceneDevice.");
                    }
                    //删除场景
                    if (!sceneService.deleteSceneBySceneId(scene_id)) {
                        logger.error("database operation exception: fail to delete record in scene.");
                    }
                }
                logger.info("****************删除设备绑定的场景成功*******************");

                sceneSelectorRelationService.deleteBindInfoByDeviceOrSelector(eachDevice.getUuid());
                logger.info("****************删除设备与情景开关的绑定成功*******************");
            }


            if (deviceTokenRelationService.deleteDeviceByIEEE2(singleDeviceTokenRelation.getIEEE()) == 0) {
                logger.error("database operation exception: fail to delete record in deviceTokenRelation");
                return "error";
            }

            System.out.println("****************删除设备成功*******************");
            logger.info("delete device successfully.");

//            /**
//             *   当删除cassandra数据库记录失败时，mysql 数据库发生回滚
//             */

            // 查找其余设备的uuid
//        List<String> uuidList = new ArrayList<>();
//        for (DeviceTokenRelation allDevice : allDevices) {
//            uuidList.add(allDevice.getUuid());
//        }
//        // 调用deviceaccess接口删除cassandra数据库记录
//        String URLPREDIX = "http://ip:port/api/v1/";
//        OkHttpClient client = new OkHttpClient();
//        String urlPrefix = URLPREDIX;
//        urlPrefix = urlPrefix.replace("ip","47.105.120.203")
//                .replace("port","30080");
//        for(int i = 0; i < uuidList.size(); i++){
//            // 删除mysql中设备关系表中的设备记录
//            Request request = new Request.Builder()
//                    .url(urlPrefix + "deviceaccess/device/" + uuidList.get(i))
//                    .delete()
//                    .build();
//
//            try {
//                client.newCall(request).execute();
//            }catch (IOException e){
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚
//                logger.warn("Error occurs in deleting records in cassandra!");
//            }
//        }

            return "success";
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return "fail";
        }
    }

    @Operation(name = "设备入网")
    @RequestMapping(value = "/addNewDevice/{gatewayName}", method = RequestMethod.GET)
    @ResponseBody
    public String addMewDevice(@PathVariable("gatewayName") String gatewayName) {
        try {
            String[] gateway = gatewayName.split("_");
            String gatewayNumber = gateway[1];

            GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(gatewayNumber);

            if (gatewayGroup.getIp() == null) {
                logger.warn("Gateway " + gatewayName + " is offline");
                //            return "error";
            }
            GatewayMethod gatewayMethod = new GatewayMethodImpl();
            gatewayMethod.permitDeviceJoinTheGateway(gatewayGroup.getIp());

            logger.info("success");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    //情景开关绑定设备
    @Operation(name = "场景绑定设备")
    @RequestMapping(value = "/sceneSelectorBindDevice", method = RequestMethod.POST)
    @ResponseBody
    public String sceneSelectorBindDevice(@RequestBody String deviceInfo) {
        try {
            int i = 0;
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(deviceInfo);
            String sceneSelectorId = jsonObject.get("sceneSelectorId").getAsString();

            Integer bindType = sceneSelectorRelationService.getBindTypeBySceneSelectorId(sceneSelectorId);
            if (bindType != null) {
                if (bindType == 1) {
                    try {
                        sceneSelectorRelationService.deleteBindInfoBySceneSelector(sceneSelectorId);
                    } catch (Exception e) {
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
            if (gatewayGroup.getIp() == null) {
                logger.warn("Gateway " + sceneSelectorTokenRelation.getGatewayName() + " is offline");
                //            return "error";
            }

            Device sceneSelector = new Device();
            sceneSelector.setShortAddress(sceneSelectorTokenRelation.getShortAddress());
            sceneSelector.setEndpoint(sceneSelectorTokenRelation.getEndPoint().byteValue());
            sceneSelector.setIEEE(sceneSelectorTokenRelation.getIEEE());

            JsonArray jsonArray = jsonObject.getAsJsonArray("deviceId");
            for (JsonElement jsonElement : jsonArray) {
                String deviceId = jsonElement.getAsString();
                DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
                Device device = new Device();
                device.setEndpoint(deviceTokenRelation.getEndPoint().byteValue());
                device.setIEEE(deviceTokenRelation.getIEEE());
                if (!deviceTokenRelation.getGatewayName().equals(sceneSelectorTokenRelation.getGatewayName())) {
                    i++;
                } else {
                    SceneSelectorRelation sceneSelectorRelation = sceneSelectorRelationService.getBindInfoBySceneSelectorIdAndDeviceId(sceneSelectorId, deviceId);
                    if (sceneSelectorRelation == null) {
                        SceneSelectorRelation newSceneSelectorRelation = new SceneSelectorRelation(sceneSelectorId, 2, deviceId);
                        sceneSelectorRelationService.addBindDevice(newSceneSelectorRelation);
                    }
                    GatewayMethod gatewayMethod = new GatewayMethodImpl();
                    gatewayMethod.setSwitchBindDevice(sceneSelector, device, gatewayGroup.getIp());
                }
            }
            if (i != 0) {
                logger.warn("%d devices are not in the same gateway as the scene selector");
                return "error";
            }
            return "success";
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    //获取情景开关绑定的设备
    @Operation(name = "获取场景绑定的设备")
    @RequestMapping(value = "/getSceneSelectorBind/{sceneSelectorId}", method = RequestMethod.GET)
    @ResponseBody
    public String getSceneSelectorBind(@PathVariable("sceneSelectorId") String sceneSelectorId) {
        List<SceneSelectorRelation> sceneSelectorRelations = sceneSelectorRelationService.getBindInfoBySceneSelectorId(sceneSelectorId);
        JsonObject jsonObject = new JsonObject();

        if (sceneSelectorRelations == null || sceneSelectorRelations.size() == 0) {
            logger.warn("scene binds no device yet.");
            return jsonObject.toString();
        }

        if (sceneSelectorRelations.get(0).getBindType() == 1) {
            jsonObject.addProperty("bindType", "scene");
            jsonObject.addProperty("scene_id", sceneSelectorRelations.get(0).getScene_id());

        } else if (sceneSelectorRelations.get(0).getBindType() == 2) {
            jsonObject.addProperty("bindType", "device");
            JsonArray jsonArray = new JsonArray();
            for (SceneSelectorRelation sceneSelectorRelation : sceneSelectorRelations) {
                jsonArray.add(sceneSelectorRelation.getDeviceId());
            }
            jsonObject.add("deviceIds", jsonArray);
        }

        return jsonObject.toString();
    }

    //删除情景开关绑定的设备
    @Operation(name = "删除场景已经绑定的设备")
    @RequestMapping(value = "/deleteSceneSelectorBind", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteSceneSelector(@RequestBody String deviceInfo) {
        try {
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(deviceInfo);
            String sceneSelectorId = jsonObject.get("sceneSelectorId").getAsString();

            DeviceTokenRelation sceneSelectorTokenRelation = deviceTokenRelationService.getRelationByUuid(sceneSelectorId);
            if (sceneSelectorTokenRelation == null) {
                logger.warn("sceneSelector %s not exists", sceneSelectorId);
                return "error";
            }

            GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(sceneSelectorTokenRelation.getGatewayName());
            if (gatewayGroup.getIp() == null) {
                logger.warn("Gateway " + sceneSelectorTokenRelation.getGatewayName() + " is offline");
                //            return "error";
            }

            Device sceneSelector = new Device();
            sceneSelector.setShortAddress(sceneSelectorTokenRelation.getShortAddress());
            sceneSelector.setEndpoint(sceneSelectorTokenRelation.getEndPoint().byteValue());
            sceneSelector.setIEEE(sceneSelectorTokenRelation.getIEEE());

            JsonArray jsonArray = jsonObject.getAsJsonArray("deviceId");
            for (JsonElement jsonElement : jsonArray) {
                String deviceId = jsonElement.getAsString();
                DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
                Device device = new Device();
                device.setEndpoint(deviceTokenRelation.getEndPoint().byteValue());
                device.setIEEE(deviceTokenRelation.getIEEE());
                GatewayMethod gatewayMethod = new GatewayMethodImpl();
                gatewayMethod.cancelBindOfSwitchAndDevice(sceneSelector, device, gatewayGroup.getIp());
                if (sceneSelectorRelationService.deleteBindInfo(sceneSelectorId, deviceId)) {
                    logger.error("database operation exception: fail to delete record in sceneSelectorRelation");
                    return "error";
                }
            }
            return "success";
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @Operation(name = "测试")
    @RequestMapping(value = "/test/{gatewayName}", method = RequestMethod.GET)
    @ResponseBody
    public String test(@PathVariable("gatewayName") String gatewayName) {
        int i = 1;
        if (i == 1) {
            //在此试验是否写入数据库
            logger.error("测试错误");
        }
        logger.info("测试信息");
        logger.warn("测试警告");
        return "success";
    }
}

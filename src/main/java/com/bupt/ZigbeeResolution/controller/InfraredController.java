package com.bupt.ZigbeeResolution.controller;

import com.bupt.ZigbeeResolution.data.*;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.InfraredService;
import com.bupt.ZigbeeResolution.utils.Operation;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.unkrig.commons.nullanalysis.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Operation(name="红外宝")
@RestController
@RequestMapping("api/v1/infrared")
public class InfraredController {


    @Autowired
    private InfraredService infraredService;

    @Autowired
    private DeviceTokenRelationService deviceTokenRelationService;

//    @GetMapping("/customer/allLearn/{shortAddress}/{endPoint}/{customerId}")
//    public List<Learn> getCusAllLearns(@PathVariable("shortAddress") String shortAddress,
//                                       @PathVariable("endPoint") Integer endPoint,
//                                       @PathVariable("customerId") Integer customerId) {
//
//        DeviceTokenRelation deviceTokenRelation = null;
//        try {
//            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        List<Learn> learns = infraredService.getCustomerAllLearns(deviceTokenRelation.getUuid(), customerId);
//
//        return learns;
//    }
//
//
//    @GetMapping("/customer/panelLearn/{shortAddress}/{endPoint}/{customerId}/{panelId}")
//    public List<Learn> getCusPanelLearn(@PathVariable("shortAddress") String shortAddress,
//                                        @PathVariable("endPoint") Integer endPoint,
//                                        @PathVariable("customerId") Integer customerId,
//                                        @PathVariable("panelId") Integer panelId) {
//
//        DeviceTokenRelation deviceTokenRelation = null;
//        try {
//            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        List<Learn> keynames = infraredService.getCustomerPanelLearn(deviceTokenRelation.getUuid(), customerId, panelId);
//
//        return keynames;
//    }
//
//
//    @GetMapping("/device/allLearn/{shortAddress}/{endPoint}")
//    @ResponseBody
//    public List<Learn> getDeviceAllLearn(@PathVariable("shortAddress") String shortAddress,
//                                         @PathVariable("endPoint") Integer endPoint) {
//
////        System.out.println("进入");
//        DeviceTokenRelation deviceTokenRelation = null;
//        try {
//            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        List<Learn> allLearns = infraredService.getDeviceAllLearns(deviceTokenRelation.getUuid());
//
//        return allLearns;
//    }
//
//    @GetMapping("/device/panelLearn/{shortAddress}/{endPoint}/{panelId}")
//    public List<Learn> getDevicePanelLearn(@PathVariable("shortAddress") String shortAddress,
//                                           @PathVariable("endPoint") Integer endPoint,
//                                           @PathVariable("panelId") Integer panelId) {
//        DeviceTokenRelation deviceTokenRelation = null;
//        try {
//            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        List<Learn> deviceLearns = infraredService.getDevicePanelLearn(deviceTokenRelation.getUuid(), panelId);
//
//        return deviceLearns;
//    }
//
//    @GetMapping("/device/getLearn/{shortAddress}/{endPoint}/{panelId}/{buttonId}")
//    public Learn getLearn(@PathVariable("shortAddress") String shortAddress,
//                            @PathVariable("endPoint") Integer endPoint,
//                            @PathVariable("panelId") Integer panelId,
//                            @PathVariable("buttonId") Integer buttonId) {
//        DeviceTokenRelation deviceTokenRelation = null;
//        try {
//            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Learn learn= infraredService.get_a_learn(deviceTokenRelation.getUuid(), panelId, buttonId);
//
//        return learn;
//    }
//
//    @GetMapping("/device/delKey/{shortAddress}/{endPoint}/{key}")
//    public String delKey(@PathVariable("shortAddress") String shortAddress,
//                         @PathVariable("endPoint") Integer endPoint,
//                         @PathVariable("key") Integer key) {
//        DeviceTokenRelation deviceTokenRelation = null;
//        try {
//            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        infraredService.deleteKey(deviceTokenRelation.getUuid(), key);
//
//        return "success";
//    }
//
//    @GetMapping("/device/delAllKeys/{shortAddress}/{endPoint}")
//    public String delAllKeys(@PathVariable("shortAddress") String shortAddress,
//                             @PathVariable("endPoint") Integer endPoint) {
////        System.out.println("进入");
//        DeviceTokenRelation deviceTokenRelation = null;
//        try {
//            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        infraredService.deleteAllKey(deviceTokenRelation.getUuid());
//
//        return "success";
//    }
//
//    @GetMapping("/device/delPane1/{shortAddress}/{endPoint}/{panelId}")
//    public String delPanel(@PathVariable("shortAddress") String shortAddress,
//                             @PathVariable("endPoint") Integer endPoint,
//                             @PathVariable("panelId") Integer panelId) {
//
//        DeviceTokenRelation deviceTokenRelation = null;
//        try {
//            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        infraredService.delPanel(deviceTokenRelation.getUuid(),panelId);
//
//        return "success";
//    }
//
//    @GetMapping("/device/delPane1s")
//    public String delPanels(@RequestParam("shortAddress") String shortAddress,
//                             @RequestParam("endPoint") Integer endPoint,
//                             @RequestParam("panelIds") List<Integer> panelIds) {
////        System.out.println("进入");
//        DeviceTokenRelation deviceTokenRelation = null;
//        try {
//            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        infraredService.delPanels(deviceTokenRelation.getUuid(),panelIds);
//
//        return "success";
//    }

    //============================================================================
    //============================================================================
    //============================================================================


    @GetMapping("/panel/get/{deviceId}/{panelId}")
    public String getPanel(@PathVariable("deviceId")String deviceId,
                           @PathVariable("panelId") Integer id){

        JsonObject res = new JsonObject();

        try {
            DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
            if (deviceTokenRelation == null) {
                res.addProperty("msg", "device not exist!");
                return res.toString();
            }

            Panel p = infraredService.findPanel(id);
            if (null == p) {
                res.addProperty("msg", "panel not exist!");
                return res.toString();
            }
            res.addProperty("msg","success");
            res.addProperty("data", p.toString());
        } catch (Exception e) {
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }
        return res.toString();
    }

    @GetMapping("/panels/get/{deviceId}")
    public String getPanels(@PathVariable("deviceId") String deviceId,
                            @RequestParam(value = "sort", required = false) Integer sort) {

        JsonObject res = new JsonObject();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
        if (deviceTokenRelation == null) {
            res.addProperty("msg", "device not exist!");
            return res.toString();
        }

        try {
            List<Panel> ps = infraredService.findPanels(deviceTokenRelation.getUuid(), sort);

            res.addProperty("msg", "success");
            res.addProperty("data", ps.toString());
        } catch (Exception e){
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }
        return res.toString();
    }

    @Operation(name="新建遥控器面板")
    @PostMapping("/panel/add/{deviceId}")
    public String createPanel(@PathVariable("deviceId")String deviceId,
                              @RequestBody String data)  {
        JsonObject res = new JsonObject();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
        if (deviceTokenRelation == null) {
            res.addProperty("msg", "device not exist");
            return res.toString();
        }
        try {
            int insert = infraredService.addPanel(deviceTokenRelation.getUuid(), (JsonObject) new JsonParser().parse(data));
            if (insert == 0) {
                res.addProperty("msg", "batis operation fail -> insert");
                return res.toString();
            }

            res.addProperty("msg", "success");
            res.addProperty("data", insert);
        } catch (Exception e) {
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }
        return res.toString();
    }

    @Operation(name="删除遥控器面板")
    @DeleteMapping("/panel/del/{deviceId}/{panelId}")
    public String deletePanel(@PathVariable("deviceId")String deviceId,
                              @PathVariable("panelId") Integer panelId) {
        JsonObject res = new JsonObject();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
        if (deviceTokenRelation == null) {
            res.addProperty("msg", "device not exist");
            return res.toString();
        }
        try {
            int delete = infraredService.deletePanel(deviceTokenRelation.getUuid(), panelId);
            if (delete == 0) {
                res.addProperty("msg", "batis operation fail -> delete");
                return res.toString();
            }

            res.addProperty("msg", "success");
            res.addProperty("data", 0);
        } catch (Exception e) {
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }
        return res.toString();
    }

    @Operation(name="删除用户下所有遥控器")
    @DeleteMapping("/panels/del/{deviceId}")
    public String deletePanels(@PathVariable("deviceId")String deviceId) {
        JsonObject res = new JsonObject();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
        if (deviceTokenRelation == null) {
            res.addProperty("msg", "device not exist");
            return res.toString();
        }
        try {
            int delete = infraredService.deletePanels(deviceTokenRelation.getUuid());
            if (delete == 0) {
                res.addProperty("msg", "batis operation fail -> delete");
                return res.toString();
            }

            res.addProperty("msg", "success");
            res.addProperty("data", 0);
        } catch (Exception e) {
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }
        return res.toString();
    }

    @Operation(name="修改遥控器面板名称")
    @PostMapping("/panel/upd/{panelId}")
    public String updatePanel(@PathVariable("panelId") Integer panelId,
                              @RequestBody String data){

        JsonObject res = new JsonObject();
        try {
            int update = infraredService.updatePanel(panelId, (JsonObject) new JsonParser().parse(data));
            if (update == 0) {
                res.addProperty("msg", "batis operation fail -> update");
                return res.toString();
            }

            res.addProperty("msg", "success");
            res.addProperty("data", 0);
        } catch (Exception e){
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }
        return res.toString();
    }

    @PostMapping("/panel/upd/{panelId}/condition")
    public String updatePanelCondition(@PathVariable("panelId")Integer panelId,
                                       @RequestBody String condition){

        JsonObject res = new JsonObject();

        JsonObject data = (JsonObject)new JsonParser().parse(condition);
        String power = data.get("power").getAsString();
        String mode = data.get("mode").getAsString();
        String windLevel = data.get("windLevel").getAsString();
        String windDirection = data.get("windDirection").getAsString();
        String tem = data.get("tem").getAsString();
        try {
            @NotNull
            Integer keyId = infraredService.getAirConditionPresetKey(power, mode, windLevel, windDirection, tem);

            if (infraredService.updatePanelCondition(panelId, keyId) != 0) {
                res.addProperty("msg", "success");
                return res.toString();
            }
            res.addProperty("msg", "update condition fail");

        } catch (Exception e) {
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }
        return res.toString();
    }

    @GetMapping("/key/get/{keyId}/condition")
    public String getAirConditionAttributes(@PathVariable("keyId")Integer keyId){
        JsonObject res = new JsonObject();
        try {
            AirConditionKey airConditionKey = infraredService.getAirConditionKeyAttributes(keyId);
            if (null != airConditionKey) {
                res.addProperty("msg", "success");
                res.addProperty("data", airConditionKey.toString());
            } else {
                res.addProperty("msg", "key not exists");
            }
        } catch (Exception e){
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }
        return res.toString();

    }

    @GetMapping("/key/get/{keyId}")
    public String getKey(@PathVariable("keyId") Integer keyId) {

        JsonObject res = new JsonObject();

        try {
            Key k = infraredService.findAKey(keyId);
            if (null == k) {
                res.addProperty("msg", "key not exist, check for params!");
                return res.toString();
            }

            res.addProperty("msg", k.toString());
        } catch (Exception e){
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }
        return res.toString();
    }

    @GetMapping("/key/get")
    public int getPowerKey(@RequestParam("power")String power,
                                           @RequestParam("mode")String mode,
                                           @RequestParam("windLevel")String windLevel,
                                           @RequestParam("windDirection")String windDirection,
                                           @RequestParam("tem")String tem) {

        if (power.equals("关机")){
            return 1;
        }
        try {
            return infraredService.getAirConditionPresetKey(power, mode, windLevel, windDirection, tem);
        } catch (Exception e) {
            System.err.println("get aircondition key fail\n" + e.getCause());
        }
        return 13;
    }

    @GetMapping("/keys/get/{panelId}")
    public String getKeys(@PathVariable("panelId") Integer id) {
        JsonObject res = new JsonObject();

        try {
            List<Key> ks = infraredService.findKeys(id);
            res.addProperty("msg", "success");
            res.addProperty("data", ks.toString());

        } catch (Exception e) {
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }

        return res.toString();
    }

    @Operation(name="新增学习按键")
    @PostMapping("key/add/{panelId}")
    public String createKey(@PathVariable("panelId") Integer panelId,
                            @RequestBody String data){
        JsonObject res = new JsonObject();

        try {
            Panel p = infraredService.findPanel(panelId);
            if (null == p) {
                res.addProperty("msg", "panel not exist, check for params!");
                return res.toString();
            }

            JsonObject data1 = (JsonObject) new JsonParser().parse(data);
            Integer number = data1.get("number").getAsInt();
            String name = data1.get("name").getAsString();
            Integer key = data1.get("key").getAsInt();

            int insert = infraredService.addAKey(panelId, number, key, name);
            if (0 == insert) {
                res.addProperty("msg", "batis operation fail -> insert");
                return res.toString();
            }

            res.addProperty("msg", "success");
            res.addProperty("data", 0);
        } catch (Exception e) {
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }
        return res.toString();
    }

    @Operation(name="删除学习按键")
    @DeleteMapping("/key/del/{panelId}/{keyId}")
    public String deleteKey(@PathVariable("panelId") Integer panelId,
                            @PathVariable("keyId") Integer keyId){

        JsonObject res = new JsonObject();

        try {
            if (null == infraredService.findPanel(panelId)) {
                res.addProperty("msg", "panel not exist, check for params!");
                return res.toString();
            }

            if (null == infraredService.findAKey(keyId)) {
                res.addProperty("msg", "key not exist, check for params!");
                return res.toString();
            }

            int delete = infraredService.deleteKey(panelId, keyId);
            if (0 == delete) {
                res.addProperty("msg", "batis operation fail -> delete");
                return res.toString();
            }

            res.addProperty("msg", "success");
            res.addProperty("data", 0);

        } catch (Exception e) {
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }
        return res.toString();
    }

    @Operation(name="删除遥控器面板下所有学习按键")
    @DeleteMapping("/keys/del/{panelId}")
    public String deleteKeys(@PathVariable("panelId") Integer panelId) {
        JsonObject res = new JsonObject();

        try {
            if (null == infraredService.findPanel(panelId)) {
                res.addProperty("msg", "panel not exist, check for params!");
                return res.toString();
            }

            int delete = infraredService.deleteKeys(panelId);
            if (0 == delete) {
                res.addProperty("msg", "batis operation fail -> delete");
                return res.toString();
            }

            res.addProperty("msg", "success");
            res.addProperty("data", 0);
        } catch (Exception e){
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }
        return res.toString();
    }

    @PostMapping("/key/upd/{keyId}")
    public String updateKey(@PathVariable("keyId") Integer keyId,
                            @RequestBody String data){
        JsonObject res = new JsonObject();

        try {
            if (null == infraredService.findAKey(keyId)) {
                res.addProperty("msg", "key not exist, check for params!");
                return res.toString();
            }

            int update = infraredService.updateKey(keyId, (JsonObject) new JsonParser().parse(data));
            if (0 == update) {
                res.addProperty("msg", "batis operation fail -> update");
                return res.toString();
            }

            res.addProperty("msg", "success");
            res.addProperty("data", 0);
        } catch(Exception e) {
            e.printStackTrace();
            res.addProperty("msg",e.getMessage());
        }
        return res.toString();
    }

    @GetMapping("/getToken")
    public String getParentDeviceToken(@RequestParam String shortAddress, @RequestParam Integer endpoint){
        try {
            DeviceTokenRelation d = deviceTokenRelationService.getParentDeviceTokenRelationBySAAndEndpoint(shortAddress, endpoint);
            if (d == null) {
                return null;
            }
            return d.getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

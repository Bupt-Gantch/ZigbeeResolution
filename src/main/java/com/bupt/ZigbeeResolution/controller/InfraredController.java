package com.bupt.ZigbeeResolution.controller;

<<<<<<< HEAD
import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.data.InfraredPanel;
import com.bupt.ZigbeeResolution.data.Learn;
import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.InfraredService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * @description: 红外宝
 * @date: 2019/7/19
 */
@Controller
@RequestMapping("api/v1/infrared")
public class InfraredController {

=======
import com.bupt.ZigbeeResolution.data.*;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.InfraredService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.unkrig.commons.nullanalysis.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/infrared")
public class InfraredController {


>>>>>>> devpeng
    @Autowired
    private InfraredService infraredService;

    @Autowired
    private DeviceTokenRelationService deviceTokenRelationService;

<<<<<<< HEAD
    @RequestMapping(value = "/getPanels/{customerId}/{deviceId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Learn> getPanels(@PathVariable("customerId")Integer customerId,
                                 @PathVariable("deviceId")String deviceId) throws Exception {

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);

        Device device = new Device();
        device.setShortAddress(deviceTokenRelation.getShortAddress());
        device.setEndpoint(deviceTokenRelation.getEndPoint().byteValue());
        device.setIEEE(deviceTokenRelation.getIEEE());

        List<Learn> panels = infraredService.getCustomerLearns(device.getDeviceId(), customerId);

        return panels;
    }


    @GetMapping("/allLearn/{shortAddress}/{endPoint}/{customerId}")
    @ResponseBody
    public List<Learn> getCusAllLearn(@PathVariable("shortAddress") String shortAddress,
                                      @PathVariable("endPoint") Integer endPoint,
                                      @PathVariable("customerId") Integer customerId) {
=======
    @GetMapping("/customer/allLearn/{shortAddress}/{endPoint}/{customerId}")
    public List<Learn> getCusAllLearns(@PathVariable("shortAddress") String shortAddress,
                                       @PathVariable("endPoint") Integer endPoint,
                                       @PathVariable("customerId") Integer customerId) {
>>>>>>> devpeng

        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
<<<<<<< HEAD
        List<Learn> learns = infraredService.getCustomerLearns(deviceTokenRelation.getUuid(), customerId);
=======
        List<Learn> learns = infraredService.getCustomerAllLearns(deviceTokenRelation.getUuid(), customerId);
>>>>>>> devpeng

        return learns;
    }


<<<<<<< HEAD
    @GetMapping("/panelLearn/{shortAddress}/{endPoint}/{customerId}/{panelId}")
    @ResponseBody
    public List<String> getCusPanelLearn(@PathVariable("shortAddress") String shortAddress,
                                         @PathVariable("endPoint") Integer endPoint,
                                         @PathVariable("customerId") Integer customerId,
                                         @PathVariable("panelId") Integer panelId) {
=======
    @GetMapping("/customer/panelLearn/{shortAddress}/{endPoint}/{customerId}/{panelId}")
    public List<Learn> getCusPanelLearn(@PathVariable("shortAddress") String shortAddress,
                                        @PathVariable("endPoint") Integer endPoint,
                                        @PathVariable("customerId") Integer customerId,
                                        @PathVariable("panelId") Integer panelId) {
>>>>>>> devpeng

        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
<<<<<<< HEAD
        List<String> keynames = infraredService.getKeyNames(deviceTokenRelation.getUuid(), customerId, panelId);
=======
        List<Learn> keynames = infraredService.getCustomerPanelLearn(deviceTokenRelation.getUuid(), customerId, panelId);
>>>>>>> devpeng

        return keynames;
    }


    @GetMapping("/device/allLearn/{shortAddress}/{endPoint}")
    @ResponseBody
    public List<Learn> getDeviceAllLearn(@PathVariable("shortAddress") String shortAddress,
<<<<<<< HEAD
                                         @PathVariable("endPoint") Integer endPoint){

        System.out.println("进入");
=======
                                         @PathVariable("endPoint") Integer endPoint) {

//        System.out.println("进入");
>>>>>>> devpeng
        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }

<<<<<<< HEAD
        List<Learn> allLearns = infraredService.getAllLearns(deviceTokenRelation.getUuid());
=======
        List<Learn> allLearns = infraredService.getDeviceAllLearns(deviceTokenRelation.getUuid());
>>>>>>> devpeng

        return allLearns;
    }

    @GetMapping("/device/panelLearn/{shortAddress}/{endPoint}/{panelId}")
<<<<<<< HEAD
    @ResponseBody
    public List<String> getDevicePanelLearn(@PathVariable("shortAddress") String shortAddress,
                                            @PathVariable("endPoint") Integer endPoint,
                                            @PathVariable("panelId") Integer panelId) {
=======
    public List<Learn> getDevicePanelLearn(@PathVariable("shortAddress") String shortAddress,
                                           @PathVariable("endPoint") Integer endPoint,
                                           @PathVariable("panelId") Integer panelId) {
>>>>>>> devpeng
        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
<<<<<<< HEAD
        List<String> deviceLearns = infraredService.getDeviceLearns(deviceTokenRelation.getUuid(), panelId);
=======
        List<Learn> deviceLearns = infraredService.getDevicePanelLearn(deviceTokenRelation.getUuid(), panelId);
>>>>>>> devpeng

        return deviceLearns;
    }

<<<<<<< HEAD

    // =========================================================================================

    /**
     * author zhs
     * time 2019/8/21
     */

    @GetMapping("/panel/get/all/{deviceId}")
    @ResponseBody
    public JsonObject getPanels(@PathVariable("deviceId")String deviceId) throws Exception{

        JsonObject res = new JsonObject();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
        if (deviceTokenRelation == null){
            res.addProperty("resMsg", "设备不存在");
            return res;
        }

        List<InfraredPanel> panels = infraredService.getPanels(deviceId);
        if (panels == null) {
            res.addProperty("resMsg","该红外宝未学习任何遥控面板");
            return res;
        }

        res.addProperty("resMsg", "查询成功");
        res.addProperty("data", panels.toString());

        return res;
    }

    @GetMapping("/panel/get/{deviceId}/{panelId}")
    @ResponseBody
    public JsonObject getPanel(@PathVariable("deviceId")String deviceId,
                               @PathVariable("panelId")Integer panelId) throws Exception{
=======
    @GetMapping("/device/getLearn/{shortAddress}/{endPoint}/{panelId}/{buttonId}")
    public Learn getLearn(@PathVariable("shortAddress") String shortAddress,
                            @PathVariable("endPoint") Integer endPoint,
                            @PathVariable("panelId") Integer panelId,
                            @PathVariable("buttonId") Integer buttonId) {
        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Learn learn= infraredService.get_a_learn(deviceTokenRelation.getUuid(), panelId, buttonId);

        return learn;
    }

    @GetMapping("/device/delKey/{shortAddress}/{endPoint}/{key}")
    public String delKey(@PathVariable("shortAddress") String shortAddress,
                         @PathVariable("endPoint") Integer endPoint,
                         @PathVariable("key") Integer key) {
        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        infraredService.deleteKey(deviceTokenRelation.getUuid(), key);

        return "success";
    }

    @GetMapping("/device/delAllKeys/{shortAddress}/{endPoint}")
    public String delAllKeys(@PathVariable("shortAddress") String shortAddress,
                             @PathVariable("endPoint") Integer endPoint) {
//        System.out.println("进入");
        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        infraredService.deleteAllKey(deviceTokenRelation.getUuid());

        return "success";
    }

    @GetMapping("/device/delPane1/{shortAddress}/{endPoint}/{panelId}")
    public String delPanel(@PathVariable("shortAddress") String shortAddress,
                             @PathVariable("endPoint") Integer endPoint,
                             @PathVariable("panelId") Integer panelId) {

        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        infraredService.delPanel(deviceTokenRelation.getUuid(),panelId);

        return "success";
    }

    @GetMapping("/device/delPane1s")
    public String delPanels(@RequestParam("shortAddress") String shortAddress,
                             @RequestParam("endPoint") Integer endPoint,
                             @RequestParam("panelIds") List<Integer> panelIds) {
//        System.out.println("进入");
        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        infraredService.delPanels(deviceTokenRelation.getUuid(),panelIds);

        return "success";
    }

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
>>>>>>> devpeng

        JsonObject res = new JsonObject();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
<<<<<<< HEAD
        if (deviceTokenRelation == null){
            res.addProperty("resMsg", "设备不存在");
            return res;
        }

        InfraredPanel panel = infraredService.getPanel(deviceId, panelId);
        if (panel == null) {
            res.addProperty("resMsg","该遥控面板不存在");
            return res;
        }

        res.addProperty("resMsg", "查询成功");
        res.addProperty("data", panel.toString());
        return  res;
    }

    @PostMapping("/panel/add")
    @ResponseBody
    public JsonObject addPanel(@RequestBody JsonObject data) throws Exception {

        JsonObject res = new JsonObject();

        int add = infraredService.addPanel(data);

        switch (add) {
            case -1:
                res.addProperty("resMsg", "设备ID不能为空");
                break;
            case 0:
                res.addProperty("resMsg", "数据库插入失败");
                break;
            default:
                res.addProperty("resMsg", "插入成功");
        }
        return res;
    }

    @DeleteMapping("/panel/delete/all/{deviceId}}")
    @ResponseBody
    public JsonObject deleteAllPanels(@PathVariable("deviceId")String deviceId) throws Exception{

        JsonObject res = new JsonObject();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
        if (deviceTokenRelation == null){
            res.addProperty("resMsg", "设备不存在");
            return res;
        }

        int delete = infraredService.deletePanels(deviceId);
        switch (delete) {
            case -1:
                res.addProperty("resMsg", "设备ID不能为空");
                break;
            case 0:
                res.addProperty("resMsg", "删除记录失败");
                break;
            default:
                res.addProperty("resMsg", "删除成功");
        }
        return res;
    }

    @DeleteMapping("/panel/delete/{deviceId}/{panelId}")
    @ResponseBody
    public JsonObject deletePanel(@PathVariable("deviceId")String deviceId,
                                  @PathVariable("panelId")Integer panelId) throws Exception{

        JsonObject res = new JsonObject();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
        if (deviceTokenRelation == null){
            res.addProperty("resMsg", "设备不存在");
            return res;
        }

        int delete = infraredService.deletePanel(deviceId, panelId);
        switch (delete) {
            case -1:
                res.addProperty("resMsg", "设备ID不能为空");
                break;
            case 0:
                res.addProperty("resMsg", "删除记录失败");
                break;
            default:
                res.addProperty("resMsg", "删除成功");
        }
        return res;
    }

    @PostMapping("/panel/update")
    @ResponseBody
    public JsonObject updatePanel(JsonObject data) throws Exception {

        JsonObject res = new JsonObject();

        int update = infraredService.updatePanel(data);

        switch(update) {
            case -1:
                res.addProperty("resMsg", "设备ID不能为空或遥控面板不存在");
                break;
            case 0:
                res.addProperty("resMsg", "更新记录失败");
                break;
            default:
                res.addProperty("resMsg", "更新成功");
        }
        return res;

    }



=======
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
>>>>>>> devpeng
}

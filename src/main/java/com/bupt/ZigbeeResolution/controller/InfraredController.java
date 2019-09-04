package com.bupt.ZigbeeResolution.controller;

import com.bupt.ZigbeeResolution.data.Key;
import com.bupt.ZigbeeResolution.data.Learn;
import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
import com.bupt.ZigbeeResolution.data.Panel;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.InfraredService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 红外宝
 * @date: 2019/7/19
 */

@RestController
@RequestMapping("api/v1/infrared")
public class InfraredController {


    @Autowired
    private InfraredService infraredService;

    @Autowired
    private DeviceTokenRelationService deviceTokenRelationService;

    @GetMapping("/customer/allLearn/{shortAddress}/{endPoint}/{customerId}")
    public List<Learn> getCusAllLearns(@PathVariable("shortAddress") String shortAddress,
                                       @PathVariable("endPoint") Integer endPoint,
                                       @PathVariable("customerId") Integer customerId) {

        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Learn> learns = infraredService.getCustomerAllLearns(deviceTokenRelation.getUuid(), customerId);

        return learns;
    }


    @GetMapping("/customer/panelLearn/{shortAddress}/{endPoint}/{customerId}/{panelId}")
    public List<Learn> getCusPanelLearn(@PathVariable("shortAddress") String shortAddress,
                                        @PathVariable("endPoint") Integer endPoint,
                                        @PathVariable("customerId") Integer customerId,
                                        @PathVariable("panelId") Integer panelId) {

        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Learn> keynames = infraredService.getCustomerPanelLearn(deviceTokenRelation.getUuid(), customerId, panelId);

        return keynames;
    }


    @GetMapping("/device/allLearn/{shortAddress}/{endPoint}")
    @ResponseBody
    public List<Learn> getDeviceAllLearn(@PathVariable("shortAddress") String shortAddress,
                                         @PathVariable("endPoint") Integer endPoint) {

//        System.out.println("进入");
        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Learn> allLearns = infraredService.getDeviceAllLearns(deviceTokenRelation.getUuid());

        return allLearns;
    }

    @GetMapping("/device/panelLearn/{shortAddress}/{endPoint}/{panelId}")
    public List<Learn> getDevicePanelLearn(@PathVariable("shortAddress") String shortAddress,
                                           @PathVariable("endPoint") Integer endPoint,
                                           @PathVariable("panelId") Integer panelId) {
        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Learn> deviceLearns = infraredService.getDevicePanelLearn(deviceTokenRelation.getUuid(), panelId);

        return deviceLearns;
    }

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

    @GetMapping("/panel/get")
    public String getPanel(@RequestParam String shortAddress,
                           @RequestParam Integer endpoint,
                           @RequestParam Integer id) throws Exception{

        JsonObject res = new JsonObject();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endpoint);
        if (deviceTokenRelation == null) {
            res.addProperty("msg", "device not exist!");
            return res.toString();
        }

        Panel p = infraredService.findPanel(id);
        if (null == p) {
            res.addProperty("msg", "panel not exist!");
            return res.toString();
        }

        res.addProperty("msg", p.toString());
        return res.toString();
    }

    @GetMapping("/panels/get")
    public String getPanels(@RequestParam String shortAddress,
                            @RequestParam Integer endpoint,
                            @RequestParam(value = "sort", required = false) Integer sort) throws Exception{

        JsonObject res = new JsonObject();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endpoint);
        if (deviceTokenRelation == null) {
            res.addProperty("msg", "device not exist!");
            return res.toString();
        }

        List<Panel> ps = infraredService.findPanels(deviceTokenRelation.getUuid(), sort);
        if (0 == ps.size()) {
            res.addProperty("msg", "haven`t create any panel yet!");
            return res.toString();
        }

        res.addProperty("msg", ps.toString());
        return res.toString();
    }

    @PostMapping("/panel/add")
    public String createPanel(@RequestBody String data) throws Exception {
        JsonObject res = new JsonObject();

        JsonObject enty = (JsonObject) new JsonParser().parse(data);
        String shortAddress = enty.get("shortAddress").getAsString();
        Integer endpoint = enty.get("endpoint").getAsInt();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endpoint);
        if (deviceTokenRelation == null) {
            res.addProperty("msg", "device not exist");
            return res.toString();
        }

        int insert = infraredService.addPanel(deviceTokenRelation.getUuid() , (JsonObject) new JsonParser().parse(data));
        if (insert == 0){
            res.addProperty("msg", "batis operation fail -> insert");
            return res.toString();
        }

        res.addProperty("msg", 0);
        return res.toString();
    }

    @DeleteMapping("/panel/del")
    public String deletePanel(@RequestParam Integer panelId,
                              @RequestBody String data) throws Exception {
        JsonObject res = new JsonObject();

        JsonObject enty = (JsonObject) new JsonParser().parse(data);
        String shortAddress = enty.get("shortAddress").getAsString();
        Integer endpoint = enty.get("endpoint").getAsInt();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endpoint);
        if (deviceTokenRelation == null) {
            res.addProperty("msg", "device not exist");
            return res.toString();
        }

        int delete = infraredService.deletePanel(deviceTokenRelation.getUuid(), panelId);
        if (delete == 0){
            res.addProperty("msg", "batis operation fail -> delete");
            return res.toString();
        }

        res.addProperty("msg", 0);
        return res.toString();
    }

    @DeleteMapping("/panels/del")
    public String deletePanels(@RequestParam String shortAddress,
                              @RequestParam Integer endpoint) throws Exception {
        JsonObject res = new JsonObject();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endpoint);
        if (deviceTokenRelation == null) {
            res.addProperty("msg", "device not exist");
            return res.toString();
        }

        int delete = infraredService.deletePanels(deviceTokenRelation.getUuid());
        if (delete == 0){
            res.addProperty("msg", "batis operation fail -> delete");
            return res.toString();
        }

        res.addProperty("msg", 0);
        return res.toString();
    }

    @PostMapping("/panel/upd")
    public String updatePanel(@RequestParam Integer panelId,
                              @RequestBody String data)throws Exception{

        JsonObject res = new JsonObject();

        int update = infraredService.updatePanel(panelId, (JsonObject) new JsonParser().parse(data));
        if (update == 0){
            res.addProperty("msg", "batis operation fail -> update");
            return res.toString();
        }

        res.addProperty("msg", 0);
        return res.toString();
    }

    @GetMapping("/key/get")
    public String getKey(@RequestParam Integer keyId) throws Exception {

        JsonObject res = new JsonObject();

        Key k = infraredService.findAKey(keyId);
        if (null == k) {
            res.addProperty("msg", "key not exist, check for params!");
            return res.toString();
        }

        res.addProperty("msg", k.toString());
        return res.toString();
    }

    @GetMapping("/keys/get")
    public String getKeys(@RequestParam Integer id) throws Exception {
        JsonObject res = new JsonObject();

        List<Key> ks= infraredService.findKeys(id);
        if (0 == ks.size()) {
            res.addProperty("msg", "key not exist, check for params!");
            return res.toString();
        }

        res.addProperty("msg", ks.toString());
        return res.toString();
    }

    @PostMapping("key/add")
    public String createKey(@RequestParam Integer panelId,
                            @RequestBody String data)throws Exception{
        JsonObject res = new JsonObject();

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

        res.addProperty("msg", 0);
        return res.toString();
    }

    @DeleteMapping("/key/del")
    public String deleteKey(@RequestParam Integer panelId,
                            @RequestParam Integer keyId)throws Exception{

        JsonObject res = new JsonObject();

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

        res.addProperty("msg", 0);
        return res.toString();
    }

    @DeleteMapping("/keys/del")
    public String deleteKeys(@RequestParam Integer panelId) throws Exception{
        JsonObject res = new JsonObject();

        if (null == infraredService.findPanel(panelId)) {
            res.addProperty("msg", "panel not exist, check for params!");
            return res.toString();
        }

        int delete = infraredService.deleteKeys(panelId);
        if (0 == delete) {
            res.addProperty("msg", "batis operation fail -> delete");
            return res.toString();
        }

        res.addProperty("msg", 0);
        return res.toString();
    }

    @PostMapping("/key/upd")
    public String updateKey(@RequestParam Integer keyId,
                            @RequestBody String data) throws Exception{
        JsonObject res = new JsonObject();

        if (null == infraredService.findAKey(keyId)) {
            res.addProperty("msg", "key not exist, check for params!");
            return res.toString();
        }

        int update = infraredService.updateKey(keyId, (JsonObject) new JsonParser().parse(data));
        if (0 == update) {
            res.addProperty("msg", "batis operation fail -> update");
            return res.toString();
        }

        res.addProperty("msg", 0);
        return res.toString();
    }
}

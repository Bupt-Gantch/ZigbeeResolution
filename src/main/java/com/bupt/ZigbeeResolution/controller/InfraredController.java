package com.bupt.ZigbeeResolution.controller;

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

    @Autowired
    private InfraredService infraredService;

    @Autowired
    private DeviceTokenRelationService deviceTokenRelationService;

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

        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Learn> learns = infraredService.getCustomerLearns(deviceTokenRelation.getUuid(), customerId);

        return learns;
    }


    @GetMapping("/panelLearn/{shortAddress}/{endPoint}/{customerId}/{panelId}")
    @ResponseBody
    public List<String> getCusPanelLearn(@PathVariable("shortAddress") String shortAddress,
                                         @PathVariable("endPoint") Integer endPoint,
                                         @PathVariable("customerId") Integer customerId,
                                         @PathVariable("panelId") Integer panelId) {

        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> keynames = infraredService.getKeyNames(deviceTokenRelation.getUuid(), customerId, panelId);

        return keynames;
    }


    @GetMapping("/device/allLearn/{shortAddress}/{endPoint}")
    @ResponseBody
    public List<Learn> getDeviceAllLearn(@PathVariable("shortAddress") String shortAddress,
                                         @PathVariable("endPoint") Integer endPoint){

        System.out.println("进入");
        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Learn> allLearns = infraredService.getAllLearns(deviceTokenRelation.getUuid());

        return allLearns;
    }

    @GetMapping("/device/panelLearn/{shortAddress}/{endPoint}/{panelId}")
    @ResponseBody
    public List<String> getDevicePanelLearn(@PathVariable("shortAddress") String shortAddress,
                                            @PathVariable("endPoint") Integer endPoint,
                                            @PathVariable("panelId") Integer panelId) {
        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> deviceLearns = infraredService.getDeviceLearns(deviceTokenRelation.getUuid(), panelId);

        return deviceLearns;
    }


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

        JsonObject res = new JsonObject();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
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



}

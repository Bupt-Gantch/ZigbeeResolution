package com.bupt.ZigbeeResolution.controller;

import com.bupt.ZigbeeResolution.data.Learn;
import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.InfraredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/customer/allLearn/{shortAddress}/{endPoint}/{customerId}")
    @ResponseBody
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
    @ResponseBody
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

        System.out.println("进入");
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
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
    public String delAllKeys(@PathVariable("shortAddress") String shortAddress,
                             @PathVariable("endPoint") Integer endPoint) {
        System.out.println("进入");
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
    @ResponseBody
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
    @ResponseBody
    public String delPanels(@RequestParam("shortAddress") String shortAddress,
                             @RequestParam("endPoint") Integer endPoint,
                             @RequestParam("panelIds") List<Integer> panelIds) {
        System.out.println("进入");
        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        infraredService.delPanels(deviceTokenRelation.getUuid(),panelIds);

        return "success";
    }
}

package com.bupt.ZigbeeResolution.controller;

import com.bupt.ZigbeeResolution.data.Learn;
import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
import com.bupt.ZigbeeResolution.service.DataService;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.InfraredService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: 红外宝
 * @date: 2019/7/19
 */
@Controller
@RequestMapping("api/v1/infraed")
public class InfraredController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataService dataService;

    @Autowired
    private InfraredService infraredService;

    @Autowired
    private DeviceTokenRelationService deviceTokenRelationService;

    @GetMapping("/learn/{shortAddress}/{endPoint}/{customerId}")
    @ResponseBody
    public List<Learn> getCustomerLearn(@PathVariable("shortAddress") String shortAddress,
                                        @PathVariable("endPoint") Integer endPoint,
                                        @PathVariable("customerId") Integer customerId) {



        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Learn> learns = infraredService.getCustomerLearns(deviceTokenRelation.getUuid(), customerId);

        //封装成json字符串
        JsonObject jsonObject = new JsonObject();
        Set<String> panels = new HashSet<String>();

        for (int i = 0; i < learns.size(); i++) {
            panels.add(learns.get(i).getPanelName());
        }
        for (String panel : panels) {
            JsonArray keys = new JsonArray();
            for (int i = 0; i < learns.size(); i++) {
                if (learns.get(i).getPanelName().equals(panel)) {
                    keys.add(learns.get(i).getName());
                }
            }
            jsonObject.addProperty(panel, keys.toString());
        }



        return learns;
    }


    @GetMapping("/names/{shortAddress}/{endPoint}/{customerId}/{panelId}")
    @ResponseBody
    public List<String> getKeynames(@PathVariable("shortAddress") String shortAddress,
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

        //封装成json串备用
        JsonObject jsonObject = new JsonObject();
        JsonArray names = new JsonArray();
        for (String keyname : keynames) {
            names.add(keyname);
        }
        jsonObject.addProperty("keynames", names.toString());

        return keynames;
    }

    @GetMapping("/devicde/learns/{shortAddress}/{endPoint}/{panelId}")
    @ResponseBody
    public List<String> getDeviceLearns(@PathVariable("shortAddress") String shortAddress,
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

    @GetMapping("/devicde/allLearns/{shortAddress}/{endPoint}")
    @ResponseBody
    public List<Learn> getAllLearns(@PathVariable("shortAddress") String shortAddress,
                                    @PathVariable("endPoint") Integer endPoint){
        DeviceTokenRelation deviceTokenRelation = null;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Learn> allLearns = infraredService.getAllLearns(deviceTokenRelation.getUuid());

        return allLearns;
    }
}

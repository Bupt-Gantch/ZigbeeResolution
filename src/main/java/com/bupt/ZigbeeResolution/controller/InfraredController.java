package com.bupt.ZigbeeResolution.controller;

import com.bupt.ZigbeeResolution.data.CustomerLearn;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @PostMapping("/learn/{shortAddress}/{endPoint}/{customerId}")
    @ResponseBody
    public String getCustomerLearn(@PathVariable("shortAddress") String shortAddress,
                                   @PathVariable("endPoint") Integer endPoint,
                                   @PathVariable("customerId") Integer customerId) {

        JsonObject jsonObject = new JsonObject();

        DeviceTokenRelation deviceTokenRelation = null;
        deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        if (deviceTokenRelation == null) {
            jsonObject.addProperty("deviceTokenRelation", "null");
            return jsonObject.toString();
        }
        List<CustomerLearn> customerLearns = infraredService.getCustomerLearns(deviceTokenRelation.getUuid(), customerId);

        Set<String> panels = new HashSet<String>();

        for (int i = 0; i < customerLearns.size(); i++) {
            panels.add(customerLearns.get(i).getPanelName());
        }
        for (String panel : panels) {
            JsonArray keys = new JsonArray();
            for (int i = 0; i < customerLearns.size(); i++) {
                if (customerLearns.get(i).getPanelName().equals(panel)) {
                    keys.add(customerLearns.get(i).getName());
                }
            }
            jsonObject.addProperty(panel, keys.toString());
        }

        return jsonObject.toString();
    }

    @PostMapping("/names/{shortAddress}/{endPoint}/{customerId}/{panelId}")
    @ResponseBody
    public String getKeynames(@PathVariable("shortAddress") String shortAddress,
                              @PathVariable("endPoint") Integer endPoint,
                              @PathVariable("customerId") Integer customerId,
                              @PathVariable("panelId") Integer panelId) {

        JsonObject jsonObject = new JsonObject();
        DeviceTokenRelation deviceTokenRelation = null;
        deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        if (deviceTokenRelation == null) {
            jsonObject.addProperty("deviceTokenRelation", "null");
            return jsonObject.toString();
        }

        List<String> keynames = infraredService.getKeyNames(deviceTokenRelation.getUuid(), customerId, panelId);
        JsonArray names = new JsonArray();
        for (String keyname : keynames) {
            names.add(keyname);
        }
        jsonObject.addProperty("keynames", names.toString());

        return jsonObject.toString();
    }

}

package com.bupt.ZigbeeResolution.controller;

        import com.bupt.ZigbeeResolution.data.Learn;
        import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
        import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
        import com.bupt.ZigbeeResolution.service.InfraredService;
        import com.google.gson.JsonArray;
        import com.google.gson.JsonObject;
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
@RequestMapping("api/v1/infrared")
public class InfraredController {


    @Autowired
    private InfraredService infraredService;

    @Autowired
    private DeviceTokenRelationService deviceTokenRelationService;

    @GetMapping("/customer/allLearn/{shortAddress}/{endPoint}/{customerId}")
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


    @GetMapping("/customer/panelLearn/{shortAddress}/{endPoint}/{customerId}/{panelId}")
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

}

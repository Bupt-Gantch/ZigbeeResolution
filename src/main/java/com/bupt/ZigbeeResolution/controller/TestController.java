package com.bupt.ZigbeeResolution.controller;



import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
import com.bupt.ZigbeeResolution.mqtt.RpcMessageCallBack;
import com.bupt.ZigbeeResolution.service.DataService;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("test/")
public class TestController {

    @Autowired
    private DataService dataService;

    @Autowired
    private DeviceTokenRelationService deviceTokenRelationService;

    @RequestMapping("resolution")
    @ResponseBody
    public String resolution() throws Exception {
        byte[] b = {(byte)0x70,(byte)0x19,(byte)0xb7 ,(byte)0x65,(byte)0x01,(byte)0x00 ,(byte)0x00 ,(byte)0x01,
                0x0a ,(byte)0x40 ,(byte)0x42 ,(byte)0x0f ,(byte)0x55 ,(byte)0x55 ,(byte)0x0b ,(byte)0xe2,(byte)0x07 , (byte)0x01 ,
                (byte)0x04 ,(byte)0x00 ,(byte)0x00 ,(byte)0x83 ,(byte)0x00 ,(byte)0x05 ,(byte)0x2e,(byte)0x00 ,(byte)0x00 ,(byte)0xab};

        dataService.resolution(b, "815", deviceTokenRelationService ,
                null, null, null);

        return "success";
    }

    @RequestMapping("test2")
    @ResponseBody
    public String test2() {

        byte[] bytes={(byte)0x2e,(byte)0x01};
        int learnKey = (int)bytes[0] + bytes[1]<<8;
        System.out.println(bytes[1]<<8);
        System.out.println(bytes[0]);
        System.out.println(learnKey);
//        RpcMessageCallBack  rpc = new RpcMessageCallBack();
//        rpc.messageArrived();

        return "success";
    }





}


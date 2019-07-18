package com.bupt.ZigbeeResolution.controller;


import com.bupt.ZigbeeResolution.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private DataService dataService;



    @RequestMapping("hi")
    public  String test() throws Exception {
        byte[] b = {(byte)0xa7, (byte)0x15, (byte)0xa4, (byte)0x7a, (byte)0x01, (byte)0x03, (byte)0x0f, (byte)0x00,
                (byte)0x55, (byte)0x55, (byte)0x0b, (byte)0xe2, (byte)0x07, (byte)0x01, (byte)0x04, (byte)0x00, (byte)0x00,
                (byte)0x83, (byte)0x00, (byte)0x05, (byte)0x2e, (byte)0x00, (byte)0x00,(byte)0xaf};

        dataService.resolution(b,"815",null,
                null,null,null);


        Integer ff=Integer.parseInt("002e",16);
        System.out.println(ff);
        return "hi";
    }
}


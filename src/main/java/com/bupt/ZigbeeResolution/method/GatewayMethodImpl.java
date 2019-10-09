package com.bupt.ZigbeeResolution.method;

import com.bupt.ZigbeeResolution.data.*;
import com.bupt.ZigbeeResolution.http.HttpControl;
import com.bupt.ZigbeeResolution.mqtt.DataMessageClient;
import com.bupt.ZigbeeResolution.service.*;
import com.bupt.ZigbeeResolution.transform.OutBoundHandler;
import com.bupt.ZigbeeResolution.transform.SocketServer;
import com.bupt.ZigbeeResolution.transform.TransportHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysql.cj.util.StringUtils;
import io.netty.channel.Channel;

import java.util.List;

public class GatewayMethodImpl extends OutBoundHandler implements  GatewayMethod{
    static int id = 1;

    HttpControl httpControl = new HttpControl();

    byte[] sendMessage =  new byte[1024];

    public void getAllDevice(String ip) throws Exception {
        byte[] bytes = new byte[8];

        int index = 0;
        bytes[index++] = (byte) 0x08;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index] = (byte) 0x81;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void getGatewayInfo() throws Exception{
        byte[] bytes = new byte[8];

        int index = 0;
        bytes[index++] = (byte) 0x08;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index] = (byte) 0x9D;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getDeviceState(Device device){
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x85;
        bytes[index++] = (byte) 0x0C;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for(int i=0;i<6;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for(int i=0;i<2;i++){
            bytes[index++] = (byte) 0x00;
        }

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getDeviceBright(Device device){
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x86;
        bytes[index++] = (byte) 0x0C;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for(int i=0;i<6;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for(int i=0;i<2;i++){
            bytes[index++] = (byte) 0x00;
        }

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getDeviceHue(Device device){
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x87;
        bytes[index++] = (byte) 0x0C;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for(int i=0;i<6;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for(int i=0;i<2;i++){
            bytes[index++] = (byte) 0x00;
        }

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getDeviceSaturation(Device device){
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x88;
        bytes[index++] = (byte) 0x0C;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for(int i=0;i<6;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for(int i=0;i<2;i++){
            bytes[index++] = (byte) 0x00;
        }

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getGroup(){
        byte[] bytes = new byte[8];
        //TransportHandler.response = 0x01;

        int index = 0;
        bytes[index++] = (byte) 0x08;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index] = (byte) 0x8E;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getGroupMember(Group group){
        byte[] bytes = new byte[11];

        int index = 0;
        bytes[index++] = (byte) 0x0B;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x98;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(group.getGroupId()), 0, bytes, index, TransportHandler.toBytes(group.getGroupId()).length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getScene(){
        System.out.print("获取场景 => ");
        byte[] bytes = new byte[8];
        //TransportHandler.response = 0x01;

        int index = 0;
        bytes[index++] = (byte) 0x08;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index] = (byte) 0x90;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getSceneDetail(Scene scene){
        System.out.print("获取场景详细信息 => ");
        byte[] bytes = new byte[scene.getSceneName().getBytes().length + 12];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (scene.getSceneName().getBytes().length+12));
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x8A;
        bytes[index++] = (byte) (0xFF & (scene.getSceneName().getBytes().length+4));
        System.arraycopy(TransportHandler.toBytes(scene.getSceneId()), 0, bytes, index, TransportHandler.toBytes(scene.getSceneId()).length);
        index=index+TransportHandler.toBytes(scene.getSceneId()).length;
        bytes[index++] = (byte) (0xFF & (scene.getSceneName().getBytes().length));
        System.arraycopy(scene.getSceneName().getBytes(), 0, bytes, index, scene.getSceneName().getBytes().length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void deleteSceneMember(Scene scene, Device device, String ip){
        byte[] bytes = new byte[scene.getSceneName().getBytes().length+23];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (scene.getSceneName().getBytes().length+23));
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x8B;
        bytes[index++] = (byte) (0xFF & (scene.getSceneName().getBytes().length+14));
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for(int i=0;i<6;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for(int i=0;i<2;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = (byte) (0xFF & (scene.getSceneName().getBytes().length));
        System.arraycopy(scene.getSceneName().getBytes(), 0, bytes, index, scene.getSceneName().getBytes().length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void getTimerTask(){
        byte[] bytes = new byte[8];
        //TransportHandler.response = 0x01;

        int index = 0;
        bytes[index++] = (byte) 0x08;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index] = (byte) 0x99;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }


    public void getTask(){
        byte[] bytes = new byte[8];
        //TransportHandler.response = 0x01;

        int index = 0;
        bytes[index++] = (byte) 0x08;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index] = (byte) 0xA6;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void controlIR(Device device, String ip, byte[] data, byte method){
        byte[] bytes = new byte[data.length+13];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (data.length+13));
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) (0xFF & (data.length+4));
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = method;
        System.arraycopy(data,0,bytes,index,data.length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_get_version(Device device, String ip) {
        System.out.println("+++++++++++++++++ 获取版本号 ++++++++++++++++++");
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) (0xFF & 21);
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) 0x0C;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x03;
        bytes[index++] = (byte) 0x06;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x02;
        bytes[index++] = (byte) 0x80;
        bytes[index++] = (byte) 0x00;
        bytes[index] = (byte) 0x82;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        System.out.println("下发指令");
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_match(Device device, String ip, String version, int matchType) {
        System.out.println("+++++++++++++++++ 红外匹配 ++++++++++++++++++");
        byte[] bytes = new byte[28];

        int index = 0;
        bytes[index++] = (byte) (0xFF & 28);
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) 0x13;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x03; // infrare control type
        bytes[index++] = (byte) 0x0D;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x09;
        byte[] version_byte = TransportHandler.toBytes(version);
        System.arraycopy(version_byte, 0, bytes, index, version_byte.length);
        index = index + version_byte.length;
        bytes[index++] = (byte) 0x81;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) (0xFF & matchType);
        byte[] countValue = new byte[10];
        System.arraycopy(bytes, index-10, countValue, 0, 10);
        bytes[index] = DataService.count_bytes(countValue);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        System.out.println("下发指令");
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_learn(Device device, String ip, String version, int matchType, int key){
        System.out.println("+++++++++++++++++ 红外学习 ++++++++++++++++++");
        byte[] bytes = new byte[30];

        int index = 0;
        bytes[index++] = (byte) (0xFF & 30);
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) 0x15;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x03; // infrared control type
        bytes[index++] = (byte) 0x0F;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x0B;
        byte[] version_byte = TransportHandler.toBytes(version);
        System.arraycopy(version_byte, 0, bytes, index, version_byte.length);
        index = index + version_byte.length;
        bytes[index++] = (byte) 0x83;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) (0xFF & matchType);
        bytes[index++] = (byte) (0x00FF & key);
        bytes[index++] = (byte) (0xFF & key>>8);
        byte[] countValue = new byte[12];
        System.arraycopy(bytes, index-12, countValue, 0, 12);
        bytes[index] = DataService.count_bytes(countValue);

        System.out.println("下发指令");
        sendMessage = TransportHandler.getSendContent(12, bytes);
        Channel channel = SocketServer.getMap().get(ip);
        channel.writeAndFlush(sendMessage);
    }

    public void IR_penetrate(Device device, String ip, String version, int seq, int matchType, int key){
        System.out.println("+++++++++++++++++ 红外透传 ++++++++++++++++++");
        byte[] bytes = new byte[30];

        int index = 0;
        bytes[index++] = (byte) (0xFF & 30);
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) 0x15;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x03; // infrare control type
        bytes[index++] = (byte) 0x0F;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x0B;
        byte[] version_byte = TransportHandler.toBytes(version);
        System.arraycopy(version_byte, 0, bytes, index, version_byte.length);
        index = index + version_byte.length;
        bytes[index++] = (byte) 0x82;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) (0xFF & matchType);
        bytes[index++] = (byte) (0x00FF & key);
        bytes[index++] = (byte) (0xFF & key>>8);
        byte[] countValue = new byte[12];
        System.arraycopy(bytes, index-12, countValue, 0, 12);
        bytes[index] = DataService.count_bytes(countValue);

        System.out.println("下发指令");
        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_query_current_device_params(Device device, String ip, String version) {
        byte[] bytes = new byte[27];

        int index = 0;
        bytes[index++] = (byte) (0xFF & 27);
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) 0x12;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x03; // infrare control type
        bytes[index++] = (byte) 0x0C;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x08;
        byte[] version_byte = TransportHandler.toBytes(version);
        System.arraycopy(version_byte, 0, bytes, index, version_byte.length);
        index = index + version_byte.length;
        bytes[index++] = (byte) 0x84;
        bytes[index++] = (byte) 0x00;
        byte[] countValue = new byte[9];
        System.arraycopy(bytes, index-9, countValue, 0, 9);
        bytes[index] = DataService.count_bytes(countValue);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_delete_learnt_key(Device device, String ip, String version, int matchType, int key) {
        byte[] bytes = new byte[30];

        int index = 0;
        bytes[index++] = (byte) (0xFF & 30);
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) 0x15;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x03; // infrare control type
        bytes[index++] = (byte) 0x0F;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x0B;
        byte[] version_byte = TransportHandler.toBytes(version);
        System.arraycopy(version_byte, 0, bytes, index, version_byte.length);
        index = index + version_byte.length;
        bytes[index++] = (byte) 0x85;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) (0xFF & matchType);
        bytes[index++] = (byte) (0xFF & key);
        bytes[index++] = (byte) (0xFF & key>>8);
        byte[] countValue = new byte[12];
        System.arraycopy(bytes, index-12, countValue, 0, 12);
        bytes[index] = DataService.count_bytes(countValue);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_delete_learnt_all_key(Device device, String ip, String version) {
        byte[] bytes = new byte[25];

        int index = 0;
        bytes[index++] = (byte) (0xFF & 25);
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) 0x11;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x03; // infrare control type
        bytes[index++] = (byte) 0x0B;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x08;
        byte[] version_byte = TransportHandler.toBytes(version);
        System.arraycopy(version_byte, 0, bytes, index, version_byte.length);
        index = index + version_byte.length;
        bytes[index++] = (byte) 0x86;
        bytes[index++] = (byte) 0x00;
        byte[] countValue = new byte[9];
        System.arraycopy(bytes, index-9, countValue, 0, 9);
        bytes[index] = DataService.count_bytes(countValue);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_exit_learn_or_match(Device device, String ip){
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) (0xFF & 21);
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) 0x0C;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x03; // infrare control type
        bytes[index++] = (byte) 0x06;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x02;
        bytes[index++] = (byte) 0x8A;
        bytes[index++] = (byte) 0x00;
        bytes[index] = (byte) 0x8C;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_save_data_to_gateway(Device device, String ip, byte[] data, String name){
        byte[] bytes = new byte[data.length+name.length()+19];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (data.length+name.length()+19));
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) (0xFF & (data.length+name.length()+10));
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x04;  // 保存数据标志
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;  // 两个保留字节
        bytes[index++] = (byte) (0xFF & (data.length + name.length()+3));
        if(data.length > 0) {
            System.arraycopy(data, 0, bytes, index, data.length);
            index += data.length;
        }
        bytes[index++] = (byte) (TransportHandler.toBytes(name).length);
        System.arraycopy(TransportHandler.toBytes(name), 0, bytes, index, TransportHandler.toBytes(name).length);
        index +=  TransportHandler.toBytes(name).length;
        bytes[index++] = (byte) 0x01; // IR Id TODO
        bytes[index] = (byte) 0x00;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_Control(Device device, String ip,String version,int key) {
        byte[] bytes = new byte[30];

        int index = 0;
        bytes[index++] = (byte) (0xFF & 21);
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) 0x0C;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x03;
        bytes[index++] = (byte) 0x06;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x55;
        bytes[index++] = (byte) 0x0b;
        byte[] version_byte = TransportHandler.toBytes(version);
        System.arraycopy(version_byte, 0, bytes, index, version_byte.length);
        index = index + version_byte.length;

        bytes[index++] = (byte) 0x82;
        bytes[index++] = (byte) 0x01;

        bytes[index++] = (byte) (0x00FF & key);
        bytes[index++] = (byte) (0xFF & key>>8);
        bytes[index] = (byte) 0x82;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_get_gatewayData(String ip){
        byte[] bytes = new byte[13];

        int index = 0;
        bytes[index++] = (byte) (0x0D);
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) 0x04;
        bytes[index++] = (byte) 0x00;  // 三个保留字节
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        bytes[index] = (byte) 0x05;  // 查询网关保存红外数据控制标志

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_send_gatewayData(Device device, String ip){
        byte[] bytes = new byte[20];

        int index = 0;
        bytes[index++] = (byte) (0x14);
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) 0x0B;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x06; // 发送网关内保存的红外数据标志
        bytes[index++] = (byte) 0x04;
        bytes[index++] = (byte) 0x00; // 后面的字节看不懂 TODO
        bytes[index++] = (byte) 0x03;
        bytes[index++] = (byte) 0x6F;
        bytes[index++] = (byte) 0x66;
        bytes[index] = (byte) 0x66;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_delete_gatewayData(Device device, String ip){
        byte[] bytes = new byte[20];

        int index = 0;
        bytes[index++] = (byte) (0x14);
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) 0x0B;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x07; // 发送网关内保存的红外数据标志
        bytes[index++] = (byte) 0x04; // 后面的字节看不懂 TODO
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x03;
        bytes[index++] = (byte) 0x6F;
        bytes[index++] = (byte) 0x66;
        bytes[index] = (byte) 0x66;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_cache_pass_throwgh(Device device, String ip, byte[] data){
        byte[] bytes = new byte[data.length+15];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (data.length+15));
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) (0xFF & (data.length+7));
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x09;  // 缓存透传数据标志
        bytes[index++] = (byte) (0xFF & (data.length));
        bytes[index++] = (byte) (0x00);  // 保留字节
        if (data.length >0) {
            System.arraycopy(data, 0, bytes, index, data.length);
        }

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void IR_get_cache_quantity(Device device, String ip){
        byte[] bytes = new byte[13];

        int index = 0;
        bytes[index++] = (byte) (0x0D);
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA7;
        bytes[index++] = (byte) 0x05;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index] = (byte) 0x0A;  // 查询缓存条目数量标志

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void getTaskDetail(Task task){
        byte[] bytes = new byte[TransportHandler.toBytes(task.getTaskName()).length+10];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (task.getTaskName().getBytes().length+10));
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA5;
        bytes[index++] = (byte) (0xFF & (task.getTaskName().getBytes().length+1));
        bytes[index++] = (byte) (0xFF & (task.getTaskName().getBytes().length));
        System.arraycopy(task.getTaskName().getBytes(), 0, bytes, index, task.getTaskName().getBytes().length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getDeviceColourTemp(Device device){
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA9;
        bytes[index++] = (byte) 0x0A;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for(int i=0;i<6;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index] = device.getEndpoint();

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void setGroupName(Group group,String name){
        byte[] bytes = new byte[name.getBytes().length+12];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (name.getBytes().length+12));
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA5;
        bytes[index++] = (byte) (0xFF & (name.getBytes().length+3));
        System.arraycopy(TransportHandler.toBytes(group.getGroupId()), 0, bytes, index, TransportHandler.toBytes(group.getGroupId()).length);
        index=index+TransportHandler.toBytes(group.getGroupId()).length;
        bytes[index++] = (byte) (0xFF & (name.getBytes().length));
        System.arraycopy(name.getBytes(), 0, bytes, index, name.getBytes().length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }


    public void changeDeviceName(Device device, String name){
        byte[] bytes = new byte[name.getBytes().length+13];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (name.getBytes().length + 13));
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x94;
        bytes[index++] = (byte) (0xFF & (name.getBytes().length + 4));
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte)(0xFF & name.getBytes().length);
        System.arraycopy(name.getBytes(),0, bytes, index, name.getBytes().length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void deleteDevice(Device device,String ip){
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x95;
        bytes[index++] = (byte) 0x0C;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        System.arraycopy(TransportHandler.toBytes(device.getIEEE()), 0, bytes, index, TransportHandler.toBytes(device.getIEEE()).length);
        index = index + TransportHandler.toBytes(device.getIEEE()).length;
        bytes[index] = device.getEndpoint();

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void setDeviceState(Device device, byte state, String ip) {
        System.out.println("进入setDeviceState");
        byte[] bytes = new byte[22];

        int index = 0;
        bytes[index++] = (byte) 0x16;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x82;
        bytes[index++] = (byte) 0x0D;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        bytes[index] = state;


        sendMessage = TransportHandler.getSendContent(12, bytes);
        System.out.println("下发指令");
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void setSoundLightAlarmState(Device device, byte state, String ip){
        System.out.println("进入setSoundLightAlarmState");
        byte[] bytes = new byte[24];

        int index = 0;
        bytes[index++] = (byte) 0x18;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x82;
        bytes[index++] = (byte) 0x0D;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = state;
        bytes[index++] = (byte) 0x3C;
        bytes[index] = (byte) 0x00;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        System.out.println("下发指令");
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void setAlarmState(Device device, byte state, String ip,int time) {
        System.out.println("进入setAlarmState");
        byte[] bytes = new byte[24];

        int index = 0;
        bytes[index++] = (byte) 0x18;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x82;
        bytes[index++] = (byte) 0x0F;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = state;
        bytes[index++] = (byte)(time & 0xFF);
        bytes[index] = (byte)((time >> 8) & 0xFF);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        System.out.println("下发指令");
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void setDeviceLevel(Device device, byte value, int transition, String ip) {
        byte[] bytes = new byte[24];

        int index = 0;
        bytes[index++] = (byte) 0x18;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x83;
        bytes[index++] = (byte) 0x0F;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = value;
        bytes[index++] = (byte) (0xFF & (byte)transition);
        bytes[index] = (byte) (0xFF & ((byte)(transition) >> 8));

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void setDeviceHueAndSat(Device device, byte hue, byte sat, int transition) {
        byte[] bytes = new byte[25];

        int index = 0;
        bytes[index++] = (byte) 0x19;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x84;
        bytes[index++] = (byte) 0x10;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = hue;
        bytes[index++] = sat;
        bytes[index++] = (byte) (0xFF & transition);
        bytes[index] = (byte) (0xFF & (transition >> 8 ) );

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void addScene(Device device, byte state, byte data2, byte data3, byte data4, String sceneName, byte irId, int transition, byte funcId, String ip) {
        System.out.print("添加场景 => ");
        byte[] bytes = new byte[31 + sceneName.getBytes().length];

        int index = 0;
        bytes[index++] = (byte) (31 + sceneName.getBytes().length);
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x91;
        bytes[index++] = (byte) (23 + sceneName.getBytes().length);
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, 2);
        index = index + 2;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        System.arraycopy(TransportHandler.toBytes(device.getDeviceId()), 0, bytes, index, TransportHandler.toBytes(device.getDeviceId()).length);
        index = index + TransportHandler.toBytes(device.getDeviceId()).length;
        bytes[index++] = state;
        bytes[index++] = data2;
        bytes[index++] = data3;
        bytes[index++] = data4;
        bytes[index++] = (byte)sceneName.getBytes().length;
        System.arraycopy(sceneName.getBytes(), 0, bytes, index, sceneName.getBytes().length);
        index = index + sceneName.getBytes().length;
        bytes[index++] = irId;
        bytes[index++] = (byte)(0xFF & transition);
        bytes[index] = funcId;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    @Override
    public void addScene(Device device, byte state, byte data2, byte data3, byte data4, String sceneName, byte irId, int transition) {
        System.out.print("添加场景 => ");
        byte[] bytes = new byte[30 + sceneName.getBytes().length];

        int index = 0;
        bytes[index++] = (byte) (30 + sceneName.getBytes().length);
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x91;
        bytes[index++] = (byte) (22 + sceneName.getBytes().length);
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index+TransportHandler.toBytes(device.getShortAddress()).length;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        System.arraycopy(TransportHandler.toBytes(device.getDeviceId()), 0, bytes, index, TransportHandler.toBytes(device.getDeviceId()).length);
        index = index + TransportHandler.toBytes(device.getDeviceId()).length;
        bytes[index++] = state;
        bytes[index++] = data2;
        bytes[index++] = data3;
        bytes[index++] = data4;
        bytes[index++] = (byte)sceneName.getBytes().length;
        System.arraycopy(sceneName.getBytes(), 0, bytes, index, sceneName.getBytes().length);
        index = index + sceneName.getBytes().length;
        bytes[index++] = irId;
        bytes[index] = (byte)transition;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void callScene(String sceneId, String ip) {
        System.out.print("调用场景 => ");
        byte[] bytes = new byte[11];

        int index = 0;
        bytes[index++] = (byte) 0x0B;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x92;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(sceneId), 0, bytes, index, 2);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    @Override
    public void getDeviceInfo(Device device) {
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i ++) {
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x93;
        bytes[index++] = (byte) 0x0C;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        for(int i = 0; i < 8; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index] = (byte) 0x00;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void changeSceneName(String sceneId, String name) {
        byte[] bytes = new byte[12 + name.getBytes().length];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (12 + name.getBytes().length));
        bytes[index++] = (byte) ((0xFF00 & (12 + name.getBytes().length)) >> 8);
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x8C;
        bytes[index++] = (byte) (3 + name.getBytes().length);
        System.arraycopy(TransportHandler.toBytes(sceneId), 0, bytes, index, 2);
        index = index + 2;
        bytes[index++] = (byte) name.getBytes().length;
        System.arraycopy(name.getBytes(), 0, bytes, index, name.getBytes().length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void setReportTime(Device device, String clusterId, String attribId, String dataType, int transition) {
        byte[] bytes = new byte[30];

        int index = 0;
        bytes[index++] = (byte) 0x1E;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x9E;
        bytes[index++] = (byte) 0x16;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, 2);
        index = index + 2;
        for (int i = 0; i < 8; i++){

            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for (int i = 0; i < 2; i++){
            bytes[index++] = (byte) 0x00;
        }
        System.arraycopy(TransportHandler.toBytes(attribId), 0, bytes, index, 2);
        index = index + 2;
        System.arraycopy(TransportHandler.toBytes(attribId), 0, bytes, index, 2);
        index = index + 2;
        bytes[index++] = (byte) 0x21;
        bytes[index++] = (byte) (0xFF & transition);
        bytes[index] = (byte) ((0xFF00 & transition) >> 8);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void setColorTemperature(Device device, int value, int transition) {
        byte[] bytes = new byte[25];

        int index = 0;
        bytes[index++] = (byte) 0x18;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA8;
        bytes[index++] = (byte) 0x0F;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, 2);
        index = index + 2;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for (int i = 0; i < 2; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = (byte) (0xFF & value);
        bytes[index++] = (byte) ((0xFF00 & value) >> 8);
        bytes[index++] = (byte) (0xFF & transition);
        bytes[index] = (byte) ((0xFF00 & transition) >> 8);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void setSwitchBindDevice(Device sceneSelector, Device device,String ip){
        byte[] bytes = new byte[31];

        int index = 0;
        bytes[index++] = (byte)0x1F;
        bytes[index++] = (byte)0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x89;
        bytes[index++] = (byte) 0x17;
        System.arraycopy(TransportHandler.toBytes(sceneSelector.getShortAddress()), 0, bytes, index, 2);
        index +=2;
        bytes[index++] = sceneSelector.getEndpoint();
        System.arraycopy(TransportHandler.toBytes(sceneSelector.getIEEE()), 0, bytes, index, 8);
        index +=8;
        bytes[index++] = device.getEndpoint();
        System.arraycopy(TransportHandler.toBytes(device.getIEEE()), 0, bytes, index, 8);
        index +=8;
        bytes[index++] = (byte)0x06;
        bytes[index] = (byte)0x04;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    @Override
    public void setSwitchBindScene(Device device, String sceneId,String ip) {
        byte[] bytes = new byte[23];
        sceneId = sceneId.substring(0,2);

        int index = 0;
        bytes[index++] = (byte) 0x1F;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x8D;
        bytes[index++] = (byte) 0x08;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, 2);
        index = index + 2;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x05;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x01;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x10;
        bytes[index++] = (byte) 0x04;
        bytes[index++] = (byte) 0x04;
        bytes[index++] = (byte) 0xF0;
        bytes[index++] = (byte) 0xF0;
        System.arraycopy(TransportHandler.toBytes(sceneId), 0, bytes, index, 1);
        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    @Override
    public void getBindRecord(Device device, String ip) {
        byte[] bytes = new byte[10];

        int index = 0;
        bytes[index++] = (byte) 0x0A;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x8D;
        bytes[index++] = (byte) 0x01;
        bytes[index] = (byte) 0x08;
        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    @Override
    public void cancelBindOfSwitchAndScene(Device device, String clusterId) {
//        byte[] bytes = new byte[35];
//
//        int index = 0;
//        bytes[index++] = (byte) 0x23;
//        bytes[index++] = (byte) 0x00;
//        for (int i = 0; i < 4; i++){
//            bytes[index++] = (byte) 0xFF;
//        }
//        bytes[index++] = (byte) 0xFE;
//        bytes[index++] = (byte) 0x96;
//        bytes[index++] = (byte) 0x00;
    }

    @Override
    public void cancelBindOfSwitchAndDevice(Device sceneSelector, Device device,String ip){
        byte[] bytes = new byte[31];

        int index = 0;
        bytes[index++] = (byte)0x1F;
        bytes[index++] = (byte)0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x96;
        bytes[index++] = (byte) 0x17;
        System.arraycopy(TransportHandler.toBytes(sceneSelector.getShortAddress()), 0, bytes, index, 2);
        index +=2;
        bytes[index++] = sceneSelector.getEndpoint();
        System.arraycopy(TransportHandler.toBytes(sceneSelector.getIEEE()), 0, bytes, index, 8);
        index +=8;
        bytes[index++] = device.getEndpoint();
        System.arraycopy(TransportHandler.toBytes(device.getIEEE()), 0, bytes, index, 8);
        index +=8;
        bytes[index++] = (byte)0x06;
        bytes[index] = (byte)0x04;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void permitDeviceJoinTheGateway(String ip){
        byte[] bytes = new byte[8];

        int index = 0;
        bytes[index++] = (byte) 0x08;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index] = (byte) 0x9F;
        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void setLock(Device device, int[] password, String ip, byte state){
        byte[] bytes = new byte[password.length+23];

        int index = 0;
        bytes[index++] = (byte) (0xFF &(password.length+23));
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x82;
        bytes[index++] = (byte) (0xFF &(password.length+14));
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = state;
        bytes[index++] = (byte) (0xFF &(password.length));
        for(int i = 0;i<password.length;i++){
            bytes[index++] = (byte) (0xFF & password[i]);
        }


        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }


    public void permitDeviceJoinTheGateway_CallBack(){

    }

    @Override
    public void setSwitchBindDevice_CallBack() {

    }

    @Override
    public void setSwitchBindScene_CallBack() {

    }

    @Override
    public void getBindRecord_CallBack() {

    }

    @Override
    public void cancelBindOfSwitchAndScene_CallBack() {

    }

    /**
     * 下发查询网关所有设备指令，网关返回设备信息，解析并处理
     * @param device
     * @param gatewayName
     * @param deviceTokenRelationService
     * @param gatewayGroupService
     * @throws Exception
     */
    @Override
    public void device_CallBack(Device device, String gatewayName, DeviceTokenRelationService deviceTokenRelationService, GatewayGroupService gatewayGroupService) throws Exception {

        System.out.printf("设备 %s 入网成功\n", device.getDeviceId());
        System.out.println(device);

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelotionByIEEEAndEndPoint(device.getIEEE(), Integer.parseInt(String.valueOf(device.getEndpoint())));

        if(deviceTokenRelation == null){ // 该设备是新设备

            System.out.println("接入一个新设备");

            String token;
            String type = DataService.deviceId2Type(device.getDeviceId());
            Integer deviceNumber = deviceTokenRelationService.getDeviceNumber();
            DeviceTokenRelation gateway = deviceTokenRelationService.getGateway(gatewayName);

            //httpControl.httplogin();
            // 创建新设备
            if(!StringUtils.isNullOrEmpty(device.getSnid().trim())) {

                // httpcreate() 函数调用 deviceaccess 的接口创建设备
                // 根据设备类型和数据库当前自动增长id值给设备命名
                String id = httpControl.httpcreate(type+"_"+deviceNumber.toString(), gateway.getIEEE(),type, device.getSnid());
                token = httpControl.httpfind(id);

                DeviceTokenRelation newDeviceTokenRelation = new DeviceTokenRelation(device.getIEEE(), Integer.parseInt(String.valueOf(device.getEndpoint())), token, type, gatewayName, device.getShortAddress(), id);

                // 插入 deviceTokenRelation
                deviceTokenRelationService.addARelation(newDeviceTokenRelation);

                // 上传设备属性
                DataMessageClient.publishAttribute(token, device.toString());

                // 上传设备数据:在线/离线状态
                JsonObject jsonObject = new JsonObject();
                if(device.getOnlineState()==3){
                    jsonObject.addProperty("online",1D);
                }else{
                    jsonObject.addProperty("online",0D);
                }
                DataMessageClient.publishData(token,jsonObject.toString());

            }else{
                System.out.println("设备SN码为空，请检查设备上传数据！");

            }

            // 如果该设备是红外宝，下发指令获取版本属性值，收到返回后上传
            if (type.equals("infrared") || type.equals("newInfrared")) {
                String ip = gatewayGroupService.getGatewayIp(device.getShortAddress(), Integer.parseInt(String.valueOf(device.getEndpoint())));
                IR_get_version(device, ip);
            }

        }else{  // 该设备已经存在于平台

            System.out.println("该设备已存在，更新设备数据...");

            if(!device.getShortAddress().equals(deviceTokenRelation.getShortAddress())){ // update and publish shortAddress
                deviceTokenRelationService.updateShortAddress(device.getShortAddress(), device.getIEEE());
                DataMessageClient.publishAttribute(deviceTokenRelation.getToken(), device.toString());

            }
            if(!gatewayName.equals(deviceTokenRelation.getGatewayName())){   // update and publish gateway name
                deviceTokenRelationService.updateGatewayName(gatewayName, device.getIEEE());
                String deviceJson = httpControl.httpGetDevice(deviceTokenRelation.getUuid());
                DeviceTokenRelation gatewayInfo = deviceTokenRelationService.getGateway(gatewayName);

                if (!StringUtils.isNullOrEmpty(deviceJson)) {
                    // update cassandra device
                    JsonObject jsonObject = (JsonObject) new JsonParser().parse(deviceJson);
                    jsonObject.remove("parentDeviceId");
                    jsonObject.addProperty("parentDeviceId", gatewayInfo.getUuid());
                    httpControl.UpdateDevice(jsonObject.toString());

                    // publish attribute
                    DataMessageClient.publishAttribute(deviceTokenRelation.getToken(), device.toString());

                } else {
                    // TODO throw NullOrIOException
                    System.err.println("获取设备信息为空，设备不存在或网络异常");

                }

            }else{
                DataMessageClient.publishAttribute(deviceTokenRelation.getToken(), device.toString());
            }

            // reduplicate with previous case, extract outside
            JsonObject jsonObject = new JsonObject();
            if(device.getOnlineState()==3){
                jsonObject.addProperty("online",1D);
            }else{
                jsonObject.addProperty("online",0D);
            }
            DataMessageClient.publishData(deviceTokenRelation.getToken(),jsonObject.toString());
        }

    }

    @Override
    public void gateway_CallBack(Gateway gateway){
        System.out.println(gateway.toString());
    }

    @Override
    public void deviceState_CallBack(Device device,DeviceTokenRelationService deviceTokenRelationService){
//        System.out.println(device.getShortAddress()+"-"+device.getEndpoint()+":"+device.getState());
        System.out.println("设备 " + device.getDeviceId() +", 状态 "+device.getState());

        DeviceTokenRelation deviceTokenRelation;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(device.getShortAddress(), Integer.parseInt(String.valueOf(device.getEndpoint())));
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("status",device.getState());

            DataMessageClient.publishData(deviceTokenRelation.getToken(), jsonObject.toString());

        }catch (Exception e){
            System.out.println("数据表中无对应token, 异常信息："+e);
        }
    }

    @Override
    public void deviceBright_CallBack(String shortAddress, int endPoint, int bright){
        System.out.println(shortAddress+"-"+endPoint+":"+bright);
    }

    @Override
    public void deviceHue_CallBack(String shortAddress, int endPoint, int hue){
        System.out.println(shortAddress+"-"+endPoint+":"+hue);
    }

    @Override
    public void deviceSaturation_CallBack(String shortAddress, int endPoint, int saturation){
        System.out.println(shortAddress+"-"+endPoint+":"+saturation);
    }

    @Override
    public void deviceColourTemp_CallBack(String shortAddress, int endPoint, int colourTemp){
        System.out.println(shortAddress+"-"+endPoint+":"+colourTemp);
    }

    @Override
    public void group_CallBack(Group group){
        System.out.println(group.toString());
    }

    @Override
    public void groupMember_CallBack(String groupId, String[] shortAddress, int[] endPoint){
        System.out.println(groupId);
        for(int i=0;i<shortAddress.length;i++){
            System.out.println(shortAddress[i]+endPoint[i]);
        }
    }

    @Override
    public void scene_CallBack(Scene scene){
        System.out.println(scene.toString());
    }

    @Override
    public void sceneDetail_CallBack(String sceneId, String[] shortAddress, int[] endPoint, String[] deviceId, byte[] data1, byte[] data2, byte[] data3, byte[] data4, byte[] IRId, int[] delay){
        System.out.println(sceneId);
        for(int i=0;i<shortAddress.length;i++){
            System.out.println(shortAddress[i]+endPoint[i]+"|"+deviceId[i]+"|"+data1[i]+","+data2[i]+","+data3[i]+","+data4[i]+"|"+IRId[i]+"|"+delay[i]);
        }
    }

    @Override
    public void deleteSceneMember_CallBack(Scene scene){
        System.out.println(scene.toString());
    }

    @Override
    public void timerTask_CallBack(TimerTask timerTask){
        System.out.println(timerTask.toString());
    }

    @Override
    public void task_CallBack(Task task){
        System.out.println(task.toString());
    }

    @Override
    public void taskDeviceDetail_CallBack(TaskDeviceDetail taskDeviceDetail, String sceneId){
        System.out.println(taskDeviceDetail.toString());
    }

    @Override
    public void taskSceneDetail_CallBack(TaskSceneDetail taskSceneDetail, String sceneId){
        System.out.println(taskSceneDetail.toString());
    }

    @Override
    public void taskTimerDetail_CallBack(TaskTimerDetail taskTimerDetail, String sceneId){
        System.out.println(taskTimerDetail.toString());
    }

    @Override
    public void setGroupName_CallBack(Group group){
        System.out.println(group.toString());
    }

    @Override
    public void changeDeviceName_CallBack(String shortAddress, int endPoint, String name){
        System.out.println("shortAddress:" + shortAddress + " | "
                            + "endPoint:" + endPoint + " | "
                            + "newName:" + name);
    }

    @Override
    public void deleteDevice_CallBack(){
        System.out.println("delete succeed! ");
    }

    @Override
    public void setDeviceState_CallBack(){
        System.out.println("change Device Status succeed! ");
    }

    @Override
    public void setDeviceLevel_CallBack() {
        System.out.println("set device level succeed! ");
    }

    @Override
    public void setDeviceHueAndSat_CallBack() {
        System.out.println("set Device Hue and sat succeed!");
    }

    @Override
    public void addScene_CallBack(Scene scene, SceneService sceneService) {
        System.out.println(scene.toString());
        sceneService.updateScene(scene);

    }

    @Override
    public void callScene_CallBack() {
        System.out.println("call scene succeed! ");
    }

    @Override
    public void getDeviceInfo_CallBack(Device device, String data) {
        System.out.println(device.toString() + "data: "+ data               );
    }

    @Override
    public void changeSceneName_CallBack(Scene scene) {
        System.out.println(scene.toString());
    }

    @Override
    public void setReportTime_CallBack() {
        System.out.println("set report time succeed!");
    }

    @Override
    public void setColorTemperature_CallBack() {
        System.out.println("set device color and Temperature succeed!");
    }

    public void getInfraredVersionCallBack(String token, String version ){
        JsonObject data = new JsonObject();
        data.addProperty("version", version);
        try {
            DataMessageClient.publishAttribute(token, data.toString());

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }


    /**
     * 设备数据上报处理 0x70
     * @param shortAddress
     * @param endPoint
     * @param data
     * @param deviceTokenRelationService
     * @param sceneService
     * @param sceneRelationService
     * @param gatewayGroupService
     * @throws Exception
     */
    @Override
    public void data_CallBack(String shortAddress, int endPoint, JsonObject data, DeviceTokenRelationService deviceTokenRelationService, SceneService sceneService, SceneRelationService sceneRelationService, GatewayGroupService gatewayGroupService) throws Exception {

        System.out.println("上传设备上报数据(0x70)...");

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        try{
            String sceneId = data.get("sceneId").getAsString();
            String gatewayName = deviceTokenRelation.getGatewayName();
            Scene mainScene = sceneService.getSceneByGatewayAndSceneId(gatewayName, sceneId);
            List<Integer> side_scene_ids = sceneRelationService.getSceneRelation(mainScene.getScene_id());
            for(Integer side_scene_id: side_scene_ids){
                Scene sideScene = sceneService.getSceneBySceneId(side_scene_id);
                GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(sideScene.getGatewayName());
                GatewayMethod gatewayMethod = new GatewayMethodImpl();
                gatewayMethod.callScene(sideScene.getSceneId(), gatewayGroup.getIp());
            }
            return;

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        if(deviceTokenRelation!=null) {
            List<DeviceTokenRelation> deviceTokenRelations = deviceTokenRelationService.getRelationByIEEE(deviceTokenRelation.getIEEE());
            for (DeviceTokenRelation deviceTokenRelation1 : deviceTokenRelations) {
                String jsonStr = data.toString();
                DataMessageClient.publishData(deviceTokenRelation1.getToken(), jsonStr);
            }
        }
    }

    public void rpc_callback(String shortAddress, int endPoint, DeviceTokenRelationService dtrs, int requestId, JsonObject data) throws Exception{

        DeviceTokenRelation deviceTokenRelation = dtrs.getRelotionBySAAndEndPoint(shortAddress,endPoint);

        if (deviceTokenRelation == null ) {
            return;
        }

        String gatewayName = deviceTokenRelation.getGatewayName();
        DeviceTokenRelation gatewayTokenRelation = dtrs.getGateway(gatewayName);

        if (gatewayTokenRelation == null) {
            return;
        }

        DataMessageClient.publishResponse(gatewayTokenRelation.getToken(), requestId, data.toString());
    }
}

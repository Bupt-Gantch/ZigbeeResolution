package com.bupt.ZigbeeResolution.test;


import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.service.DataService;
import com.bupt.ZigbeeResolution.transform.SocketServer;
import com.bupt.ZigbeeResolution.transform.TransportHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Arrays;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    static String version = "E20701040000";
    static int matchType = 1;
    static byte endpoint = 1;
    static String shortAddress = "11E4";
    static String keyName = "test";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        for(byte b : result1){
            System.out.print(byteTo16(b)+" ");
        }
        System.out.println("");
        System.out.println("----------");
        result.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }


    // 连接成功后，向server发送消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "hello Server!";
        System.out.println(msg);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    System.out.println("发送获取版本号指令");
                    Device device = new Device();
                    device.setEndpoint(endpoint);
                    device.setShortAddress(shortAddress);
                    ByteBuf encoded = ctx.alloc().buffer(1024);
                    encoded.writeBytes(IR_get_version(device));
                    ctx.writeAndFlush(encoded);
                    try{
                        Thread.sleep(3000);
                    }catch (Exception e){

                    }
                }
            }
        }).start();
    }

    public static byte[] IR_learn(Device device, String version, int matchType, int key){
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
        //byte count = DataService.count_bytes(version_byte);
        //bytes[index] = (byte) (0x0B + count + (byte) 0x83  + matchType + key);
        byte[] countValue = new byte[12];
        System.arraycopy(bytes, index-12, countValue, 0, 12);
        bytes[index] = DataService.count_bytes(countValue);

        return TransportHandler.getSendContent(12, bytes);
    }

    public static byte[] IR_get_version(Device device) {
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

        return TransportHandler.getSendContent(12, bytes);
    }

    public String byteTo16(byte bt){
        String[] strHex={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
        String resStr="";
        int low =(bt & 15);
        int high = bt>>4 & 15;
        resStr = strHex[high]+strHex[low];
        return resStr;
    }
}
package com.bupt.ZigbeeResolution.transform;

import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
import com.bupt.ZigbeeResolution.data.GatewayGroup;
import com.bupt.ZigbeeResolution.http.HttpControl;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;
import com.bupt.ZigbeeResolution.mqtt.DataMessageClient;
import com.bupt.ZigbeeResolution.mqtt.RpcMqttClient;
import com.bupt.ZigbeeResolution.service.*;
import com.google.gson.JsonObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.eclipse.paho.client.mqttv3.MqttClient;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransportHandler extends SimpleChannelInboundHandler<byte[]> implements GenericFutureListener<Future<? super Void>> {
    private UserService userService;
    private GatewayGroupService gatewayGroupService;
    private DeviceTokenRelationService deviceTokenRelationService;
    private SceneService sceneService;
    private SceneRelationService sceneRelationService;

    public static Map<String, MqttClient> mqttMap = new ConcurrentHashMap<>();
    private HttpControl hc = new HttpControl();
    public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static final ChannelGroup channelgroups = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public final static String LoginControlMessage = "Login OK!\r\nControl Mode\r\nGateway online:";
    public final static String LoginMessage = "[Febit FCloud Server V2.0.0]\r\n[Normal Socket Mode]\r\nLogin:";
    private DataService dataService = new DataService();
    public static byte response = 0x00 ;
    byte[] b = new byte[] { 4, 0, 31, 0 };
    byte[] bt = new byte[1024];

    String ipsname = null;
    String ips = null;

    public static GatewayMethodImpl gatewayMethod = new GatewayMethodImpl();

    public TransportHandler(UserService userService, GatewayGroupService gatewayGroupService, DeviceTokenRelationService deviceTokenRelationService, SceneService sceneService, SceneRelationService sceneRelationService) {
        this.userService = userService;
        this.gatewayGroupService = gatewayGroupService;
        this.deviceTokenRelationService = deviceTokenRelationService;
        this.sceneService = sceneService;
        this.sceneRelationService = sceneRelationService;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        System.out.println("网关 " + getIPString(ctx) + " 连接成功");

        // 保存网关 channel
        SocketServer.getMap().put(getIPString(ctx), ctx.channel());
        Channel channel = ctx.channel();

        // 向网关发送 LoginMessage
        bt = getSendContent(80, LoginMessage.getBytes());
        channel.write(bt);

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : group) {
            ch.writeAndFlush(channel.remoteAddress());
        }
        group.add(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 网关 channel 关闭 （断电，断网...）
        String ip =getRemoteAddress(ctx);
        System.out.println("设备下线:"+ip.substring(1));

        // 从 Map 中移除网关 channel
        String gatewayName = gatewayGroupService.getGatewayNameByIp(ip.substring(1));
        if(gatewayName!=null){
            gatewayGroupService.removeGatewayGroupByIp(ip.substring(1));
            SocketServer.getMap().remove(getIPString(ctx));

            mqttMap.get(gatewayName).disconnect();
            mqttMap.get(gatewayName).close();
            mqttMap.remove(gatewayName);
        }
        ctx.close().sync();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
        System.out.println("已经60秒未收到客户端的消息了！");
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state()== IdleState.READER_IDLE){
                String ip =getRemoteAddress(ctx);
                System.out.println("设备超时下线:"+ip.substring(1));
                String gatewayName = gatewayGroupService.getGatewayNameByIp(ip.substring(1));
                if(gatewayName!=null){
                    gatewayGroupService.removeGatewayGroupByIp(ip.substring(1));
                    SocketServer.getMap().remove(getIPString(ctx));

                    mqttMap.get(gatewayName).disconnect();
                    mqttMap.get(gatewayName).close();
                    mqttMap.remove(gatewayName);
                }
                ctx.close().sync();
            }
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
        System.out.println("origin-msg received:" + SocketServer.bytesToHexString(msg));
        String name = null;
        String pwd = null;
        byte byteA3 = msg[2];
        Channel channel = ctx.channel();
        String ctxip = channel.toString();
        String ip = getRemoteAddress(ctx);
        for (Channel ch : group) {
            ips = ip.substring(1);
            String checkGatewayName = gatewayGroupService.getGatewayNameByIp(ips);
            if(checkGatewayName!=null){
                if(!mqttMap.containsKey(checkGatewayName) && byteA3 != 81){
                    DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getGateway(checkGatewayName);
                    RpcMqttClient rpcMqttClient = new RpcMqttClient(checkGatewayName, deviceTokenRelation.getToken(), gatewayGroupService);
                    rpcMqttClient.init();
                }
            }

            if (ch == channel){
                if (byteA3 == 30) { // HeartBeatMessage
                    System.out.println(ctx.toString() + "心跳包" + SocketServer.bytesToHexString(msg));
                    ch.writeAndFlush(b);

                }else if(byteA3 == 81) { // LoginMessage
                    System.out.println(ctx.toString() + "登录认证" + SocketServer.bytesToHexString(msg));
                    String s = new String(msg);
                    if (s.length() == 16) {
                        name = s.substring(6, 11);
                        pwd = s.substring(12, 16);
                    } else if (s.length() == 17) {
                        name = s.substring(6, 12);
                        pwd = s.substring(13, 17);
                    } else if (s.length() == 18) {
                        name = s.substring(6,13);
                        pwd = s.substring(14,18);
                    } else if (s.length() == 19) {
                        name = s.substring(6,14);
                        pwd = s.substring(15,19);
                    }
                    /*  用空格分割网关名和密码
                    JsonObject item = dataService.getItem(s.substring(6, s.length()));
                    name = item.get("name").getAsString();
                    pwd = item.get("pwd").getAsString();
                    */
                    name = name.trim();
                    GatewayGroup gatewayGroup = new GatewayGroup(name, ips, ctxip);
                    System.out.println(ctx.toString() + "网关 " + name + " 登录成功");
                    if(gatewayGroupService.getGatewayGroup(name)!=null){
                        gatewayGroupService.removeGatewayGroupByName(name);
                    }
                    gatewayGroupService.addGatewayGroup(gatewayGroup);

                    DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelotionByGatewayNameAndEndPoint(name, 0);
                    if(deviceTokenRelation == null){  // 网关未接入过平台
                        //hc.httplogin();
                        // 调用 deviceaccess 接口创建设备 TODO 以后改成RPC调用
                        String id = hc.httpcreate("Gateway_"+name, "","Gateway", "");
                        String token = hc.httpfind(id);

                        // mysql.deviceTokenRelation 插入记录
                        DeviceTokenRelation newDeviceTokenRelation = new DeviceTokenRelation(id, 0, token,"Gateway", name,"0000", id);
                        deviceTokenRelationService.addARelation(newDeviceTokenRelation);

                        // 初始化网关的 mqtt 客户端
                        RpcMqttClient rpcMqttClient = new RpcMqttClient(name, token, gatewayGroupService);
                        rpcMqttClient.init();
                        //mqttMap.put(ips, rpcMqttClient.getMqttClient());

                        // 上传网关密码属性
                        JsonObject data = new JsonObject();
                        data.addProperty("pwd", pwd);
                        DataMessageClient.publishAttribute(id,data.toString());

                    }else{ // 网关已接入平台，重新入网
                        // 重新初始化 mqtt 客户端
                        RpcMqttClient rpcMqttClient = new RpcMqttClient(deviceTokenRelation.getGatewayName(), deviceTokenRelation.getToken(), gatewayGroupService);
                        rpcMqttClient.init();
                        //mqttMap.put(ips, rpcMqttClient.getMqttClient());
                    }

                    // 发送 LoginReply 消息
                    bt = getSendContent(10, LoginControlMessage.getBytes());
                    channelgroups.add(channel);
                    ch.writeAndFlush(bt);

                    // 发送查询网关下所有设备指令
                    gatewayMethod.getAllDevice(ips);

                } else if (byteA3 == 12) { // 0x0c
                    // TODO ControlMessage
                    System.out.println(ctx.toString() + "控制数据" + SocketServer.bytesToHexString(msg));
                    //chs.writeAndFlush(msg);

                } else if (byteA3 == 11) { // 0x0b UpdateMessage
                    System.out.println(ctx.toString() + "传感器数据" + SocketServer.bytesToHexString(msg));

                    // 去掉数据包头
                    byte[] body = new byte[msg.length-6];
                    System.arraycopy(msg, 6, body, 0, msg.length-6);
                    String gatewayName = gatewayGroupService.getGatewayNameByIp(ips);

                    // 解析设备上报数据
                    dataService.resolution(body, gatewayName, deviceTokenRelationService, sceneService, gatewayGroupService, sceneRelationService);

                } else {
                    // TODO 其他消息类型
                    System.out.println(ctx.toString() + "未知数据包类型" + SocketServer.bytesToHexString(msg));
                }
            }
        }
    }

    @Override
    public void operationComplete(Future<? super Void> future) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("客户端 " + getRemoteAddress(ctx) + " 关闭了连接");
    }

    public String bytesToHexFun3(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for(byte b : bytes) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }

    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }

    public static String getIPString(ChannelHandlerContext ctx) {
        String ipString = "";
        String socketString = ctx.channel().remoteAddress().toString();
        //int colonAt = socketString.indexOf(":");
        ipString = socketString.substring(1);
        return ipString;
    }

    public static String getRemoteAddress(ChannelHandlerContext ctx) {
        String socketString = "";
        socketString = ctx.channel().remoteAddress().toString();
        return socketString;
    }

    public static byte[] getSendContent(int type, byte[] message) {
        int messageLength = message.length;
        int contentLength = message.length + 6;

        byte message_low = (byte) (messageLength & 0x00ff); // 数据包总长度
        byte message_high = (byte) ((messageLength >> 8) & 0xff);

        byte type_low = (byte) (type & 0x00ff); // 数据包类型
        byte type_high = (byte) ((type >> 8) & 0xff);

        byte content_low = (byte) (contentLength & 0x00ff); // （后续）有效数据长度
        byte content_high = (byte) ((contentLength >> 8) & 0xff);

        // 数据包头部
        byte[] headMessage = new byte[6];
        headMessage[0] = content_low;
        headMessage[1] = content_high;
        headMessage[2] = type_low;
        headMessage[3] = type_high;
        headMessage[4] = message_low;
        headMessage[5] = message_high;

        // 拼接数据包头部和有效数据
        byte[] sendContent = new byte[contentLength];
        System.arraycopy(headMessage, 0, sendContent, 0, 6);
        System.arraycopy(message, 0, sendContent, 6, messageLength);

        return sendContent;
    }
}

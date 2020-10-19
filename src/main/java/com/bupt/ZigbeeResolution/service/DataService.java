package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.common.Common;
//import com.bupt.ZigbeeResolution.common.RpcResult;
import com.bupt.ZigbeeResolution.data.*;
import com.bupt.ZigbeeResolution.method.GatewayMethod;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;
import com.bupt.ZigbeeResolution.mqtt.DataMessageClient;
import com.bupt.ZigbeeResolution.transform.SocketServer;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class DataService {
    private static List<Object> list = new LinkedList<Object>();

    private GatewayMethod gatewayMethod = new GatewayMethodImpl();

    public static void cleatList() {
        list.clear();
    }

    Integer length;
    String shortAddress;
    int endPoint;
    String[] shortAddresses;
    int[] endPoints;
    int taskNameLength;

//    @Autowired
//    private RpcResult rpcResult;

    @Autowired
    private InfraredService infraredService;

    Common instance = Common.getInstance();


    public void resolution(byte[] bytes, String gatewayName, DeviceTokenRelationService deviceTokenRelationService, SceneService sceneService, GatewayGroupService gatewayGroupService, SceneRelationService sceneRelationService) throws Exception {
//        System.out.println("进入");
        System.out.println("R：" + SocketServer.bytesToHexString(bytes));

        byte Response = bytes[0];
        switch (Response) {
            case 0x01:
                Device device = new Device();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                device.setShortAddress(byte2HexStr(Arrays.copyOfRange(bytes, 2, 4)));
                device.setEndpoint(bytes[4]);
                device.setProfileId(byte2HexStr(Arrays.copyOfRange(bytes, 5, 7)));
                device.setDeviceId(byte2HexStr(Arrays.copyOfRange(bytes, 7, 9)));
                if ("0201".equals(device.getDeviceId())) {
                    return;
                }
                device.setState(bytes[9] == 0x01);
                Integer nameLength = Integer.parseInt(String.valueOf(bytes[10]));
                if (nameLength == 0) {
                    device.setName("");
                } else {
                    device.setName(bytesToAscii(Arrays.copyOfRange(bytes, 11, 11 + nameLength)));
                }
                device.setOnlineState(bytes[11 + nameLength]);
                device.setIEEE(byte2HexStr(Arrays.copyOfRange(bytes, 12 + nameLength, 20 + nameLength)));
                Integer snLength = Integer.parseInt(String.valueOf(bytes[20 + nameLength]));
                if (snLength == 0) {
                    device.setSnid("");
                } else {
                    device.setSnid(bytesToAscii(Arrays.copyOfRange(bytes, 21 + nameLength, 21 + nameLength + snLength)));
                }
                device.setZoneType(byte2HexStr(Arrays.copyOfRange(bytes, 21 + nameLength + snLength, 23 + nameLength + snLength)));
                device.setElectric(Double.valueOf(String.valueOf(bytes[23 + nameLength + snLength])));
                device.setRecentState(Arrays.copyOfRange(bytes, length - 4, length));
//                System.out.println("完成解析");
                gatewayMethod.device_CallBack(device, gatewayName, deviceTokenRelationService, gatewayGroupService);
                break;

            case 0x15:
                Gateway gateway = new Gateway();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                gateway.setVersion(bytesToAscii(Arrays.copyOfRange(bytes, 2, 7)));
                gateway.setSnid(byte2HexStr(Arrays.copyOfRange(bytes, 7, 11)));
                gateway.setUsername(bytesToAscii(Arrays.copyOfRange(bytes, 11, 31)));
                gateway.setPassword(bytesToAscii(Arrays.copyOfRange(bytes, 31, 51)));
                gateway.setDeviceNumber(Integer.parseInt(String.valueOf(bytes[51])));
                gateway.setGroupNumber(Integer.parseInt(String.valueOf(bytes[52])));
                gateway.setTimerNumber(Integer.parseInt(String.valueOf(bytes[53])));
                gateway.setSceneNumber(Integer.parseInt(String.valueOf(bytes[54])));
                gateway.setMissionNumber(Integer.parseInt(String.valueOf(bytes[55])));
                gateway.setCompileVersionNumber(byte2HexStr(Arrays.copyOfRange(bytes, 61, 65)));
//                System.out.println("完成解析");
                gatewayMethod.gateway_CallBack(gateway);
                break;

            case 0x07:
                Device stateDevice = new Device();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                stateDevice.setShortAddress(byte2HexStr(Arrays.copyOfRange(bytes, 2, 4)));
                stateDevice.setEndpoint(bytes[4]);
                stateDevice.setState(bytes[5] == 0x01);
//                System.out.println("完成解析");
                gatewayMethod.deviceState_CallBack(stateDevice, deviceTokenRelationService);
                break;

            case 0x08:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                shortAddress = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                endPoint = Integer.parseInt(String.valueOf(bytes[4]));
                int bright = Integer.parseInt(String.valueOf(bytes[5]));
//                System.out.println("完成解析");
                gatewayMethod.deviceBright_CallBack(shortAddress, endPoint, bright);
                break;

            case 0x09:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                shortAddress = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                endPoint = Integer.parseInt(String.valueOf(bytes[4]));
                int hue = Integer.parseInt(String.valueOf(bytes[5]));
//                System.out.println("完成解析");
                gatewayMethod.deviceBright_CallBack(shortAddress, endPoint, hue);
                break;

            case 0x0A:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                shortAddress = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                endPoint = Integer.parseInt(String.valueOf(bytes[4]));
                int saturation = Integer.parseInt(String.valueOf(bytes[5]));
//                System.out.println("完成解析");
                gatewayMethod.deviceBright_CallBack(shortAddress, endPoint, saturation);
                break;

            case 0x0C:
                Group group = new Group();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                group.setGroupId(byte2HexStr(Arrays.copyOfRange(bytes, 2, 4)));
                Integer groupNameLength = Integer.parseInt(String.valueOf(bytes[4]));
                if (groupNameLength == 0) {
                    group.setGroupName("");
                } else {
                    group.setGroupName(bytesToAscii(Arrays.copyOfRange(bytes, 5, 5 + groupNameLength)));
                }
//                System.out.println("完成解析");
                gatewayMethod.group_CallBack(group);
                break;

            case 0x0B:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                String groupId = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                int memberLength = Integer.parseInt(String.valueOf(bytes[4]));
                shortAddresses = new String[memberLength];
                endPoints = new int[memberLength];
                for (int i = 0; i < memberLength; i++) {
                    shortAddresses[i] = byte2HexStr(Arrays.copyOfRange(bytes, 5 + 3 * i, 7 + 3 * i));
                    endPoints[i] = Integer.parseInt(String.valueOf(bytes[7 + 3 * i]));
                }
//                System.out.println("完成解析");
                gatewayMethod.groupMember_CallBack(groupId, shortAddresses, endPoints);
                break;

            case 0x0D:
                Scene scene = new Scene();
                scene.setSceneId(byte2HexStr(new byte[]{bytes[2], bytes[3]}));
                int nameLen = (int) bytes[4];
                byte[] nameByte = new byte[nameLen];
                System.arraycopy(bytes, 5, nameByte, 0, nameLen);
                scene.setSceneName(bytesToAscii(nameByte));
                System.out.println("完成解析");
                // 添加场景,修改场景名的返回值一样
                gatewayMethod.addScene_CallBack(scene, sceneService);
                break;

            case 0x0E:
                scene = new Scene();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                scene.setSceneId(byte2HexStr(Arrays.copyOfRange(bytes, 2, 4)));
                int sceneNameLength = Integer.parseInt(String.valueOf(bytes[4]));
                if (sceneNameLength == 0) {
                    scene.setSceneName("");
                } else {
                    scene.setSceneName(bytesToAscii(Arrays.copyOfRange(bytes, 5, 5 + sceneNameLength)));
                }
                scene.setSceneNumber(Integer.parseInt(String.valueOf(bytes[5 + sceneNameLength])));
//                System.out.println("完成解析");
                gatewayMethod.scene_CallBack(scene);
                break;

            case 0x20:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                String sceneId = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                int deviceCount = Integer.parseInt(String.valueOf(bytes[4]));

                shortAddresses = new String[deviceCount];
                endPoints = new int[deviceCount];
                String[] deviceId = new String[deviceCount];
                byte[] data1 = new byte[deviceCount];
                byte[] data2 = new byte[deviceCount];
                byte[] data3 = new byte[deviceCount];
                byte[] data4 = new byte[deviceCount];
                byte[] IRId = new byte[deviceCount];
                int[] delay = new int[deviceCount];

                for (int i = 0; i < deviceCount; i++) {
                    shortAddresses[i] = byte2HexStr(Arrays.copyOfRange(bytes, 5 + 12 * i, 7 + 12 * i));
                    endPoints[i] = Integer.parseInt(String.valueOf(bytes[7 + 12 * i]));
                    deviceId[i] = byte2HexStr(Arrays.copyOfRange(bytes, 8 + 12 * i, 10 + 12 * i));
                    data1[i] = bytes[10 + 12 * i];
                    data2[i] = bytes[11 + 12 * i];
                    data3[i] = bytes[12 + 12 * i];
                    data4[i] = bytes[13 + 12 * i];
                    IRId[i] = bytes[14 + 12 * i];
                    delay[i] = Integer.parseInt(String.valueOf(bytes[15 + 12 * i]));
                }

//                System.out.println("完成解析");
                gatewayMethod.sceneDetail_CallBack(sceneId, shortAddresses, endPoints, deviceId, data1, data2, data3, data4, IRId, delay);
                break;

            case 0x21:
                Scene deleteScene = new Scene();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                deleteScene.setSceneId(byte2HexStr(Arrays.copyOfRange(bytes, 2, 4)));
                int deleteSceneNameLength = Integer.parseInt(String.valueOf(bytes[4]));
                if (deleteSceneNameLength == 0) {
                    deleteScene.setSceneName("");
                } else {
                    deleteScene.setSceneName(bytesToAscii(Arrays.copyOfRange(bytes, 5, 5 + deleteSceneNameLength)));
                }
//                System.out.println("完成解析");
                gatewayMethod.scene_CallBack(deleteScene);
                break;

            case 0x11:
                TimerTask timerTask = new TimerTask();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                timerTask.setTaskId(Integer.parseInt(String.valueOf(bytes[2])));
                timerTask.setAddressMode(Integer.parseInt(String.valueOf(bytes[3])));
                timerTask.setShortAddress(byte2HexStr(Arrays.copyOfRange(bytes, 4, 6)));
                timerTask.setEndPoint(bytes[6]);
                timerTask.setDay(Integer.parseInt(String.valueOf(bytes[7])));
                timerTask.setHour(Integer.parseInt(String.valueOf(bytes[8])));
                timerTask.setMinute(Integer.parseInt(String.valueOf(bytes[9])));
                timerTask.setSecond(Integer.parseInt(String.valueOf(bytes[10])));
                timerTask.setTaskMode(bytes[11]);
                timerTask.setData1(bytes[12]);
                timerTask.setData2(bytes[13]);
//                System.out.println("完成解析");
                gatewayMethod.timerTask_CallBack(timerTask);
                break;

            case 0x25:
                Task task = new Task();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                task.setTaskType(bytes[2]);
                task.setTaskId(byte2HexStr(Arrays.copyOfRange(bytes, 3, 5)));
                taskNameLength = Integer.parseInt(String.valueOf(bytes[5]));
                if (taskNameLength == 0) {
                    task.setTaskName("");
                } else {
                    task.setTaskName(bytesToAscii(Arrays.copyOfRange(bytes, 6, 6 + taskNameLength)));
                }
//                System.out.println("完成解析");
                gatewayMethod.task_CallBack(task);
                break;

            case 0x24:
                TaskTimerDetail taskTimerDetail = new TaskTimerDetail();
                TaskSceneDetail taskSceneDetail = new TaskSceneDetail();
                TaskDeviceDetail taskDeviceDetail = new TaskDeviceDetail();
                String taskSceneId;
                length = Integer.parseInt(String.valueOf(bytes[1]));

                switch (bytes[3]) {
                    case 0x01:
                        taskTimerDetail.setTaskType(bytes[2]);
                        taskTimerDetail.setDay(Integer.parseInt(String.valueOf(bytes[4])));
                        taskTimerDetail.setHour(Integer.parseInt(String.valueOf(bytes[5])));
                        taskTimerDetail.setMinute(Integer.parseInt(String.valueOf(bytes[6])));
                        taskTimerDetail.setSecond(Integer.parseInt(String.valueOf(bytes[7])));
                        taskSceneId = byte2HexStr(Arrays.copyOfRange(bytes, 24, 26));
                        taskTimerDetail.setIsAlarm(bytes[43]);
                        taskTimerDetail.setIsAble(bytes[44]);
                        taskNameLength = Integer.parseInt(String.valueOf(bytes[49]));
                        if (taskNameLength == 0) {
                            taskTimerDetail.setTaskName("");
                        } else {
                            taskTimerDetail.setTaskName(bytesToAscii(Arrays.copyOfRange(bytes, 50, 50 + taskNameLength)));
                        }
                        taskTimerDetail.setTaskId(byte2HexStr(Arrays.copyOfRange(bytes, 50 + taskNameLength, 52 + taskNameLength)));

//                        System.out.println("完成解析");
                        gatewayMethod.taskTimerDetail_CallBack(taskTimerDetail, taskSceneId);
                        break;
                    case 0x02:
                        taskSceneDetail.setTaskType(bytes[2]);
                        taskSceneDetail.setSceneId(byte2HexStr(Arrays.copyOfRange(bytes, 4, 6)));
                        taskSceneId = byte2HexStr(Arrays.copyOfRange(bytes, 24, 26));
                        taskSceneDetail.setIsAlarm(bytes[43]);
                        taskSceneDetail.setIsAble(bytes[44]);
                        taskNameLength = Integer.parseInt(String.valueOf(bytes[49]));
                        if (taskNameLength == 0) {
                            taskSceneDetail.setTaskName("");
                        } else {
                            taskSceneDetail.setTaskName(bytesToAscii(Arrays.copyOfRange(bytes, 50, 50 + taskNameLength)));
                        }
                        taskSceneDetail.setTaskId(byte2HexStr(Arrays.copyOfRange(bytes, 50 + taskNameLength, 52 + taskNameLength)));

//                        System.out.println("完成解析");
                        gatewayMethod.taskSceneDetail_CallBack(taskSceneDetail, taskSceneId);
                        break;
                    case 0x03:
                        taskDeviceDetail.setTaskType(bytes[2]);
                        taskDeviceDetail.setShortAddress(byte2HexStr(Arrays.copyOfRange(bytes, 4, 6)));
                        taskDeviceDetail.setDeviceId(byte2HexStr(Arrays.copyOfRange(bytes, 6, 8)));
                        taskDeviceDetail.setEndPoint(bytes[8]);
                        taskDeviceDetail.setCondition1(bytes[9]);
                        taskDeviceDetail.setData1(bytesToInt(Arrays.copyOfRange(bytes, 10, 14)));
                        if (bytes[14] != 0x00) {
                            taskDeviceDetail.setCondition2(bytes[14]);
                            taskDeviceDetail.setData2(bytesToInt(Arrays.copyOfRange(bytes, 15, 19)));
                        }
                        taskSceneId = byte2HexStr(Arrays.copyOfRange(bytes, 24, 26));
                        taskDeviceDetail.setIsAlarm(bytes[43]);
                        taskDeviceDetail.setIsAble(bytes[44]);
                        taskNameLength = Integer.parseInt(String.valueOf(bytes[49]));
                        if (taskNameLength == 0) {
                            taskDeviceDetail.setTaskName("");
                        } else {
                            taskDeviceDetail.setTaskName(bytesToAscii(Arrays.copyOfRange(bytes, 50, 50 + taskNameLength)));
                        }
                        taskDeviceDetail.setTaskId(byte2HexStr(Arrays.copyOfRange(bytes, 50 + taskNameLength, 52 + taskNameLength)));

//                        System.out.println("完成解析");
                        gatewayMethod.taskDeviceDetail_CallBack(taskDeviceDetail, taskSceneId);
                        break;
                }
                break;

//            case (byte) 0x26:  // 红外宝
//                int index = 1;
//                int total_length = bytes[index++] & 0xff; // 总长度
//                shortAddress = byte2HexStr(Arrays.copyOfRange(bytes, index, index + 2));
//                index += 2;
//                endPoint = bytes[index++];
//
//                switch (bytes[index++]) {
//                    case 0x02:  // 学习
//                        break;
//                    case 0x03: // 透传
//                        System.out.print("infrared");
//                        break;
//                    case 0x04: // 保存数据到网关
//                        break;
//                    case 0x05: // 查询网关保存的红外数据
//                        break;
//                    case 0x06: // 发送网关保存的红外数据
//                        break;
//                    case 0x07: // 删除网关保存的红外数据
//                        break;
//                    case 0x09: // 缓存透传指令
//                        break;
//                    case 0x0a: // 查询缓存条目数量
//                        break;
//                    default:
//                        break;
//                }
//                break;

            case 0x27:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                shortAddress = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                endPoint = Integer.parseInt(String.valueOf(bytes[4]));
                int colourTemp = (int) ((bytes[5] & 0xFF) | ((bytes[6] & 0xFF) << 8));

//                System.out.println("完成解析");
                gatewayMethod.deviceColourTemp_CallBack(shortAddress, endPoint, colourTemp);
                break;


            case 0x29:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                switch (bytes[2]) {
                    // 更改设备名返回值
                    case 0x03:
                        shortAddress = byte2HexStr(Arrays.copyOfRange(bytes, 4, 6));
                        endPoint = Integer.parseInt(String.valueOf(bytes[6]));
                        String name = bytesToAscii(bytes, 8, Integer.parseInt(String.valueOf(bytes[7])));

                        gatewayMethod.changeDeviceName_CallBack(shortAddress, endPoint, name);
                        break;

                    case 0x04:
                        if (bytes[2] == (byte) 0xa7) {
                            System.out.println("查询网关内保存的红外数据返回");
                        }
                        switch (bytes[3]) {
                            case (byte) 0x95:
                                gatewayMethod.deleteDevice_CallBack();
                                break;
                            case (byte) 0x82:
                                gatewayMethod.setDeviceState_CallBack();
                                break;
                            case (byte) 0x83:
                                gatewayMethod.setDeviceLevel_CallBack();
                                break;
                            case (byte) 0x84:
                                gatewayMethod.setDeviceHueAndSat_CallBack();
                                break;
                            case (byte) 0x92:
                                gatewayMethod.callScene_CallBack();
                                break;
                            case (byte) 0x9E:
                                gatewayMethod.setReportTime_CallBack();
                                break;
                            case (byte) 0xA7:
                                if (bytes[5] == (byte) 0x01) {
                                    System.out.println("透传超时");
                                }
                                break;
                            case (byte) 0xA8:
                                gatewayMethod.setColorTemperature_CallBack();
                                break;
                            case (byte) 0x89:
                                gatewayMethod.setSwitchBindDevice_CallBack();
                        }
                        break;

                    case (byte) 0x93:
                        device = new Device();
                        device.setShortAddress(byte2HexStr(new byte[]{bytes[5], bytes[6]}));
                        device.setEndpoint(bytes[7]);
                        byte[] data = new byte[4];
                        System.arraycopy(bytes, 8, data, 0, 4);
                        String dataStr = byte2HexStr(data);
                        gatewayMethod.getDeviceInfo_CallBack(device, dataStr);
                        break;
                }
                break;

            case (byte) 0x31:
                length = Integer.parseInt(String.valueOf(bytes[2]));
                byte recordId = bytes[3];
                byte[] sourceAddress = Arrays.copyOfRange(bytes, 4, 5);
                byte sourceEndPoint = bytes[6];
                switch (bytes[7]) {
                    case (byte) 0x01:
                        System.out.print("场景 => ");
                        byte scene_id = bytes[9];
                        System.out.print("长度: " + length +
                                " | 记录ID: " + recordId +
                                " | 源地址: " + byte2HexStr(sourceAddress) +
                                " | 源endpoint: " + sourceEndPoint +
                                " | 场景ID: " + scene_id
                        );
                        break;
                    case (byte) 0x02:
                        System.out.print("设备 => ");
                        Integer deviceNum = (int) bytes[9];
                        System.out.print("长度: " + length +
                                " | 记录ID: " + recordId +
                                " | 源地址: " + byte2HexStr(sourceAddress) +
                                " | 源endpoint: " + sourceEndPoint +
                                " | 设备个数: " + deviceNum
                        );
                        for (int i = 0; i < deviceNum - 1; i = i + 3) {
                            System.out.print(" | 目标地址" + i + 1 + ": " + bytes[10 + i]);
                            System.out.print(" | 目标endpoint" + i + 1 + ": " + byte2HexStr(Arrays.copyOfRange(bytes, 11 + i, 12 + i)));
                        }
                        break;
                }
                break;


            case (byte) 0xAF:
                Group newGroupName = new Group();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                newGroupName.setGroupId(byte2HexStr(Arrays.copyOfRange(bytes, 2, 4)));
                Integer newGroupNameLength = Integer.parseInt(String.valueOf(bytes[4]));
                if (newGroupNameLength == 0) {
                    newGroupName.setGroupName("");
                } else {
                    newGroupName.setGroupName(bytesToAscii(Arrays.copyOfRange(bytes, 5, 5 + newGroupNameLength)));
                }
//                System.out.println("完成解析");
                gatewayMethod.setGroupName_CallBack(newGroupName);
                break;

            case 0x70 :
                case 0x72:
                //TODO 设备主动上报（目前只做实验室温湿度传感器和PM2.5传感器） & 红外设备响应
                Double temperature;
                Integer humidity;
                Integer pm;
                Integer alarm;
                Integer attribute_value_length;
                Integer illumination;
                Double onlineStatus;
                JsonObject data = new JsonObject();
                String sceneSelectorUseSceneId;

                length = Integer.parseInt(String.valueOf(bytes[1]));
                String shortAddress = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                Integer endPoint = Integer.parseInt(String.valueOf(bytes[4]));
                Integer requestId = instance.getRequestId(shortAddress+String.valueOf(endPoint));

                String clusterId = byte2HexStr(Arrays.copyOfRange(bytes, 5, 7));
                System.out.println("shortAddress:" + shortAddress + "  endPoint:" + endPoint + "  clusterId:" + clusterId);
                switch (clusterId) {
                    case "0000":  // infrared 完善红外宝功能
                        int seq = (int) bytes[7]; //报告个数
                        int learnKey = -1;
                        int low = 0;
                        int high = 0;
                        String attribute = byte2HexStr(Arrays.copyOfRange(bytes, 8, 10)); // 0A 40
                        JsonObject resJson = new JsonObject();
                        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
//                        DeviceTokenRelation parentDevicceTokenRelation = deviceTokenRelationService.getParentDeviceTokenRelationBySAAndEndpoint(shortAddress,endPoint);
                        switch (bytes[21]) {
                            case (byte) 0x81:  // 匹配
//                                rpcResult.setResult(requestId, (int) bytes[24]);
                                resJson.addProperty("match", (int) bytes[24]);
                                data.addProperty("match", (int) bytes[24]);
                                if ((int) bytes[24] == 0) {
                                    System.out.println("********************* 匹配成功 *********************");
                                } else {
                                    System.out.println("********************* 匹配失败 *********************");
                                }
                                break;
                            case (byte) 0x82:  // 控制
                                low = bytes[24];
                                high = bytes[25] << 8;
                                learnKey = low + high;
                                resJson.addProperty("control", 0);
                                resJson.addProperty("key",learnKey);
                                data.addProperty("control", 0);
                                data.addProperty("key",learnKey);
                                System.out.println("********************* 控制命令：按键功能编号 = " + learnKey+ " *********************");
                                break;
                            case (byte) 0x83:  // 学习
                                int matchType = bytes[23];
                                low = bytes[24];
                                high = bytes[25] << 8;
                                learnKey = low + high;//两字节16进制值转换为int
                                int learnResult = bytes[26];
                                resJson.addProperty("type", matchType);
                                resJson.addProperty("key", learnKey);
                                resJson.addProperty("learn", learnResult);
                                data.addProperty("learn", learnResult);
                                data.addProperty("key", learnKey);

//                                rpcResult.setResult(requestId, learnResult);

                                //保存红外宝学习码
                                if (learnResult == 0) {
                                    System.out.println("********************* 学习成功 *********************");
//                                    infraredService.updateState(deviceTokenRelation.getUuid(), learnKey); //修改学习码状态为成功
                                } else if (learnResult == 1) {
                                    System.out.println("********************* 学习失败 *********************");
                                    infraredService.deleteKey(deviceTokenRelation.getUuid(), learnKey); //删除失败的学习码
                                } else {
                                    System.out.println("********************* 存储器空间已满 *********************");
                                    infraredService.deleteKey(deviceTokenRelation.getUuid(), learnKey);
                                }
                                break;
                            case (byte) 0x84:  // 查询当前设备参数
                                int AC_key = (int) bytes[23] + bytes[24] << 8;
                                int TV_key = (int) bytes[25] + bytes[26] << 8;
                                int STB_key = (int) bytes[27] + bytes[28] << 8;
                                resJson.addProperty("AC_key", AC_key);
                                resJson.addProperty("TV_key", TV_key);
                                resJson.addProperty("STB_key", STB_key);
                                resJson.addProperty("AC", bytes[29] == 0xAA);
                                resJson.addProperty("TV", bytes[30] == 0xAA);
                                resJson.addProperty("STB", bytes[31] == 0xAA);
                                data.addProperty("AC_key", AC_key);
                                data.addProperty("TV_key", TV_key);
                                data.addProperty("STB_key", STB_key);
                                data.addProperty("AC", bytes[29] == 0xAA);
                                data.addProperty("TV", bytes[30] == 0xAA);
                                data.addProperty("STB", bytes[31] == 0xAA);
                                System.out.println("********************* 查询当前设备参数 ==>" + resJson.toString());
                                break;
                            case (byte) 0x85:  // 删除该红外设备某个已学习的键
                                matchType = bytes[23];
                                low = bytes[24];
                                high = bytes[25] << 8;
                                learnKey = low + high;
                                resJson.addProperty("delete", 0);
                                resJson.addProperty("key", learnKey);
                                data.addProperty("delete", 0);
                                infraredService.deleteKey(deviceTokenRelation.getUuid(), learnKey); //删除
                                System.out.println("********************* 删除学习键 " + learnKey+ " 成功 *********************");
                                break;
                            case (byte) 0x86:  // 删除该红外设备全部已学习数据
                                resJson.addProperty("deleteAll", 0);
                                data.addProperty("deleteAll", 0);
                                infraredService.deletePanels(deviceTokenRelation.getUuid());//删除全部
                                System.out.println("********************* 删除该红外设备全部数据成功 *********************");
                                break;
                            case (byte) 0x8A:
                                resJson.addProperty("exit", 0);
//                                json.addProperty("命令类型", "退出");
                                data.addProperty("exit", 0);
                                System.out.println("********************* 退出匹配或学习状态成功 *********************");
                                break;
                            default:
                                if (bytes[14] == 0x06) { // 获取版本号
                                    String version = byte2HexStr(Arrays.copyOfRange(bytes, 15, 21));
                                    JsonObject j = new JsonObject();
                                    j.addProperty("version", version);
                                    DataMessageClient.publishAttribute(deviceTokenRelation.getToken(), j.toString());
                                    System.out.println("********************* 读取版本号成功, version ==> " + version + " *********************");
                                } else {
                                    System.err.println("********************* Unknown instruction type " + String.valueOf(bytes[21]) + " *********************");
                                }
                                break;
                        }

                        // 发送数据到平台
                        try {
                            // rpc 调用返回
                            if (resJson != null) {
                                gatewayMethod.rpc_callback(shortAddress, endPoint, deviceTokenRelationService, requestId, resJson);
                            }
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                        break;

                    case "0204":  // 温度传感器上报数据
                        for (int i = 0; i < Integer.parseInt(String.valueOf(bytes[7])); i++) {
                            if (byte2HexStr(Arrays.copyOfRange(bytes, 8 + i * 5, 10 + i * 5)).equals("0000")) {
                                if (bytes[10 + i * 5] == 0x29) {
                                    BigDecimal b = new BigDecimal((double) dataBytesToInt(Arrays.copyOfRange(bytes, 11 + i * 5, 13 + i * 5)) / (double) 100);
                                    temperature = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    //System.out.println(temperature);
                                    data.addProperty("temperature", temperature);
                                }
                            } else if (byte2HexStr(Arrays.copyOfRange(bytes, 8 + i * 5, 10 + i * 5)).equals("1100")) { // TODO 旧版本API文档表示 0204是温湿度
                                if (bytes[10 + i * 5] == 0x29) {
                                    humidity = dataBytesToInt(Arrays.copyOfRange(bytes, 11 + i * 5, 13 + i * 5));
                                    data.addProperty("humidity", humidity.doubleValue());
                                }
                            }
                        }
                        break;

                    case "0504":  // 湿度传感器上报数据
                        for (int i = 0; i < Integer.parseInt(String.valueOf(bytes[7])); i++) {
                            if (byte2HexStr(Arrays.copyOfRange(bytes, 8 + i * 5, 10 + i * 5)).equals("0000")) {  // 0x0000表示湿度测量值
                                if (bytes[10 + i * 5] == 0x29) {  // 数据类型: uint16
                                    humidity = dataBytesToInt(Arrays.copyOfRange(bytes, 11 + i * 5, 13 + i * 5));
                                    data.addProperty("humidity", humidity.doubleValue());
                                }else if (bytes[10 + i * 5] == 0x21) { //TODO 新版中 0x21数据类型才是上报湿度信息 2020-7-14 16:51:09
                                    humidity = dataBytesToInt(Arrays.copyOfRange(bytes, 11 + i * 5, 13 + i * 5));
                                    data.addProperty("humidity", humidity.doubleValue());
                                    System.out.println("走到了0x21数据为:" + humidity);
                                }
                            }
                        }
                        break;

                    case "1504":  // PM2.5上报
                        String[] PM = {"PM1.0", "PM2.5", "PM10"};
                        for (int i = 0; i < Integer.parseInt(String.valueOf(bytes[7])); i++) {
                            if (byte2HexStr(Arrays.copyOfRange(bytes, 8 + i * 5, 10 + i * 5)).equals("0100")) {
                                if (bytes[10 + i * 5] == 0x21) {
                                    pm = dataBytesToInt(Arrays.copyOfRange(bytes, 11 + i * 5, 13 + i * 5));
                                    data.addProperty("PM1.0", pm.doubleValue());
                                }
                            } else if (byte2HexStr(Arrays.copyOfRange(bytes, 8 + i * 5, 10 + i * 5)).equals("0000")) {
                                if (bytes[10 + i * 5] == 0x21) {
                                    pm = dataBytesToInt(Arrays.copyOfRange(bytes, 11 + i * 5, 13 + i * 5));
                                    data.addProperty("PM2.5", pm.doubleValue());
                                }
                            } else if (byte2HexStr(Arrays.copyOfRange(bytes, 8 + i * 5, 10 + i * 5)).equals("0200")) {
                                if (bytes[10 + i * 5] == 0x21) {
                                    pm = dataBytesToInt(Arrays.copyOfRange(bytes, 11 + i * 5, 13 + i * 5));
                                    data.addProperty("PM10", pm.doubleValue());
                                }
                            }
                        }
                        break;

                    case "0604":
                        for (int i = 0; i < Integer.parseInt(String.valueOf(bytes[7])); i++) {
                            if (byte2HexStr(Arrays.copyOfRange(bytes, 8 + i * 5, 10 + i * 5)).equals("0000")) {
                                if (bytes[10 + i * 5] == 0x21) {
                                    alarm = Integer.parseInt(String.valueOf(bytes[11 + i * 5]));
                                    data.addProperty("PIR_status", alarm.doubleValue());
                                }
                            }
                        }
                        break;

                    case "0005":  // 报警设备
                        for (int i = 0; i < Integer.parseInt(String.valueOf(bytes[7])); i++) {
                            String message_type = byte2HexStr(Arrays.copyOfRange(bytes, 8 + i * 5, 10 + i * 5));
                            if (message_type.equals("8000")) {  // 有设备上传报警状态
                                if (bytes[10 + i * 5] == 0x21) {  // 属性数据类型为 uint16 -> 0xffff
//                                    alarm = dataBytesToInt(Arrays.copyOfRange(bytes, 11+i*5, 13+i*5));
                                    String[] attribute_array = {"alarm", "alarm", "tamper", "battery", "surpervision", "restore", "trouble", "ac"};
                                    byte[] attribute_value = byteToBit(bytes[11 + i * 5]);
                                    if (attribute_value[0] == 0x1 || attribute_value[1] == 0x1) {  // bit0 和 bit1 表示报警状态
                                        data.addProperty("alarm", 1D);
                                    } else {
                                        data.addProperty("alarm", 0D);
                                    }
                                    for (int j = 2; j < 8; j++) {  // 暂时只考虑低位字节，高位字节全0不考虑
                                        data.addProperty(attribute_array[j], (double) attribute_value[j]);//
                                    }
//                                    if (alarm== 1 || alarm == 21) {   // 人体红外报警、水浸
//                                        data.addProperty("alarm", 1D);
//                                    } else if (alarm == 17) {  // 烟感报警
//                                        data.addProperty("alarm", 1D);
//                                    } else if (alarm == 5) {   // 吸顶红外 、 门磁(0x04是关门)
//                                        data.addProperty("alarm", 1D);
//                                    } else if (alarm == 2) {   // 紧急按钮
//                                        data.addProperty("alarm", 1D);
//                                    } else {    // 0x10  0x15  0x11  0x00
//                                        data.addProperty("alarm", 0D);
//                                    }

                                }
                            } else if (message_type.equals("8100")) {  // 设备注册成功
                                // 数据类型 -> 这里用字节数表示（默认为无符号整型）
                                attribute_value_length = (int) (bytes[10 + i * 5]) - 31;
                                // 注册成功后的 ZoneID
                                String zone_id = byte2HexStr(Arrays.copyOfRange(bytes, 11 + i * 5, 13 + i * 5));
                                // 设备的ZoneType
                                String zone_type = byte2HexStr(Arrays.copyOfRange(bytes, 13 + i * 5, 15 + i * 5));
                                data.addProperty("zone_id", zone_id);
                                data.addProperty("zone_type", zone_type);

                            } else if (message_type.equals("0100")) { // ZoneType 返回
                                byte data_type = bytes[10 + i * 5];
                                String zone_type = byte2HexStr(Arrays.copyOfRange(bytes, 11 + i * 5, 13 + i * 5));
                                data.addProperty("zone_type", zone_type);
                            }
                        }
                        break;

                    case "0004":  // 光照传感器
                        for (int i = 0; i < Integer.parseInt(String.valueOf(bytes[7])); i++) {
                            if (byte2HexStr(Arrays.copyOfRange(bytes, 8 + i * 5, 10 + i * 5)).equals("0000")) {
                                if (bytes[10 + i * 5] == 0x21) {
                                    illumination = dataBytesToInt(Arrays.copyOfRange(bytes, 11 + i * 5, 13 + i * 5));
                                    data.addProperty("illumination", illumination.doubleValue());
                                }
                            }
                        }
                        break;

                    case "F0F0":
                        for (int i = 0; i < Integer.parseInt(String.valueOf(bytes[7])); i++) {
                            if (byte2HexStr(Arrays.copyOfRange(bytes, 8 + i * 5, 10 + i * 5)).equals("F0F0")) {
                                if (bytes[10 + i * 5] == 0x20) {
                                    sceneSelectorUseSceneId = byte2HexStr(Arrays.copyOfRange(bytes, 11 + i * 5, 12 + i * 5)) + "00";
                                    data.addProperty("sceneId", sceneSelectorUseSceneId);
                                }
                            }
                        }
                        break;

                    case "EEFB":  // 入网报告
                        for (int i = 0; i < Integer.parseInt(String.valueOf(bytes[7])); i++) {
                            if (byte2HexStr(Arrays.copyOfRange(bytes, 8 + i * 5, 10 + i * 5)).equals("D0F0")) {
                                if (bytes[10 + i * 5] == 0x20) {
                                    if (Integer.parseInt(String.valueOf(bytes[11 + i * 5])) == 3) {  //在网
                                        onlineStatus = 1D;
                                    } else {
                                        onlineStatus = 0D;
                                    }
                                    data.addProperty("online", onlineStatus);
                                    System.out.println("onlineStatus = " + onlineStatus);
                                }
                            }
                        }
                        break;

                    case "0101":
                        int amount = Integer.parseInt(String.valueOf(bytes[7]));
                        if (byte2HexStr(Arrays.copyOfRange(bytes, 8, 10)).equals("F5F0")) {
                            String dataType;
                            if (bytes[10] == 0x42) {
                                dataType = "String";
                            }
                            int length = (int) (bytes[11] & 0xFF);
                            if (bytes[12] == 0x20) {
                                switch (bytes[13]) {
                                    case 0x00:
                                        data.addProperty("unlock method", "password");
                                        break;
                                    case 0x02:
                                        data.addProperty("unlock method", "fingerprint");
                                        break;
                                    case 0x03:
                                        data.addProperty("unlock method", "card");
                                        break;
                                    case 0x04:
                                        data.addProperty("unlock method", "remote");
                                        break;
                                    case 0x05:
                                        data.addProperty("unlock method", "multiple ways");
                                        break;
                                }
                                data.addProperty("operate", Integer.parseInt(String.valueOf(bytes[14])));
                                data.addProperty("userId", byte2HexStr(Arrays.copyOfRange(bytes, 15, 17)));
                                data.addProperty("eventTime", byte2HexStr(Arrays.copyOfRange(bytes, 18, 22)));
                                int lockStateLength = (int) (bytes[22] & 0xFF);
                                byte[] lockState = byteToBit(bytes[23]);
                                String lockStateValue = "";
                                for (int i = 0; i < 8; i++) {
                                    if (lockState[i] == 0x01) {
                                        switch (i) {
                                            case 0:
                                                lockStateValue = lockStateValue + "|Enable the door lock to open normally";
                                                break;
                                            case 1:
                                                lockStateValue = lockStateValue + "|Disable the door lock to open normally";
                                                break;
                                            case 3:
                                                lockStateValue = lockStateValue + "|Verify the administrator to enter the menu";
                                                break;
                                            case 4:
                                                lockStateValue = lockStateValue + "|Double verification mode";
                                                break;
                                            case 7:
                                                lockStateValue = lockStateValue + "|Duress alarm";
                                                break;
                                        }
                                    }
                                }
                                data.addProperty("lockState", lockStateValue);
                            } else if (bytes[12] == 0x01) {
                                data.addProperty("operate", 2);
                                if (bytes[13] != 0x00) {
                                    return;
                                }
                            } else if (bytes[12] == 0x00) {
                                data.addProperty("operate", 1);
                                if (bytes[13] != 0x00) {
                                    return;
                                }
                            }
                        }
                        break;

                    case "0100":  // 电压值上报
                        int atrribute_report_count = Integer.parseInt(String.valueOf(bytes[7]));
                        // 电池电压
                        if (byte2HexStr(Arrays.copyOfRange(bytes, 8, 10)).equals("2000")) {
                            if (bytes[10] == 0x20) {
                                double battery_voltage = ((int) (bytes[11] & 0xFF)) / 10D;
                                data.addProperty("voltage", battery_voltage);
                            }
                        }
                        // 电池电量
                        if (byte2HexStr(Arrays.copyOfRange(bytes, 12, 14)).equals("2100")) {
                            if (bytes[14] == 0x20) {
                                double electricPercent = ((int) (bytes[15] & 0xFF)) / 2D;
                                data.addProperty("electric(%)", electricPercent);
                            }
                        }
                        // 电池状态
                        if (byte2HexStr(Arrays.copyOfRange(bytes, 16, 18)).equals("3E00")) {
                            if (bytes[18] == 0x1B) {
                                // 电池欠压
                                if (byte2HexStr(Arrays.copyOfRange(bytes, 15, 19)).equals("00000001")) {
                                    data.addProperty("lowPowerAlarm", true);
                                }
                                // 电池正常
                                else if (byte2HexStr(Arrays.copyOfRange(bytes, 15, 19)).equals("00000000")) {
                                    data.addProperty("lowPowerAlarm", false);
                                }
                            }
                        }

                        break;

                    case "0900":  // TODO 0x0009 表示这条数据包指示设备的一些报警信息
                        for (int i = 0; i < Integer.parseInt(String.valueOf(bytes[7])); i++) { // 表示 x 个属性上报
                            // 报警命令帧类型 (0x0501安防遥控器，0xf5f0遥控器)
                            String alarm_frame_type = byte2HexStr(Arrays.copyOfRange(bytes, 8, 10));
                            // 剩余数据长度
                            int remaining_data_length = (int) (bytes[11]);
                            // 遥控器发出的命令类型 (0x00是 ARM 命令)
                            byte instruction_type = bytes[12];
                            // 报警模式 (0x00 撤防， 0x01在家布防， 0x02夜间布防， 0x03布防)
                            byte alarm_type = bytes[13];
                            // 密码长度
                            int password_length = bytes[14];
                            // 密码
                            String password = AsciiStringToString(byte2HexStr(Arrays.copyOfRange(bytes, 15, 15 + password_length)));
                            // ZoneID
                            byte zone_id = bytes[15 + password_length];
                        }
                        break;
                }
                gatewayMethod.data_CallBack(shortAddress, endPoint, data, deviceTokenRelationService, sceneService, sceneRelationService, gatewayGroupService);
                break;
        }

        instance.removeRequestId(shortAddress+endPoint);
        System.out.println("完成解析");
    }

    /**
     * 十六进制字符串转十进制
     *
     * @param hex 十六进制字符串
     * @return 十进制数值
     */
    public static int hexStringToAlgorism(String hex) {
        hex = hex.toUpperCase();
        int max = hex.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = hex.charAt(i - 1);
            int algorism = 0;
            if (c >= '0' && c <= '9') {
                algorism = c - '0';
            } else {
                algorism = c - 55;
            }
            result += Math.pow(16, max - i) * algorism;
        }
        return result;
    }

    /**
     * ASCII码字符串转数字字符串
     *
     * @param
     * @return 字符串
     */
    public static String AsciiStringToString(String content) {
        String result = "";
        int length = content.length() / 2;
        for (int i = 0; i < length; i++) {
            String c = content.substring(i * 2, i * 2 + 2);
            int a = hexStringToAlgorism(c);
            char b = (char) a;
            String d = String.valueOf(b);
            result += d;
        }
        return result;
    }

    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
//			sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    public static String bytesToAscii(byte[] bytes, int offset, int dateLen) {
        if ((bytes == null) || (bytes.length == 0) || (offset < 0) || (dateLen <= 0)) {
            return null;
        }
        if ((offset >= bytes.length) || (bytes.length - offset < dateLen)) {
            return null;
        }

        String asciiStr = null;
        byte[] data = new byte[dateLen];
        System.arraycopy(bytes, offset, data, 0, dateLen);
        try {
            asciiStr = new String(data, "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
        }
        return asciiStr;
    }

    public static String bytesToAscii(byte[] bytes) {
        return bytesToAscii(bytes, 0, bytes.length);
    }


    public static List<Object> getList() {
        return list;
    }

    public static int bytesToInt(byte[] src) {
        int value;
        value = (int) ((src[0] & 0xFF)
                | ((src[1] & 0xFF) << 8)
                | ((src[2] & 0xFF) << 16)
                | ((src[3] & 0xFF) << 24));
        return value;
    }

    public static int dataBytesToInt(byte[] src) {
        int value;
        value = (int) ((src[0] & 0xFF)
                | ((src[1] & 0xFF) << 8));
        return value;
    }

    public static String deviceId2Type(String deviceId) {
        String type = null;
        switch (deviceId) {
            case "0000":
                break;
            case "0100":
                break;
            case "0200":
                type = "switch";
                break;
            case "0300":
                break;
            case "0400":
                type = "sceneSelector";
                break;
            case "0500":
                break;
            case "0600":
                break;
            case "0700":
                break;
            case "0800":
                break;
            case "0900":
                type = "outlet";
                break;
            case "0A00":
                type = "lock";
                break;
            case "0101":
                type = "dimmableLight";
                break;
            case "0601":
                type = "lightSensor";
                break;
            case "6101":
                type = "infrared";
                break;
            case "6301":
                type = "newInfrared";
                break;
            case "0304":
                type = "SoundLightAlarm";
                break;
            case "0202":
                type = "curtain";
                break;
            case "0203":
                type = "temperature";
                break;
            case "0903":
                type = "PM2.5";
                break;
            case "0204":
                type = "IASZone";
                break;
            default:
                type = "unknown";
                break;
        }
        return type;
    }

    public static byte[] byteToBit(byte n) {
        byte[] bit = new byte[8];
        for (int i = 0; i < 8; i++) {
            bit[i] = (byte) ((n >> i) & 0x1);
        }
        return bit;
    }

    public static byte[] intArray2ByteArray(int[] version_int) {
        byte[] bytes = new byte[version_int.length];
        for (int i = 0; i < version_int.length; i++) {
            bytes[i] = (byte) (0xFF & version_int[i]);
        }
        return bytes;
    }

    public static byte count_bytes(byte[] bytes) {
        if (null == bytes || bytes.length <= 0) {
            return 0x00;
        }
        byte result = 0x00;
        for (byte B : bytes) {
            result += B;
        }
        return (byte) result;
    }

    public static byte[] byteMergerAll(byte[]... values) {
        int length_byte = 0;
        for (int i = 0; i < values.length; i++) {
            length_byte += values[i].length;
        }
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (int i = 0; i < values.length; i++) {
            byte[] b = values[i];
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }

    public JsonObject getItem(String s) {
        if (s.length() <= 0)
            return null;
        JsonObject item = new JsonObject();

        int i = 0;
        String tmp = "";
        while (i <= s.length() - 1 || s.charAt(i) != ' ') {
            tmp += s.charAt(i);
            i++;
        }
        i++; // skip 0x20
        item.addProperty("name", tmp);
        tmp = "";
        while (i <= s.length() - 1) {
            tmp += s.charAt(i);
            i++;
        }
        item.addProperty("pwd", tmp);
        return item;
    }
}

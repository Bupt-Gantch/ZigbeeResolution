package com.bupt.ZigbeeResolution.method;

import com.bupt.ZigbeeResolution.data.*;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.GatewayGroupService;
import com.bupt.ZigbeeResolution.service.SceneRelationService;
import com.bupt.ZigbeeResolution.service.SceneService;
import com.google.gson.JsonObject;

public interface GatewayMethod {
    void getAllDevice(String ip) throws Exception;

    void getGatewayInfo() throws Exception;

    void getDeviceState(Device device);

    void getDeviceBright(Device device);

    void getDeviceHue(Device device);

    void getDeviceSaturation(Device device);

    void getGroup();

    void getGroupMember(Group group);

    void getScene();

    void getSceneDetail(Scene scene);

    void deleteSceneMember(Scene scene,Device device, String ip);

    void getTimerTask();

    void getTask();

    void getTaskDetail(Task task);

    void getDeviceColourTemp(Device device);

    void setGroupName(Group group, String name);

    // 修改设备名
    void changeDeviceName(Device device, String name);

    //删除指定设备
    void deleteDevice(Device device,String ip);

    //设置指定设备的开关状态
    void setDeviceState(Device device, byte state, String ip);

    //设置指定设备的开关状态
    void setSoundLightAlarmState(Device device, byte state, String ip);

    //设置指定设备的开关状态
    void setAlarmState(Device device, byte state, String ip, int time);

    // 设置指定设备的亮度
    void setDeviceLevel(Device device ,byte value, int transition, String ip);

    // 设置指定设备的颜色
    void setDeviceHueAndSat(Device device, byte hue, byte sat, int transition);

    // 添加场景
    void addScene(Device device , byte state, byte data2,
                  byte data3, byte data4, String sceneName,
                  byte irId, int transition, byte funcId,
                  String ip);
    // 报警器
    void addScene(Device device , byte state, byte data2,
                  byte data3, byte data4, String sceneName,
                  byte irId, int transition);

    // 调用场景
    void callScene(String sceneId, String ip);

    // 获取设备信息
    void getDeviceInfo(Device device);

    // 修改场景名
    void changeSceneName(String sceneId, String sceneName);

    // 设置报告间隔时间
    void setReportTime(Device device, String clusterId,
                       String attribId, String dataType, int time);

    // 设置指定设备的色温
    void setColorTemperature(Device device, int  value, int transition);

    // 命令场景开关绑定场景
    void setSwitchBindDevice(Device sceneSelector, Device device, String ip);

    // 命令场景开关绑定场景
    void setSwitchBindScene(Device device, String sceneId, String ip);

    // 获取绑定记录
    void getBindRecord(Device device, String ip);

    void setLock(Device device, int[] password, String ip, byte state);

    void controlIR(Device device, String ip, byte[] data, byte method);

    // 查询红外版本
    void IR_get_version(Device device, String ip);

    // 红外设备匹配
    void IR_match(Device device, String ip, String version, int matchType);

    // 透传情况
    void IR_penetrate(Device device, String ip, String version, int seq, int matchType, int key);

    // 红外学习
    void IR_learn(Device device, String ip, String version, int matchType, int key);

    // 查询当前红外设备参数
    void IR_query_current_device_params(Device device, String ip, String version);

    // 删除某个学习好的按键数据
    void IR_delete_learnt_key(Device device, String ip, String version, int matchType, int key);

    // 删除所有学习好的按键数据
    void IR_delete_learnt_all_key(Device device, String ip, String version);

    // 退出学习或匹配
    void IR_exit_learn_or_match(Device device, String ip);

    // 保存数据到网关
    void IR_save_data_to_gateway(Device device, String ip, byte[] data, String name);

    // 查询网关内保存的红外数据时
    void IR_get_gatewayData(String ip);

    // 发送网关内保存的红外数据
    void IR_send_gatewayData(Device device, String ip);

    // 删除网关内保存的红外数据
    void IR_delete_gatewayData(Device device, String ip);

    // 缓存透传指令
    void IR_cache_pass_throwgh(Device device, String ip, byte[] data);

    // 查询缓存条目数量
    void IR_get_cache_quantity(Device device, String ip);

    // 取消场景开关和场景的绑定
    void cancelBindOfSwitchAndScene(Device device, String clusterId);

    void cancelBindOfSwitchAndDevice(Device sceneSelector, Device device, String ip);

    void permitDeviceJoinTheGateway(String ip);

    void permitDeviceJoinTheGateway_CallBack();

    void setSwitchBindDevice_CallBack();

    void setSwitchBindScene_CallBack();

    void getBindRecord_CallBack();

    void cancelBindOfSwitchAndScene_CallBack();

    void device_CallBack(Device device, String gatewayName, DeviceTokenRelationService deviceTokenRelationService, GatewayGroupService gatewayGroupService) throws Exception;

    void gateway_CallBack(Gateway gateway);

    void deviceState_CallBack(Device device, DeviceTokenRelationService deviceTokenRelationService);

    void deviceBright_CallBack(String shortAddress, int endPoint, int bright);

    void deviceHue_CallBack(String shortAddress, int endPoint, int hue);

    void deviceSaturation_CallBack(String shortAddress, int endPoint, int saturation);

    void deviceColourTemp_CallBack(String shortAddress, int endPoint, int colourTemp);

    void group_CallBack(Group group);

    void groupMember_CallBack(String groupId, String[] shortAddress, int[] endPoint);

    void scene_CallBack(Scene scene);

    void sceneDetail_CallBack(String sceneId, String[] shortAddress, int[] endPoint, String[] deviceId, byte[] data1, byte[] data2, byte[] data3, byte[] data4, byte[] IRId, int[] delay);

    void deleteSceneMember_CallBack(Scene scene);

    void timerTask_CallBack(TimerTask timerTask);

    void task_CallBack(Task task);

    void taskSceneDetail_CallBack(TaskSceneDetail taskSceneDetail, String sceneId);

    void taskTimerDetail_CallBack(TaskTimerDetail taskTimerDetail, String sceneId);

    void taskDeviceDetail_CallBack(TaskDeviceDetail taskDeviceDetail, String sceneId);

    void setGroupName_CallBack(Group group);

    void changeDeviceName_CallBack(String shortAddress, int endPoint, String name);

    void deleteDevice_CallBack();

    void setDeviceState_CallBack();

    void setDeviceLevel_CallBack();

    void setDeviceHueAndSat_CallBack();

    void addScene_CallBack(Scene scene, SceneService sceneService);

    void callScene_CallBack();

    void getDeviceInfo_CallBack(Device device, String data);

    void changeSceneName_CallBack(Scene scene);

    void setReportTime_CallBack();

    void setColorTemperature_CallBack();

    void data_CallBack(String shortAddress, int endPoint, JsonObject data, DeviceTokenRelationService deviceTokenRelationService, SceneService sceneService, SceneRelationService sceneRelationService,  GatewayGroupService gatewayGroupService) throws Exception;

    void rpc_callback(String shortAddress, int endPoint, DeviceTokenRelationService dtrs, int requestId, JsonObject data)throws Exception;
}

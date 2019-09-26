package com.bupt.ZigbeeResolution.mqtt;

import com.bupt.ZigbeeResolution.common.Common;
import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
<<<<<<< HEAD
import com.bupt.ZigbeeResolution.data.OpLog;
=======
import com.bupt.ZigbeeResolution.data.Key;
>>>>>>> devpeng
import com.bupt.ZigbeeResolution.method.GatewayMethod;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.GatewayGroupService;
import com.bupt.ZigbeeResolution.service.InfraredService;
import com.bupt.ZigbeeResolution.transform.TransportHandler;
<<<<<<< HEAD
=======
import com.bupt.ZigbeeResolution.util.SpringUtil;
>>>>>>> devpeng
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
<<<<<<< HEAD
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
=======
>>>>>>> devpeng

public class RpcMessageCallBack implements MqttCallback{
	private String token;
	private GatewayGroupService gatewayGroupService;
	private RpcMqttClient rpcMqttClient;
	private GatewayMethod gatewayMethod = new GatewayMethodImpl();
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	Common instance = Common.getInstance();

	InfraredService irService = (InfraredService) SpringUtil.getBean("infraredService");

	DeviceTokenRelationService deviceTokenRelationService = (DeviceTokenRelationService)SpringUtil.getBean("deviceTokenRelationService");

	public RpcMessageCallBack(MqttClient rpcMqtt,String token, GatewayGroupService gatewayGroupService, String gatewayName){
		this.token = token;
		this.rpcMqttClient = new RpcMqttClient(gatewayName,token, gatewayGroupService);
		this.gatewayGroupService = gatewayGroupService;
	}
	public RpcMessageCallBack(){}

	@Override
	public void connectionLost(Throwable arg0) {
<<<<<<< HEAD
		logger.warn("进入mqtt断线回调");
		//System.out.println("进入mqtt断线回调");
=======
		System.out.println("进入mqtt断线回调" + arg0.getCause());
>>>>>>> devpeng
		//SecondActivity.wrapper.init();
		while (!rpcMqttClient.init()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void messageArrived(String topic, MqttMessage msg) throws Exception {
<<<<<<< HEAD
		System.out.println(msg);
=======
		System.out.printf("received rpc message, topic %s , msg " + msg, topic);

>>>>>>> devpeng
		JsonObject jsonObject = new JsonParser().parse(new String(msg.getPayload())).getAsJsonObject();
		String shortAddress = jsonObject.get("shortAddress").getAsString();
		byte endpoint = jsonObject.get("Endpoint").getAsByte();

		// store requestId
		Integer position = topic.lastIndexOf("/");
		Integer requestId = Integer.parseInt(topic.substring(position+1));
		System.out.println(requestId);
		instance.putRequestId(shortAddress+endpoint, requestId);

<<<<<<< HEAD
		switch (jsonObject.get("serviceName").getAsString()){
=======
		String serviceName = jsonObject.get("serviceName").getAsString();
		String methodName = jsonObject.get("methodName").getAsString();

		switch (serviceName){
>>>>>>> devpeng
			case "control switch":
				switch (methodName){
					case "setstate":
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(shortAddress);
							controlDevice.setEndpoint(endpoint);

							byte state;
							if (jsonObject.get("status").getAsString().equals("true")) {
								state = 0x01;
							} else {
								state = 0x00;
							}

							String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
							if (ip == null) {
								OpLog opLog = new OpLog("switch", "set status","fail", "gateway is offline");
								logger.error(opLog.toString());
								//System.out.println("Gateway offline");
								//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
							}
							OpLog opLog = new OpLog("switch", "set status","succeed");
							logger.info(opLog.toString());
							System.out.println("gateway ip: " + ip );
							gatewayMethod.setDeviceState(controlDevice, state, ip);
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"success");
						}catch (Exception e){
<<<<<<< HEAD
							OpLog opLog = new OpLog("switch", "set status","exception", e.getMessage());
							logger.error(opLog.toString());
							//System.out.println(e);
=======
							System.out.println(e.getMessage());
>>>>>>> devpeng
						}

						break;
				}
				break;

			case "control dimmableLight":
				switch (methodName){
					case "setbright":
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(shortAddress);
							controlDevice.setEndpoint(endpoint);

							byte bright;
							bright = (byte)(0xFF & Integer.parseInt(jsonObject.get("bright").getAsString()));
							//System.out.println("进入控制");

							String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
							if (ip == null) {
								OpLog opLog = new OpLog("dimmableLight", "set bright","fail", "gateway is offline");
								logger.error(opLog.toString());
								//System.out.println("Gateway offline");
								//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
							}

							OpLog opLog = new OpLog("dimmableLight", "set bright","succeed");
							logger.info(opLog.toString());
							System.out.println("gateway ip: " + ip );
							gatewayMethod.setDeviceLevel(controlDevice, bright, 0, ip);
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"success");
						}catch (Exception e){
							OpLog opLog = new OpLog("dimmableLight", "set bright","exception", e.getMessage());
							logger.error(opLog.toString());
						}
						break;
				}
				break;

			case "control curtain":
				switch (methodName){
					case "setstate":
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(shortAddress);
							controlDevice.setEndpoint(endpoint);

							byte state;
							state = (byte)(0xFF & Integer.parseInt(jsonObject.get("status").getAsString()));
							//System.out.println("进入控制");

							String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
							if (ip == null) {
								OpLog opLog = new OpLog("curtain", "setstate","fail", "gateway is offline");
								logger.error(opLog.toString());
								//System.out.println("Gateway offline");
								//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
							}

							OpLog opLog = new OpLog("curtain", "setstate","succeed");
							logger.info(opLog.toString());
							System.out.println("gateway ip: " + ip );
							gatewayMethod.setDeviceState(controlDevice, state, ip);
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"success");
						}catch (Exception e){
							OpLog opLog = new OpLog("curtain", "setstate","exception", e.getMessage());
							logger.error(opLog.toString());
						}

						break;
				}
				break;

			case "control SoundLightAlarm":
				switch (methodName){
					case "setstate" :
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(shortAddress);
							controlDevice.setEndpoint(endpoint);

							byte state;
                            state = (byte)(0xFF & Integer.parseInt(jsonObject.get("status").getAsString()));

							String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
							if (ip == null) {
								System.out.println("Gateway offline");
							}

							System.out.println(ip);
							gatewayMethod.setSoundLightAlarmState(controlDevice, state, ip);
						}catch (Exception e){
							System.out.println(e);
						}
						break;
				}
				break;

			case "control alarm":
				switch (methodName){
					case "setstate":
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(shortAddress);
							controlDevice.setEndpoint(endpoint);

							byte state;
							state = (byte)(0xFF & Integer.parseInt(jsonObject.get("status").getAsString()));
							int time = jsonObject.get("time").getAsInt();

							String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
							if (ip == null) {
								OpLog opLog = new OpLog("alarm", "setstate","fail", "gateway is offline");
								logger.error(opLog.toString());
								//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
							}

<<<<<<< HEAD

							OpLog opLog = new OpLog("alarm", "setstate","succeed");
							logger.info(opLog.toString());
							System.out.println("gateway ip: " + ip );
=======
							System.out.println(ip);
>>>>>>> devpeng
							gatewayMethod.setAlarmState(controlDevice,state,ip,time);

							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"success");
						}catch (Exception e){
							OpLog opLog = new OpLog("alarm", "setstate","exception", e.getMessage());
							logger.error(opLog.toString());
						}

						break;
				}
				break;

			case "control lock":
				switch (methodName){
					case "setstate":
						Device controlDevice = new Device();
						controlDevice.setShortAddress(shortAddress);
						controlDevice.setEndpoint(endpoint);

						byte state;
						state = (byte)(0xFF & Integer.parseInt(jsonObject.get("status").getAsString()));


						String passwordStr = jsonObject.get("password").getAsString();
						int[] password = new int[passwordStr.length()/2];

						for (int i=0; i<passwordStr.length(); i=i+2) {
							password[i/2] = (passwordStr.charAt(i) - '0')*10+(passwordStr.charAt(i+1) - '0');
						}

						String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
						if (ip == null) {
							OpLog opLog = new OpLog("lock", "setstate","fail", "gateway is offline");
							logger.error(opLog.toString());
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}

						OpLog opLog = new OpLog("lock", "setstate","succeed");
						logger.info(opLog.toString());
						System.out.println("gateway ip: " + ip );
						gatewayMethod.setLock(controlDevice, password, ip, state);
						break;
				}
				break;

            case "control IR":
            	System.out.println("///////////////// 进入红外宝指令下发 /////////////////");
				Device controlDevice = new Device();
<<<<<<< HEAD
				controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
				controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());
				String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
				String version = "";  // 红外宝版本号
				int matchType = 5;    // 初始化为自定义类型

                if (ip == null) {
                    OpLog opLog = new OpLog("infrared", "control","fail", "gateway is offline");
                    logger.error(opLog.toString());
                    break;
                }

                switch (jsonObject.get("methodName").getAsString()){
					case "getVersion":  // 获取版本号
=======
				controlDevice.setShortAddress(shortAddress);
				controlDevice.setEndpoint(endpoint);
                String version = "";
                int type = 5;

                DeviceTokenRelation deviceTokenRelation = null;
                DeviceTokenRelation d = null;
                try {
                    deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(controlDevice.getShortAddress(), (int) controlDevice.getEndpoint());
                    d = deviceTokenRelationService.getParentDeviceTokenRelationBySAAndEndpoint(shortAddress, (int)endpoint);
                }catch (Exception e){
                    JsonObject errMsg = new JsonObject();
                    errMsg.addProperty("errMsg", e.getMessage());
                    gatewayMethod.rpc_callback(d, requestId , errMsg);
                    e.printStackTrace();
                    return;
                }

                String ip = gatewayGroupService.getGatewayIp(shortAddress, (0xFF)&endpoint);
                if (ip == null) {
                    System.out.println("Gateway offline");
                    DataMessageClient.publishResponse( d.getToken() ,requestId,"gateway offline");
                    return;
                }

                switch (methodName){
					case "getVersion":
>>>>>>> devpeng
						gatewayMethod.IR_get_version(controlDevice, ip);
						// mqtt回调
						JsonObject json = new JsonObject();
						json.addProperty("version", version);
						rpcMqttClient.publicResponce(requestId, json.toString());
						break;

					case "match":  // 码组匹配
<<<<<<< HEAD
						try {
							version = jsonObject.get("version").getAsString();
							matchType = jsonObject.get("matchType").getAsInt();

							gatewayMethod.IR_match(controlDevice, ip, version, matchType);
 						} catch (Exception e) {
							OpLog opLog = new OpLog("infrared", "match","exception", e.getMessage());
							logger.error(opLog.toString());
						}
						break;

                    case "learn":  // 码组学习
						version = jsonObject.get("version").getAsString();
						matchType = jsonObject.get("matchType").getAsInt();
                        //Integer learn_key = jsonObject.get("key").getAsInt();
                        String key_name = jsonObject.get("name").getAsString();
						Integer learn_key = null;

						DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(controlDevice.getShortAddress(), (int)controlDevice.getEndpoint());
                        if (null != deviceTokenRelation) {
                            try {
                                if (matchType == 1) {
                                    learn_key = irService.get_maxkey_of_airCondition(deviceTokenRelation.getUuid());
                                    if (null == learn_key) { // 若从未学习过按键, 空调设备从603开始
                                        learn_key = 603;
                                    } else {  // 若已学习过按键，取（learn_key + 1）为当前值
                                        learn_key += 1;
                                    }
                                } else {
                                    learn_key = irService.get_maxkey_of_non_airConditon(deviceTokenRelation.getUuid());
                                    if (null == learn_key) {
                                        learn_key = 44; // 若从未学习过按键, 其他红外设备从44开始
                                    } else {
                                        learn_key += 1;
                                    }
                                }
                                while (null != irService.findKey(deviceTokenRelation.getUuid(), learn_key)) {
                                    learn_key += 1;
                                }
                                gatewayMethod.IR_learn(controlDevice, ip, version, matchType, learn_key);

//                                irService.addKey(deviceTokenRelation.getUuid(), learn_key, key_name, matchType);
                            } catch (Exception e){
                                OpLog log = new OpLog("infrared", "learn", "exception");
                                logger.error(log.toString());
                            }
						}
=======
						version = jsonObject.get("version").getAsString();
						type = jsonObject.get("type").getAsInt();

						gatewayMethod.IR_match(controlDevice, ip, version, type);
						break;

                    case "learn":  // 码组学习
						System.out.println("---------- 学习 ----------");
						version = jsonObject.get("version").getAsString();            // 版本号
						type = jsonObject.get("type").getAsInt();           // 设备类型
                        String key_name = jsonObject.get("keyName").getAsString(); // 按钮名称
                        Integer number = jsonObject.get("number").getAsInt();     // 按钮ID
                        Integer panelId = jsonObject.get("panelId").getAsInt();       // 面板ID
						Integer key = null;

                        if (null != deviceTokenRelation) {
							if (type == 1) {
								key = irService.get_maxkey_of_airCondition(deviceTokenRelation.getUuid());
								if(null == key){ // 若从未学习过按键, 空调设备从603开始
									key = 603;
								} else {  // 若已学习过按键，取（key + 1）为当前值
									key += 1;
								}
							} else {
								key = irService.get_maxkey_of_non_airConditon(deviceTokenRelation.getUuid());
								if (null == key) {
									key = 44; // 若从未学习过按键, 其他红外设备从44开始
								} else {
									key += 1;
								}
//								if (key >= 602) {
//								    key = irService.get_maxkey(deviceTokenRelation.getUuid()) + 1;
//                                }
							}
							// 检验按键编号冲突
							while (null != irService.findAKey(panelId, key)){
								key += 1;
							}
							// 下发学习指令
							gatewayMethod.IR_learn(controlDevice, ip, version, type, key);
							// 添加按键到数据库
                            //irService.addAKey(panelId, number, key, key_name);
							Key k = irService.findAKey(panelId,key);
							if (k != null){
								irService.updateKeyName(k.getId(), key_name);
							} else {
								irService.addAKey(panelId, number, key, key_name);
							}
						} else {
						    System.err.println("device not exists");
                        }
>>>>>>> devpeng
                        break;

					case "penetrate":
						version = jsonObject.get("version").getAsString();
						type = jsonObject.get("type").getAsInt();
						Integer control_key = jsonObject.get("key").getAsInt();

<<<<<<< HEAD
						gatewayMethod.IR_penetrate(controlDevice, ip, version,0, matchType, control_key);
=======
						gatewayMethod.IR_penetrate(controlDevice, ip, version,0, type, control_key);
>>>>>>> devpeng
						break;

					case "currentKey":
						version = jsonObject.get("version").getAsString();
<<<<<<< HEAD
=======

>>>>>>> devpeng
						gatewayMethod.IR_query_current_device_params(controlDevice, ip, version);
						break;

					case "deleteKey":
						version = jsonObject.get("version").getAsString();
						type = jsonObject.get("type").getAsInt();
						Integer deleteKey = jsonObject.get("key").getAsInt();
<<<<<<< HEAD
						gatewayMethod.IR_delete_learnt_key(controlDevice, ip, version, matchType, deleteKey);
=======

						gatewayMethod.IR_delete_learnt_key(controlDevice, ip, version, type, deleteKey);
						irService.deleteKey(controlDevice.getDeviceId(), deleteKey);
>>>>>>> devpeng
						break;

					case "deleteAllKey":
						version = jsonObject.get("version").getAsString();
<<<<<<< HEAD
						if (ip == null) {
                            OpLog opLog = new OpLog("infrared", "delete all keys","fail", "gateway is offline");
                            logger.error(opLog.toString());
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
=======

>>>>>>> devpeng
						gatewayMethod.IR_delete_learnt_all_key(controlDevice, ip, version);
						irService.deleteAllKey(controlDevice.getDeviceId());
						break;

					case "exit":
						gatewayMethod.IR_exit_learn_or_match(controlDevice, ip);
						break;

					case "saveDataInGateway":
						byte saveMethod = 0x04;
						byte[] data = TransportHandler.toBytes(jsonObject.get("data").getAsString());
						String name = jsonObject.get("IRDeviceName").getAsString();

						gatewayMethod.IR_save_data_to_gateway(controlDevice, ip, data,name);
						//TODO 拼接红外数据包
						break;

					case "selectDataInGateway":
						byte selectMethod = 0x05;
						controlDevice.setShortAddress("0000");
						controlDevice.setEndpoint((byte)0x00);

						gatewayMethod.controlIR(controlDevice, ip, null, selectMethod);
						break;

					case "sendData":
						byte sendMethod = 0x06;
						byte[] sendData = TransportHandler.toBytes(jsonObject.get("sendData").getAsString());

						gatewayMethod.controlIR(controlDevice, ip, sendData, sendMethod);
						break;

					case "deleteDataInGateway":
						byte deleteMethod = 0x07;
						byte[] deleteData = TransportHandler.toBytes(jsonObject.get("sendData").getAsString());

						gatewayMethod.controlIR(controlDevice, ip, deleteData, deleteMethod);
						break;

					case "cachePenetrate":
						byte cacheMethod = 0x09;
						//TODO 数据包
						byte[] cacheData = TransportHandler.toBytes(jsonObject.get("sendData").getAsString());
						gatewayMethod.IR_cache_pass_throwgh(controlDevice, ip, cacheData);
						break;

					case "selectCache":
						byte selectCacheMethod = 0x0A;
<<<<<<< HEAD
=======

>>>>>>> devpeng
						gatewayMethod.controlIR(controlDevice, ip, null, selectCacheMethod);
						break;
					case "control":
                        break;
                }
                break;
		}
	}
}

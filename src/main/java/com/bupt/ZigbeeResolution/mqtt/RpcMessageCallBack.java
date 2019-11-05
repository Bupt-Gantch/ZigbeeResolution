package com.bupt.ZigbeeResolution.mqtt;

import com.bupt.ZigbeeResolution.common.Common;
//import com.bupt.ZigbeeResolution.common.RpcResult;
import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
import com.bupt.ZigbeeResolution.data.Key;
import com.bupt.ZigbeeResolution.method.GatewayMethod;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.GatewayGroupService;
import com.bupt.ZigbeeResolution.service.InfraredService;
import com.bupt.ZigbeeResolution.transform.TransportHandler;
import com.bupt.ZigbeeResolution.util.SpringUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.context.request.async.DeferredResult;

public class RpcMessageCallBack implements MqttCallback{
	private String token;
	private GatewayGroupService gatewayGroupService;
	private RpcMqttClient rpcMqttClient;
	private GatewayMethod gatewayMethod = new GatewayMethodImpl();

	Common instance = Common.getInstance();

	InfraredService irService = (InfraredService) SpringUtil.getBean("infraredService");

	DeviceTokenRelationService deviceTokenRelationService = (DeviceTokenRelationService)SpringUtil.getBean("deviceTokenRelationService");

//	@Autowired
//	RpcResult<Integer> rpcResult;

	public RpcMessageCallBack(MqttClient rpcMqtt,String token, GatewayGroupService gatewayGroupService, String gatewayName){
		this.token = token;
		this.rpcMqttClient = new RpcMqttClient(gatewayName,token, gatewayGroupService);
		this.gatewayGroupService = gatewayGroupService;
	}

	public RpcMessageCallBack(){}

	@Override
	public void connectionLost(Throwable arg0) {
		System.out.println("进入mqtt断线回调" + arg0.getCause());
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
		System.out.printf("received rpc message, topic %s , msg " + msg, topic);

		JsonObject jsonObject = new JsonParser().parse(new String(msg.getPayload())).getAsJsonObject();
		String shortAddress = jsonObject.get("shortAddress").getAsString();
		byte endpoint = jsonObject.get("Endpoint").getAsByte();

		// store requestId
		Integer position = topic.lastIndexOf("/");
		Integer requestId = Integer.parseInt(topic.substring(position+1));
		System.out.println(requestId);
		instance.putRequestId(shortAddress+endpoint, requestId);

		String serviceName = jsonObject.get("serviceName").getAsString();
		String methodName = jsonObject.get("methodName").getAsString();

		switch (serviceName){
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
								System.out.println("Gateway offline");
								//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
							}

							System.out.println(ip);
							gatewayMethod.setDeviceState(controlDevice, state, ip);
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"success");
						}catch (Exception e){
							System.out.println(e.getMessage());
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
								System.out.println("Gateway offline");
								//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
							}

							System.out.println(ip);
							gatewayMethod.setDeviceLevel(controlDevice, bright, 0, ip);
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"success");
						}catch (Exception e){
							System.out.println(e);
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
								System.out.println("Gateway offline");
								//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
							}

							System.out.println(ip);
							gatewayMethod.setDeviceState(controlDevice, state, ip);
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"success");
						}catch (Exception e){
							System.out.println(e);
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
								System.out.println("Gateway offline");
								//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
							}

							System.out.println(ip);
							gatewayMethod.setAlarmState(controlDevice,state,ip,time);

							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"success");
						}catch (Exception e){
							System.out.println(e);
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
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}

						System.out.println(ip);
						gatewayMethod.setLock(controlDevice, password, ip, state);
						break;
				}
				break;

            case "control IR": // TODO 测试DefrredResult
            	System.out.println("///////////////// 进入红外宝指令下发 /////////////////");
				Device controlDevice = new Device();
				controlDevice.setShortAddress(shortAddress);
				controlDevice.setEndpoint(endpoint);
                String version = "";
                int type = 5;


                DeviceTokenRelation deviceTokenRelation = null;
//                DeviceTokenRelation parentDeviceTokenRelation = null;
                try {
                    deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(controlDevice.getShortAddress(), (int) controlDevice.getEndpoint());
//					parentDeviceTokenRelation = deviceTokenRelationService.getParentDeviceTokenRelationBySAAndEndpoint(shortAddress, (int)endpoint);

                }catch (Exception e){
                    e.printStackTrace();
                    JsonObject errMsg = new JsonObject();
                    errMsg.addProperty("errMsg", e.getMessage());
                    gatewayMethod.rpc_callback(shortAddress, (int)endpoint, deviceTokenRelationService , requestId , errMsg);
                    return;
                }

                String ip = gatewayGroupService.getGatewayIp(shortAddress, (0xFF)&endpoint);
                if (ip == null) {
                    System.err.println("Gateway offline");
					JsonObject errMsg = new JsonObject();
					errMsg.addProperty("errMsg", "Gateway offline");
					gatewayMethod.rpc_callback(shortAddress, (int)endpoint, deviceTokenRelationService , requestId,errMsg);
                    return;
                }

                switch (methodName){
					case "getVersion":
						gatewayMethod.IR_get_version(controlDevice, ip);
//						DeferredResult<Integer> getVersionRes = rpcResult.createResult(requestId, 2000L, 3);

//						System.out.println("get version response:" + getVersionRes);
						break;

					case "match":  // 码组匹配
						version = jsonObject.get("version").getAsString();
						type = jsonObject.get("type").getAsInt();

						gatewayMethod.IR_match(controlDevice, ip, version, type);
						break;

                    case "learn":  // 码组学习
						System.out.println("---------- 学习 ----------");
						version = jsonObject.get("version").getAsString();            	// 版本号
						type = jsonObject.get("type").getAsInt();           			// 设备类型
                        String key_name = jsonObject.get("keyName").getAsString(); 		// 按钮名称
                        Integer number = jsonObject.get("number").getAsInt();     		// 按钮ID redundance
                        Integer panelId = jsonObject.get("panelId").getAsInt();       	// 面板ID
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
								// TODO 测试相同相同按键编号不同类型能够下发指令成功
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
//							// 新建 DeferredResult 对象, 异步处理进程
//							DeferredResult<Integer> learnRes = rpcResult.createResult(requestId, 2000L, 3);
//
//							// 添加按键到数据库
//							if (!learnRes.hasResult()){
//								return;
//							}



							Key k = irService.findAKey(panelId,key);
							if (k != null){
								irService.updateKeyName(k.getId(), key_name);
							} else {
								irService.addAKey(panelId, number, key, key_name);
							}
						} else {
						    System.err.println("device not exists");
                        }
                        break;

					case "penetrate":
						version = jsonObject.get("version").getAsString();
						type = jsonObject.get("type").getAsInt();
						Integer control_key = jsonObject.get("key").getAsInt();

						gatewayMethod.IR_penetrate(controlDevice, ip, version,0, type, control_key);
						break;

					case "currentKey":
						version = jsonObject.get("version").getAsString();

						gatewayMethod.IR_query_current_device_params(controlDevice, ip, version);
						break;

					case "deleteKey":
						version = jsonObject.get("version").getAsString();
						type = jsonObject.get("type").getAsInt();
						Integer deleteKey = jsonObject.get("key").getAsInt();

						gatewayMethod.IR_delete_learnt_key(controlDevice, ip, version, type, deleteKey);
						irService.deleteKey(controlDevice.getDeviceId(), deleteKey);
						break;

					case "deleteAllKey":
						version = jsonObject.get("version").getAsString();

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
						//TODO 数据包到底是啥？
						byte[] cacheData = TransportHandler.toBytes(jsonObject.get("sendData").getAsString());
						gatewayMethod.IR_cache_pass_throwgh(controlDevice, ip, cacheData);
						break;

					case "selectCache":
						byte selectCacheMethod = 0x0A;

						gatewayMethod.controlIR(controlDevice, ip, null, selectCacheMethod);
						break;
					case "control":
                        break;
                }
                break;
		}
	}
}

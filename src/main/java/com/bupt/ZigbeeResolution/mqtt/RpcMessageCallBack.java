package com.bupt.ZigbeeResolution.mqtt;

import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
import com.bupt.ZigbeeResolution.method.GatewayMethod;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;
import com.bupt.ZigbeeResolution.service.DataService;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.GatewayGroupService;
import com.bupt.ZigbeeResolution.service.InfraredService;
import com.bupt.ZigbeeResolution.transform.TransportHandler;
import com.bupt.ZigbeeResolution.util.SpringUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class RpcMessageCallBack implements MqttCallback{
	private String token;
	private GatewayGroupService gatewayGroupService;
	private RpcMqttClient rpcMqttClient;
	private GatewayMethod gatewayMethod = new GatewayMethodImpl();

	InfraredService irService = (InfraredService) SpringUtil.getBean("infraredService");

	DeviceTokenRelationService deviceTokenRelationService = (DeviceTokenRelationService)SpringUtil.getBean("deviceTokenRelationService");

	public RpcMessageCallBack(MqttClient rpcMqtt,String token, GatewayGroupService gatewayGroupService, String gatewayName){
		this.token = token;
		this.rpcMqttClient = new RpcMqttClient(gatewayName,token, gatewayGroupService);
		this.gatewayGroupService = gatewayGroupService;
	}

	@Override
	public void connectionLost(Throwable arg0) {
		System.out.println("进入mqtt断线回调");
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
		// TODO Auto-generated method stub
		System.out.println(msg);
		JsonObject jsonObject = new JsonParser().parse(new String(msg.getPayload())).getAsJsonObject();
		Integer position = topic.lastIndexOf("/");
		Integer requestId = Integer.parseInt(topic.substring(position+1));
		System.out.println(requestId);


		switch (jsonObject.get("serviceName").getAsString()){
			case "control switch":
				switch (jsonObject.get("methodName").getAsString()){
					case "setstate":
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
							controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());

							byte state;
							if (jsonObject.get("status").getAsString().equals("true")) {
								state = 0x01;
							} else {
								state = 0x00;
							}
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

			case "control dimmableLight":
				switch (jsonObject.get("methodName").getAsString()){
					case "setbright":
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
							controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());

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
				switch (jsonObject.get("methodName").getAsString()){
					case "setstate":
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
							controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());

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
				switch (jsonObject.get("methodName").getAsString()){
					case "setstate" :
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
							controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());

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
				switch (jsonObject.get("methodName").getAsString()){
					case "setstate":
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
							controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());

							byte state;
							state = (byte)(0xFF & Integer.parseInt(jsonObject.get("status").getAsString()));
							int time = jsonObject.get("time").getAsInt();

									//System.out.println("进入控制");

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
				switch (jsonObject.get("methodName").getAsString()){
					case "setstate":
						Device controlDevice = new Device();
						controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
						controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());

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

            case "control IR":
				Device controlDevice = new Device();
				controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
				controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());
				String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
				String version = "";
				int matchType = 5;
				System.out.println("Control IR CallBack : "+jsonObject.toString());
                switch (jsonObject.get("methodName").getAsString()){
					case "getVersion":
						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.IR_get_version(controlDevice, ip);
						break;

					case "match":  // 码组匹配
						version = jsonObject.get("version").getAsString();
						matchType = jsonObject.get("matchType").getAsInt();
						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.IR_match(controlDevice, ip, version, matchType);
						break;

                    case "learn":  // 码组学习
						version = jsonObject.get("version").getAsString();
						matchType = jsonObject.get("matchType").getAsInt();
//                        Integer learn_key = jsonObject.get("key").getAsInt();
                        String key_name = jsonObject.get("name").getAsString();
						Integer learn_key = null;

                        if (ip == null) {
                            System.out.println("Gateway offline");
                            //rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
                        }
//						gatewayMethod.IR_learn(controlDevice, ip, version, matchType, learn_key);
						DeviceTokenRelation deviceTokenRelation = null;
						try {
							deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(controlDevice.getShortAddress(), (int) controlDevice.getEndpoint());
						}catch (Exception e){
							e.printStackTrace();
						}
//						deviceTokenRelation = new DeviceTokenRelation(2386,"6090DD01008D1500",1,"8TbjprjWobxomsd0uiOr","newInfrared","5200095","11E4","2d01f2e0-79fd-11e9-8fc2-67fbc94ac784");
                        if (null != deviceTokenRelation) {
							System.out.println("deviceTokenRelation not null.");
							if (matchType == 1) {
								learn_key = irService.get_maxkey_of_airCondition(deviceTokenRelation.getUuid());
								if(null == learn_key){ // 若从未学习过按键, 空调设备从603开始
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
							while (null != irService.findKey(deviceTokenRelation.getUuid(), learn_key)){
								learn_key += 1;
							}
							System.out.println("IR_LEARN. IP : "+ip+" , matchType : "+matchType+" , learn_key : "+learn_key);
							gatewayMethod.IR_learn(controlDevice, ip, version, matchType, learn_key);
							System.out.println("IR add Key");
                        	irService.addKey(deviceTokenRelation.getUuid(), learn_key, key_name, matchType);
						}
                        break;

					case "penetrate":
						version = jsonObject.get("version").getAsString();
						matchType = jsonObject.get("matchType").getAsInt();
						Integer control_key = jsonObject.get("key").getAsInt();

						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.IR_penetrate(controlDevice, ip, version,0, matchType, control_key);
						break;

					case "currentKey":
						version = jsonObject.get("version").getAsString();
						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.IR_query_current_device_params(controlDevice, ip, version);
						break;

					case "deleteKey":
						version = jsonObject.get("version").getAsString();
						matchType = jsonObject.get("matchType").getAsInt();
						Integer deleteKey = jsonObject.get("key").getAsInt();
						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.IR_delete_learnt_key(controlDevice, ip, version, matchType, deleteKey);
						break;

					case "deleteAllKey":
						version = jsonObject.get("version").getAsString();
						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.IR_delete_learnt_all_key(controlDevice, ip, version);
						break;

					case "exit":
						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.IR_exit_learn_or_match(controlDevice, ip);
						break;

					case "saveDataInGateway":
						byte saveMethod = 0x04;
						byte[] data = TransportHandler.toBytes(jsonObject.get("data").getAsString());
						String name = jsonObject.get("IRDeviceName").getAsString();

						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.IR_save_data_to_gateway(controlDevice, ip, data,name);
						//TODO 拼接红外数据包
						break;

					case "selectDataInGateway":
						byte selectMethod = 0x05;
						controlDevice.setShortAddress("0000");
						controlDevice.setEndpoint((byte)0x00);

						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.controlIR(controlDevice, ip, null, selectMethod);
						break;

					case "sendData":
						byte sendMethod = 0x06;
						byte[] sendData = TransportHandler.toBytes(jsonObject.get("sendData").getAsString());

						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.controlIR(controlDevice, ip, sendData, sendMethod);
						break;

					case "deleteDataInGateway":
						byte deleteMethod = 0x07;
						byte[] deleteData = TransportHandler.toBytes(jsonObject.get("sendData").getAsString());

						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
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
						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.controlIR(controlDevice, ip, null, selectCacheMethod);
						break;
					case "control":
						String key = jsonObject.get("FuncKey").getAsString();
						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}


                }
                break;
		}
	}
}

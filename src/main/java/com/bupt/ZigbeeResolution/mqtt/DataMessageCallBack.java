package com.bupt.ZigbeeResolution.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class DataMessageCallBack implements MqttCallback{

	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		System.err.println("connection lost: " + arg0.getCause());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		System.out.println("delivery message complete");
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

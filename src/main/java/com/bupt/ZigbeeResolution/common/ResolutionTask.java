package com.bupt.ZigbeeResolution.common;

import com.bupt.ZigbeeResolution.service.*;

public class ResolutionTask implements Runnable{
    private String gatewayName;
    private byte[] bytes;
    private DeviceTokenRelationService deviceTokenRelationService;
    private SceneService sceneService;
    private GatewayGroupService gatewayGroupService;
    private SceneRelationService sceneRelationService;
    private DataService dataService;
    
    public ResolutionTask(byte[] bytes, String gatewayName, DeviceTokenRelationService deviceTokenRelationService, SceneService sceneService, GatewayGroupService gatewayGroupService, SceneRelationService sceneRelationService, DataService dataService){
        this.gatewayName = gatewayName;
        this.bytes = bytes;
        this.deviceTokenRelationService = deviceTokenRelationService;
        this.sceneService = sceneService;
        this.gatewayGroupService = gatewayGroupService;
        this.sceneRelationService = sceneRelationService;
        this.dataService = dataService;
    }
    
    
    @Override
    public void run() {
        try {
            dataService.resolution(bytes, gatewayName, deviceTokenRelationService, sceneService, gatewayGroupService, sceneRelationService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

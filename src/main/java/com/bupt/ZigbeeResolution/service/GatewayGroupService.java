package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.GatewayGroup;
import com.bupt.ZigbeeResolution.mapper.GatewayGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GatewayGroupService {

    @Autowired
    private GatewayGroupMapper gatewayGroupMapper;

    public Integer addGatewayGroup (GatewayGroup gatewayGroup){
        return gatewayGroupMapper.addGatewayGroup(gatewayGroup);
    }

    public boolean removeGatewayGroupByIp(String ip){
        try {
            gatewayGroupMapper.removeGatewayGroupByIp(ip);
            return true;
        }catch (Exception e){
            return  false;
        }
    }

    public GatewayGroup getGatewayGroup(String name){
        return gatewayGroupMapper.getGatewayGroup(name);
    }

    public boolean removeGatewayGroupByName(String name){
        try {
            gatewayGroupMapper.removeGatewayGroupByName(name);
            return true;
        }catch (Exception e){
            return  false;
        }
    }

    public String getGatewayNameByIp(String ip){
        return gatewayGroupMapper.getGatewayNameByIp(ip);
    }

    public String getGatewayIp(String shortAddress, Integer endPoint){return  gatewayGroupMapper.getGatewayIp(shortAddress, endPoint); }

}

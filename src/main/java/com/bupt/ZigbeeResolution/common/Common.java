package com.bupt.ZigbeeResolution.common;

import lombok.Synchronized;

import java.util.HashMap;
import java.util.Map;

public class Common {
    private Map<String, Integer> rpcRequest = new HashMap<>();

    private volatile static Common instance = new Common();

    private Common(){};

    public static Common getInstance(){
        if (instance == null){
            instance = new Common();
        }
        return instance;
    }

    public boolean containsRequest(String key){
        return  (rpcRequest.containsKey(key));
    }

    public Integer getRequestId(String key){
        return rpcRequest.get(key);
    }

    @Synchronized
    public void putRequestId(String key, Integer requestId){
        rpcRequest.putIfAbsent(key, requestId);
    }

    @Synchronized
    public void removeRequestId(String key){
        rpcRequest.remove(key);
    }
}

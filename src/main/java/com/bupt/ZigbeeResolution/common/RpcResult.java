//package com.bupt.ZigbeeResolution.common;
//
//import lombok.Data;
//import lombok.Synchronized;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.async.DeferredResult;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Data
//@Component
//public class RpcResult<T>{
//
//    private Map<Integer, DeferredResult<T>> map = new HashMap<>();
//
//    public DeferredResult<T> createResult(Integer key, long timeout, T timeoutMsg) {
//
//        DeferredResult<T> res = new DeferredResult<T>(timeout, timeoutMsg);
//
//        res.onTimeout(() -> {
//            System.out.println(key + " timeout, remove rpc result");
//            removeResult(key);
//        });
//
//        res.onCompletion(() -> {
//            System.out.println(key + "complete, remove rpc result");
//            removeResult(key);
//        });
//
//        putResult(key, res);
//
//        return res;
//    }
//
//    @Synchronized
//    public void setResult(Integer key, T result) {
//        map.get(key).setResult(result);
//    }
//
//    @Synchronized
//    public DeferredResult<T> getResult(Integer key) {
//        return map.get(key);
//    }
//
//    @Synchronized
//    public void putResult(Integer key, DeferredResult<T> result) {
//        map.putIfAbsent(key, result);
//    }
//
//    @Synchronized
//    public void removeResult(Integer key) {
//        map.remove(key);
//    }
//
//}
//
//

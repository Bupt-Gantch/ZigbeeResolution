package com.bupt.ZigbeeResolution.Gateway;

import com.bupt.ZigbeeResolution.Gateway.data.Command;
import com.bupt.ZigbeeResolution.Gateway.data.Responce;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

public class GatewayServiceImpl implements GatewayService{
    ExecutorService service = Executors.newCachedThreadPool();
    NettyWrapper nettyWrapper = new NettyWrapper();
    AtomicLong requestId = new AtomicLong(0);
    @Override
    public Responce sendMsg(Command cmd) throws Exception{
        final long Id = requestId.getAndIncrement();
        cmd.setRequestId(Id);
        Future<Responce> future = service.submit(new Callable<Responce>(){
            public Responce call(){
                nettyWrapper.send(cmd);
                //TransportHandler.response = cmd.getResponseType();
                return nettyWrapper.getResponce(Id);
            }
        });
        return future.get();
    }
}

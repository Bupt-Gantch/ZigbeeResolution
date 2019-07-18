package com.bupt.ZigbeeResolution.Gateway;

import com.bupt.ZigbeeResolution.Gateway.data.Command;
import com.bupt.ZigbeeResolution.Gateway.data.Responce;
import com.bupt.ZigbeeResolution.transform.SocketServer;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class NettyWrapper {
    Set<Command> sendSet = new TreeSet<>();
    Map<Long,Responce> resultMap = new ConcurrentHashMap<>();

    public void send(Command command){
        //todo 调用netty contxt发送该command
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(command.getCmd());
        sendSet.add(command);
    }

    public boolean match(Responce responce){
        Command matchendCommand = null;
        for(Command command:sendSet){
            if(responce.requestId == command.requestId) {
                matchendCommand = command;
            }
        }
        if(matchendCommand != null){
            resultMap.put(matchendCommand.getRequestId(), responce);
            return true;
        }
        return false;
    }

    public Responce getResponce(long requestId){
        while(resultMap.get(requestId) != null){}
        Responce responce = resultMap.get(requestId);
        resultMap.remove(requestId);
        return responce;
    }
}

package com.bupt.ZigbeeResolution.Gateway.data;

import lombok.Data;

@Data
public class Command {
   public long requestId;
   byte[] cmd;
   byte responseType;

   public Command(byte[] cmd, byte responseType){
       this.cmd=cmd;
       this.responseType=responseType;
   }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
}

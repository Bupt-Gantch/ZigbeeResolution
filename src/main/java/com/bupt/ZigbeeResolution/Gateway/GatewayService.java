package com.bupt.ZigbeeResolution.Gateway;

import com.bupt.ZigbeeResolution.Gateway.data.Command;
import com.bupt.ZigbeeResolution.Gateway.data.Responce;

public interface GatewayService {
    Responce sendMsg(Command cmd) throws Exception;
}

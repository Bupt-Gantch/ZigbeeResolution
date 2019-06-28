package com.bupt.ZigbeeResolution.service;

import java.util.List;

import com.bupt.ZigbeeResolution.data.LoggingEvent;
/**
 * @author yjt
 * @date 2019/06/27
 */
public interface ILoggingEventService {

    public boolean insertLog(LoggingEvent loggingEvent);

}


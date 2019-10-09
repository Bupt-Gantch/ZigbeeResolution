package com.bupt.ZigbeeResolution.service;


import com.bupt.ZigbeeResolution.data.LoggingEvent;
/**
 * @author yjt
 * @date 2019/06/27
 */
public interface ILoggingEventService {

    boolean insertLog(LoggingEvent loggingEvent);

}


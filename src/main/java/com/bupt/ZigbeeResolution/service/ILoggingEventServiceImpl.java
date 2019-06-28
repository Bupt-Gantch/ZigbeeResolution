package com.bupt.ZigbeeResolution.service;



import com.bupt.ZigbeeResolution.data.LoggingEvent;
import com.bupt.ZigbeeResolution.mapper.LogEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yjt
 * @date 2019/06/27
 */

@Service
public class ILoggingEventServiceImpl implements ILoggingEventService{

    @Autowired
    private LogEventMapper logEventMapper;

    @Override
    public boolean insertLog(LoggingEvent loggingEvent) {
        Integer i = logEventMapper.addLogEvent(loggingEvent);
        return i == 1;
    }
}
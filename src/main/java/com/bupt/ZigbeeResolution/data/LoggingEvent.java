package com.bupt.ZigbeeResolution.data;


/**
 * logging_event
 * @author 
 */
import lombok.Data;

@Data
public class LoggingEvent{
    private Long event_id;

    private Long timestmp;

    private String formatted_message;

    public LoggingEvent() {
    }

    public LoggingEvent(Long timestmp, String formatted_message) {
        this.timestmp = timestmp;
        this.formatted_message = formatted_message;
    }

    public LoggingEvent(Long event_id, Long timestmp, String formatted_message) {
        this.event_id = event_id;
        this.timestmp = timestmp;
        this.formatted_message = formatted_message;
    }

    public void setTimestmp(Long timestmp) {
        this.timestmp = timestmp;
    }


    public void setFormatted_message(String formatted_message) {
        this.formatted_message = formatted_message;
    }
}
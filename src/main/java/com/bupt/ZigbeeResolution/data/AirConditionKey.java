package com.bupt.ZigbeeResolution.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AirConditionKey {
    private Integer id;

    private String power;

    private String mode;

    private String windLevel;

    private String windDirection;

    private String tem;
}

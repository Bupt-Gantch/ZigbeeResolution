package com.bupt.ZigbeeResolution.mapper;

import com.bupt.ZigbeeResolution.data.InfraredModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface InfraredMapper {
    @Select("SELECT `key` FROM infrared_model WHERE deviceId = #{deviceId} AND type = #{matchType} AND brand_cn = #{brand_cn} AND name = #{name}")
    Integer select_by_deviceid_type_brandCN_name(String deviceId, Integer matchType, String brand_cn, String name);

    @Insert("INSERT INTO infrared_model(deviceId, `key`, name, type) VALUES(#{deviceId}, #{key}, #{name}, #{matchType})")
    void insert(@Param("deviceId")String deviceId, @Param("key")Integer key, @Param("name")String name, @Param("matchType")Integer matchType);

    @Select("SELECT MAX(`key`) FROM infrared_model WHERE `key` > 602 AND deviceId = #{deviceId} AND type = 1")
    Integer select_maxnum_of_airCondition(String deviceId);

    @Select("SELECT MAX(`key`) FROM infrared_model WHERE deviceId = #{deviceId} AND `key` > 43 AND type != 1")
    Integer select_maxnum_of_non_airCondition(String deviceId);

    @Select("SELECT `key` FROM infrared_model WHERE `key` = #{key} AND deviceId = #{deviceId}")
    Integer select_by_key_deviceId(@Param("deviceId")String deviceId, @Param("key")Integer key);

    @Delete("DELETE FROM infrared_model WHERE `key` = #{key} AND deviceId = #{deviceId}")
    void delete_a_key(String deviceId, Integer key);

    @Delete("DELETE FROM infrared_model WHERE deviceId = #{deviceId}")
    void delete_all_key(String deviceId);

    @Update("update infrared_model set state = 1 where `key` = #{key} AND deviceId = #{deviceId}")
    void updateStateByResult(@Param("deviceId")String deviceId, @Param("key")Integer key);


}

package com.bupt.ZigbeeResolution.mapper;

import com.bupt.ZigbeeResolution.data.CustomerLearn;
import com.bupt.ZigbeeResolution.data.InfraredModel;
import org.apache.ibatis.annotations.*;
import org.junit.runners.Parameterized.Parameters;

import java.util.List;

@Mapper
public interface InfraredMapper {
    @Select("SELECT `key` FROM infrared_model WHERE deviceId = #{deviceId} AND type = #{matchType} AND brand_cn = #{brand_cn} AND name = #{name}")
    Integer select_by_deviceid_type_brandCN_name(String deviceId, Integer matchType, String brand_cn, String name);

    @Insert("INSERT INTO infrared_model(deviceId, `key`, name, type, customerId, buttonId, panelId, status) VALUES(#{deviceId}, #{key}, #{name}, #{matchType}, #{customerId}, #{buttonId}, #{panelId}, #{status})")
    void insert(@Param("deviceId") String deviceId, @Param("key") Integer key, @Param("name") String name, @Param("matchType") Integer matchType,
                @Param("customerId") Integer customerId, @Param("buttonId") Integer buttonId, @Param("panelId") Integer panelId, @Param("status") Integer status);

    @Select("SELECT MAX(`key`) FROM infrared_model WHERE `key` > 602 AND deviceId = #{deviceId} AND type = 1")
    Integer select_maxnum_of_airCondition(String deviceId);

    @Select("SELECT MAX(`key`) FROM infrared_model WHERE deviceId = #{deviceId} AND `key` > 43 AND type != 1")
    Integer select_maxnum_of_non_airCondition(String deviceId);

    @Select("SELECT `key` FROM infrared_model WHERE `key` = #{key} AND deviceId = #{deviceId}")
    Integer select_by_key_deviceId(@Param("deviceId") String deviceId, @Param("key") Integer key);

    @Delete("DELETE FROM infrared_model WHERE `key` = #{key} AND deviceId = #{deviceId}")
    void delete_a_key(String deviceId, Integer key);

    @Delete("DELETE FROM infrared_model WHERE deviceId = #{deviceId}")
    void delete_all_key(String deviceId);

    //把学习成功的学习码状态修改为 1
    @Update("update infrared_model set state = 1 where `key` = #{key} AND deviceId = #{deviceId}")
    void updateStateByResult(@Param("deviceId") String deviceId, @Param("key") Integer key);

    //获取用户所学习的所有功能
    @Select("select name,panelName from infrared_model,panel where deviceId = #{deviceId}" +
            "and customerId = #{customerId} and state=1 and infrared_model.panelId=panel.panelId ")
    List<CustomerLearn> selectCustomerLearn(@Param("deviceId") String deviceId, @Param("customerId") Integer customerId);

    //获取用户遥控面板所学的功能
    @Select("select name from infrared_model where deviceId = #{deviceId} and customerId = #{customerId} " +
            "and panelId = #{panelId} and state=1  ")
    List<String> selectKeyNames(@Param("deviceId") String deviceId, @Param("customerId") Integer customerId, @Param("panelId") Integer panelId);


}

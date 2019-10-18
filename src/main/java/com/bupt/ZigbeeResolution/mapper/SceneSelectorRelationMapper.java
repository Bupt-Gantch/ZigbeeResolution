package com.bupt.ZigbeeResolution.mapper;

import com.bupt.ZigbeeResolution.data.SceneSelectorRelation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SceneSelectorRelationMapper {

    @Insert("INSERT INTO sceneSelectorRelation (sceneSelectorId, bindType, scene_id) VALUES (#{sceneSelectorId}, #{bindType}, #{scene_id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addBindScene(SceneSelectorRelation sceneSelectorRelation);

    @Insert("INSERT INTO sceneSelectorRelation (sceneSelectorId, bindType, deviceId) VALUES (#{sceneSelectorId}, #{bindType}, #{deviceId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addBindDevice(SceneSelectorRelation sceneSelectorRelation);

    @Select("SELECT * FROM sceneSelectorRelation WHERE sceneSelectorId = #{sceneSelectorId}")
    List<SceneSelectorRelation> getBindInfoBySceneSelectorId(@Param("sceneSelectorId") String sceneSelectorId);

    @Select("SELECT * FROM sceneSelectorRelation WHERE sceneSelectorId = #{sceneSelectorId} AND deviceId = #{deviceId}")
    SceneSelectorRelation getBindInfoBySceneSelectorIdAndDeviceId(@Param("sceneSelectorId") String sceneSelectorId, @Param("deviceId")String deviceId);

    @Select("SELECT bindType FROM sceneSelectorRelation WHERE sceneSelectorId = #{sceneSelectorId} GROUP BY bindType")
    Integer getBindTypeBySceneSelectorId(@Param("sceneSelectorId") String sceneSelectorId);

    @Select("SELECT * FROM sceneSelectorRelation WHERE deviceId = #{deviceId}")
    List<SceneSelectorRelation> getBindInfoByDeviceId(@Param("deviceId")String deviceId);

    @Delete("DELETE FROM sceneSelectorRelation WHERE sceneSelectorId = #{sceneSelectorId} AND deviceId = #{deviceId}")
    Integer deleteBindInfo(@Param("sceneSelectorId") String sceneSelectorId, @Param("deviceId") String deviceId);

    @Delete("DELETE FROM sceneSelectorRelation WHERE sceneSelectorId = #{sceneSelectorId}")
    Integer deleteBindInfoBySceneSelector(@Param("sceneSelectorId") String sceneSelectorId);

    @Delete("DELETE FROM sceneSelectorRelation WHERE deviceId = #{deviceId}")
    Integer deleteBindInfoByDeviceId(@Param("deviceId") String deviceId);
}

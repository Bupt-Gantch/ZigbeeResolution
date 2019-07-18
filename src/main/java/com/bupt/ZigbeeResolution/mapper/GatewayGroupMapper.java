package com.bupt.ZigbeeResolution.mapper;

import com.bupt.ZigbeeResolution.data.GatewayGroup;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GatewayGroupMapper {
    @Insert("INSERT INTO gatewayGroup(name,ip,channel) VALUES (#{name}, #{ip}, #{channel})")
    int addGatewayGroup (GatewayGroup gatewayGroup);

    @Delete("DELETE FROM gatewayGroup Where ip =#{ip}")
    void removeGatewayGroupByIp(String ip);

    @Delete("DELETE FROM gatewayGroup Where name =#{name}")
    void removeGatewayGroupByName(String name);

    @Select("SELECT * FROM gatewayGroup where name = #{name}")
    GatewayGroup getGatewayGroup(String name);

    @Select("SELECT name FROM gatewayGroup where ip = #{ip}")
    String getGatewayNameByIp(String ip);

    @Select("SELECT ip FROM gatewayGroup, deviceTokenRelation WHERE deviceTokenRelation.shortAddress = #{shortAddress} AND deviceTokenRelation.endPoint = #{endPoint} AND deviceTokenRelation.gatewayName = gatewayGroup.name")
    String getGatewayIp(@Param("shortAddress") String shortAddress, @Param("endPoint") Integer endPoint);

}

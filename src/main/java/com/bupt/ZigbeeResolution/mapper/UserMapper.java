package com.bupt.ZigbeeResolution.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select count(*) from user_new where userName = #{userName}")
    Integer getUser(String userName);

}

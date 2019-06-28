package com.bupt.ZigbeeResolution.mapper;

import com.bupt.ZigbeeResolution.data.IrKey;
import com.bupt.ZigbeeResolution.data.IrRemoteControl;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IrControlMapper {

    /**
     * 查找用户保存的遥控器设置
     * @param infraredId  红外宝uuid
     */
    @Select("select * from ir_remote_control where infrared_id = #{infraredId}")
    List<IrRemoteControl> getAllRemoteControlByCustomerid(@Param("infraredId") String infraredId);

    /**
     * 查找用户保存的遥控器id
     */
    @Select("select id from ir_remote_control where infrared_id = #{infraredId}")
    List<Integer> getAllRemoteControlIdByCustomerid(@Param("infraredId") String infraredId);

    /**
     * 查找遥控器所有按键
     * @param id
     * @return
     */
    @Select("select * from ir_remote_control where id = #{id}")
    IrRemoteControl getAllKeys(@Param("id")Integer id);

    /**
     * 通过id查找按键
     * @param id
     * @return
     */
    @Select("select * from ir_key where id = #{id}")
    IrKey getAKeyById(@Param("id")Integer id);

    /**
     * 查找某一类型按键的信息
     * @param key
     * @param device_type
     * @return
     */
    @Select("select id, key, device_type, name from ir_key where key = #{key} and device_type = #{device_type}")
    List<IrKey> getKey(@Param("key")Integer key, @Param("device_type")Integer device_type);

    /**
     * 查询某一设备类型当前最大按键编号
     * @param device_type
     * @return
     */
    @Select("select max(key) from ir_key where device_type = #{device_type}")
    Integer getMaxKey(@Param("device_type")Integer device_type);

    /**
     * 添加遥控器
     * @param rc
     * @return
     */
    @Insert("insert into ir_remote_control (name, device_type, secondary_type, preset_key, custom_key, infrared_id) " +
            "values (#{customerid}, #{name}, #{device_type}, #{secondary_type}, #{keyJson})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void addRemoteControl(IrRemoteControl rc);

    /**
     * 添加学习的按键(Init）
     * @param irKey
     * @return 空调自定义按键学习从603开始，非空调自定义学习按键从44开始
     */
    @Insert("insert into ir_key(key, device_type, name) values(#{key}, #{device_type}, #{name})")
    @Options(keyProperty="key", keyColumn="key")
    void addInitKey(IrKey irKey);

    /**
     * 添加学习的按键（非空调）
     * @param irKey
     * @return key自动填入当前设备类型的最大值+1，并返回
     */
    @Insert("insert into ir_key(key, device_type, name) values(#{key}, #{device_type}, #{name})")
    @Options(keyProperty="key", keyColumn="key")
    @SelectKey(statement = "select max(key) + 1 from ir_key where device_type != {device_type}", keyProperty = "key", before = true, resultType = int.class)
    void addKey(IrKey irKey);

    /**
     * 添加学习的按键(空调）
     * @param irKey
     * @return key自动填入空调类型的最大值+1，并返回
     */
    @Insert("insert into ir_key(key, device_type, name) values(#{key}, #{device_type}, #{name})")
    @Options(keyProperty="key", keyColumn="key")
    @SelectKey(statement = "select max(key) + 1 from ir_key where device_type = #{device_type}", keyProperty = "key", before = true, resultType = int.class)
    void addAirConditionKey(IrKey irKey);

    /**
     * 删除遥控器
     * @param id
     */
    @Delete("delete from ir_remote_control where id = #{id}")
    void removeIrRemoteControlById(@Param("id")Integer id);

    /**
     * 删除用户所有的遥控器
     * @param customerid
     */
    @Delete("delete from ir_remote_control where customerid = #{customerid}")
    void removeIrRemoteControlByCustomerid(@Param("customerid")Integer customerid);

    /**
     * 删除按键
     * @param id
     */
    @Delete("delete from ir_key where id = #{id}")
    void removeKey(@Param("id")Integer id);

    /**
     * 重命名遥控器
     * @param id
     * @param name
     */
    @Update("update ir_remote_control set name = #{name} where id = #{id}")
    void renameRemoteControl(@Param("id")Integer id, @Param("name")String name);

    /**
     * 重命名按键
     * @param id
     * @param name
     */
    @Update("update ir_key set name = #{name} where id = #{id}")
    void renameKey(@Param("id")Integer id, @Param("name")String name);

    /**
     * 更新遥控器下按键
     * @param id
     * @param custom_keys
     */
    @Update("update ir_remote_control set custom_key = #{custom_keys} where id = #{id}")
    void updateRemoteControlCustomKeys(@Param("id")Integer id, @Param("custom_keys")String custom_keys);
}

package com.bupt.ZigbeeResolution.mapper;

import com.bupt.ZigbeeResolution.data.Learn;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface InfraredMapper {
    @Select("SELECT `key` FROM infrared_model WHERE deviceId = #{deviceId} AND type = #{matchType} AND brand_cn = #{brand_cn} AND name = #{name}")
    Integer select_by_deviceid_type_brandCN_name(String deviceId, Integer matchType, String brand_cn, String name);

    //下发学习指令时插入的数据，添加了customerId、buttonId、panelId、state
    @Insert("INSERT INTO infrared_model(deviceId, `key`, name, type, customerId, buttonId, panelId, status) VALUES(#{deviceId}, #{key}, #{name}, #{matchType}, #{customerId}, #{buttonId}, #{panelId}, #{state})")
    void insert(@Param("deviceId") String deviceId, @Param("key") Integer key, @Param("name") String name, @Param("matchType") Integer matchType,
                @Param("customerId") Integer customerId, @Param("buttonId") Integer buttonId, @Param("panelId") Integer panelId, @Param("state") Integer state);

    //空调设备功能键编号必须大于十进制 602
    @Select("SELECT MAX(`key`) FROM infrared_model WHERE `key` > 602 AND deviceId = #{deviceId} AND type = 1")
    Integer select_maxnum_of_airCondition(String deviceId);

    //非空调设备功能键编号必须大于十进制 43
    @Select("SELECT MAX(`key`) FROM infrared_model WHERE deviceId = #{deviceId} AND `key` > 43 AND type != 1")
    Integer select_maxnum_of_non_airCondition(String deviceId);


    @Select("SELECT `key` FROM infrared_model WHERE `key` = #{key} AND deviceId = #{deviceId}")
    Integer select_by_key_deviceId(@Param("deviceId") String deviceId, @Param("key") Integer key);

    //删除某个学习好的红外设备的数据
    @Delete("DELETE FROM infrared_model WHERE `key` = #{key} AND deviceId = #{deviceId}")
    void delete_a_key(@Param("deviceId") String deviceId, @Param("key") Integer key);

    //删除红外设备的全部学习数据
    @Delete("DELETE FROM infrared_model WHERE deviceId = #{deviceId}")
    void delete_all_key(String deviceId);

    //把学习成功的学习码状态修改为 1
    @Update("update infrared_model set state = 1 where `key` = #{key} AND deviceId = #{deviceId}")
    void updateStateByResult(@Param("deviceId") String deviceId, @Param("key") Integer key);

    //获取该用户所学习的所有功能
    @Select("select `name`,`key`,`buttonId`,`panelId` from infrared_model where deviceId = #{deviceId}" +
            "and customerId = #{customerId} and state=1 ")
    List<Learn> selectCustomerAllLearns(@Param("deviceId") String deviceId, @Param("customerId") Integer customerId);

    //获取该用户某个遥控面板已学习的功能
    @Select("select `name`,`key`,`buttonId`,`panelId` from infrared_model where deviceId = #{deviceId} and customerId = #{customerId} " +
            "and panelId = #{panelId} and state=1  ")
    List<Learn> selectCustomerPanelLearn(@Param("deviceId") String deviceId, @Param("customerId") Integer customerId, @Param("panelId") Integer panelId);

    //获取该红外设备某个遥控面板已学习的功能
    @Select("select `name`,`key`,`buttonId`,`panelId` from infrared_model where deviceId = #{deviceId} and panelId = #{panelId} and state=1  ")
    List<Learn> selectDevicePanelLearn(@Param("deviceId") String deviceId, @Param("panelId") Integer panelId);

    //获取该红外设备所学习的所有功能
    @Select("select `name`,`key`,`buttonId`,`panelId` from infrared_model where deviceId = #{deviceId} and state=1 ")
    List<Learn> selectDeviceAllLearns(@Param("deviceId") String deviceId);

    //查找某个按键的学习情况
    @Select("SELECT `name`,`key`,`buttonId`,`panelId` FROM infrared_model WHERE deviceId = #{deviceId} AND panelId = #{panelId} AND buttonId = #{buttonId}")
    Learn select_a_learn(@Param("deviceId") String deviceId, @Param("panelId") Integer panelId, @Param("buttonId") Integer buttonId);

    //删除某个面板上所学的所有功能
    @Delete("DELETE FROM infrared_model WHERE panelId = #{panelId} AND deviceId = #{deviceId}")
    void delPanel(@Param("deviceId") String deviceId,@Param("panelId") Integer panelId);

    //批量删除面板
    @DeleteProvider(type = Provider.class,method = "batchDelete")
    void delPanels(@Param("deviceId") String deviceId,@Param("panelIds") List<Integer> panelIds);

    class Provider{
        public String batchDelete(Map<String, Object> para){
            List<Integer> panelIds = (List<Integer>)para.get("panelIds");
            String deviceId =(String) para.get("deviceId");
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM infrared_model WHERE panelId IN (");
            for (int i = 0; i < panelIds.size(); i++) {
                sb.append("'").append(panelIds.get(i)).append("'");
                if (i < panelIds.size() - 1)
                    sb.append(",");
            }
            sb.append(") AND deviceId = ").append("'").append(deviceId).append("'");

            return sb.toString();
        }
    }

}

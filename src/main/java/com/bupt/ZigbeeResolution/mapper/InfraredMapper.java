package com.bupt.ZigbeeResolution.mapper;

import com.bupt.ZigbeeResolution.data.AirConditionKey;
import com.bupt.ZigbeeResolution.data.Key;
import com.bupt.ZigbeeResolution.data.Learn;
import com.bupt.ZigbeeResolution.data.Panel;
import javafx.util.Pair;
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
    @Select("SELECT\tMAX( `key` ) FROM\t(\tSELECT T2.key_id  FROM infrared_panel_relation AS T0 INNER JOIN infrared_panel AS T1 ON\tT0.panel_id=T1.id INNER JOIN infrared_panel_key_relation AS T2 ON T1.id = T2.panel_id \n" +
            "\t\tAND T1.type=1 AND T0.device_id=#{deviceId}\n" +
            "\t) AS T3\n" +
            "\tINNER JOIN infrared_key AS T4 ON T3.key_id = T4.id;")
    Integer elect_maxkey_of_airCondition(@Param("deviceId") String deviceId);

    //非空调设备功能键编号必须大于十进制 43
    @Select("SELECT\tMAX( `key` ) FROM\t(\tSELECT T2.key_id  FROM infrared_panel_relation AS T0 INNER JOIN infrared_panel AS T1 ON\tT0.panel_id=T1.id INNER JOIN infrared_panel_key_relation AS T2 ON T1.id = T2.panel_id \n" +
            "\t\tAND T1.type!=1 AND T0.device_id=#{deviceId}\n" +
            "\t) AS T3\n" +
            "\tINNER JOIN infrared_key AS T4 ON T3.key_id = T4.id;")
    Integer select_maxkey_of_non_airCondition(String deviceId);

    //非空调设备功能键编号必须大于十进制 43
    @Select("SELECT\tMAX( `key` ) FROM\t(\tSELECT T2.key_id  FROM infrared_panel_relation AS T0 INNER JOIN infrared_panel AS T1 ON\tT0.panel_id=T1.id INNER JOIN infrared_panel_key_relation AS T2 ON T1.id = T2.panel_id \n" +
            "AND T0.device_id=#{deviceId}\n" +
            "\t) AS T3\n" +
            "\tINNER JOIN infrared_key AS T4 ON T3.key_id = T4.id;")
    Integer select_maxkey(String deviceId);


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

        public String SortedSelect(Map<String, Object> para) {
            Integer sort = (Integer)para.get("sort");
            String sql = "SELECT T2.id, T2.`name`, T2.`timestamp`, T2.type, T2.condition FROM infrared_panel_relation AS T1 INNER JOIN infrared_panel AS T2 ON T1.panel_id=T2.id WHERE T1.device_id = #{deviceId}";
            if (sort == null)
                return sql;
            switch (sort) {
                case 1: // byName
                    sql += "ORDER BY T2.`name` ASC";
                    break;
                case 2: //byType
                    sql += "ORDER BY T2.type ASC";
                    break;
                case 3:
                    sql += "ORDER BY T2.`timestamp` DESC";
                    break;
                default:
                    break;
            }
            return sql;
        }
    }

    @Select("SELECT * FROM infrared_panel WHERE id=#{id}")
    Panel select_panel_by_id(@Param("id")Integer id);

    @SelectProvider(type = Provider.class,method = "SortedSelect")
    List<Panel> select_panels_by_deviceId(@Param("deviceId")String deviceId, @Param("sort")Integer sort);

    @Delete("DELETE FROM infrared_panel_relation WHERE device_id = #{deviceId} AND panel_id = #{panelId}")
    int delete_panel_relation(@Param("deviceId")String deviceId, @Param("panelId")Integer panelId);

    @Delete("DELETE FROM infrared_panel WHERE id = #{panelId}")
    int delete_panel_by_id(@Param("panelId")Integer panelId);

    @Insert("INSERT INTO infrared_panel(`name`,type, `condition`, timestamp) VALUES(#{name}, #{type}, #{condition}, current_timestamp)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert_panel(Panel p);

    @Insert("INSERT INTO infrared_panel_relation(device_id, panel_id) VALUES(#{deviceId}, #{panelId})")
    int insert_panel_relation(@Param("deviceId")String deviceId, @Param("panelId")Integer panelId);

    @Update("UPDATE infrared_panel SET `name` = #{name}, type = #{type} WHERE id = #{id}")
    int update_panel(Panel p);

    @Update("UPDATE infrared_panel SET `condition` = #{condition} WHERE id = #{panelId}")
    int update_panel_condition(@Param("panelId")Integer panelId, @Param("condition")Integer condition);

    @Select("SELECT * FROM infrared_key WHERE id=#{keyId}")
    Key select_key_by_id(@Param("keyId")Integer  keyId);

    @Select("SELECT DISTINCT T6.panel_id, T6.id FROM ( SELECT T2.panel_id FROM infrared_panel_relation AS T1 INNER JOIN infrared_panel_key_relation AS T2 ON T1.panel_id = T2.panel_id AND T1.device_id = #{deviceId} ) AS T5 INNER JOIN ( SELECT T3.panel_id,T4.id FROM infrared_panel_key_relation AS T3 INNER JOIN infrared_key AS T4 ON T3.key_id = T4.id AND T4.`key` = #{key} ) AS T6 ON T5.panel_id = T6.panel_id")
    Pair<Integer, Integer> select_key_by_deviceId_key(@Param("deviceId")String deviceId, @Param("key")Integer key);

    @Select("SELECT T2.id, T2.number, T2.`key`, T2.`name` FROM infrared_panel_key_relation AS T1 INNER JOIN infrared_key AS T2 ON T1.key_id=T2.id WHERE T1.panel_id = #{panelId} AND T2.`key` = #{key}")
    Key select_key_by_panelId_key(@Param("panelId")Integer panelId, @Param("key")Integer key);

    @Select("SELECT T2.id, T2.`name`, T2.number, T2.`key` FROM infrared_panel_key_relation AS T1 INNER JOIN infrared_key AS T2 ON T1.key_id=T2.id WHERE T1.panel_id = #{id}")
    List<Key> select_keys_by_panelId(@Param("id") Integer id);

    @Insert("INSERT INTO infrared_key(`name`, number, `key`) VALUES(#{name},#{number},#{key})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert_key(Key k);

    @Insert("INSERT INTO infrared_panel_key_relation VALUES(#{panelId}, #{keyId})")
    int insert_panel_key_relation(@Param("panelId") Integer panelId, @Param("keyId") Integer keyId);

    @Delete("DELETE FROM infrared_key WHERE id = #{keyId}")
    int delete_key_by_id(@Param("keyId")Integer keyId);

    @Delete("DELETE FROM infrared_panel_key_relation WHERE panel_id=#{panelId} AND key_id=#{keyId}")
    int delete_panel_key_relation(@Param("panelId")Integer panelId, @Param("keyId")Integer keyId);

    @Update("UPDATE infrared_key SET `name`=#{name}, number=#{number} WHERE id=#{id}")
    int update_key(Key k);

    @Update("UPDATE infrared_key SET `name`=#{name} WHERE id=#{keyId}")
    int update_keyName(@Param("keyId")Integer keyId, @Param("name")String name);

    @Select("SELECT id FROM infrared_panel_aircondition WHERE power=#{power} " +
            "AND mode=#{mode} AND windLevel=#{windLevel} " +
            "AND windDirection=#{windDirection} AND tem=#{tem}")
    int select_key_of_AirCondition(@Param("power")String power, @Param("mode")String mode, @Param("windLevel")String windLevel,
                              @Param("windDirection")String windDirection, @Param("tem")String tem);

    @Select("SELECT * FROM infrared_panel_aircondition WHERE id = #{id}")
    AirConditionKey select_airconditionKey_attributes(@Param("id")Integer id);
}

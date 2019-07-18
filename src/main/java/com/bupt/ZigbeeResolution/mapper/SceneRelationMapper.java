package com.bupt.ZigbeeResolution.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SceneRelationMapper {
    @Insert("INSERT INTO sceneRelation (main_scene_id, side_scene_id) VALUES (#{main_scene_id}, #{side_scene_id})")
    Integer addSceneRelation(@Param("main_scene_id")Integer main_scene_id, @Param("side_scene_id")Integer side_scene_id);

    @Select("SELECT side_scene_id FROM sceneRelation Where main_scene_id = #{main_scene_id}")
    List<Integer> getRelatedSceneId(@Param("main_scene_id")Integer main_scene_id);

    @Delete("DELETE FROM sceneRelation WHERE main_scene_id = #{main_scene_id}")
    Integer removeRelation (@Param("main_scene_id")Integer main_scene_id);
}

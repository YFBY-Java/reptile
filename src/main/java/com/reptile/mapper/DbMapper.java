package com.reptile.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 注释：
 * </p>
 * Since: 2024/8/3
 * Author: ChaserFire
 */
@Mapper
public interface DbMapper {

    void createMiyousheTable(@Param("tableName") String tableName);


    @Select("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = #{tableName}")
    int tableExists(@Param("tableName") String tableName);


    @Select("SELECT table_name " +
            "FROM information_schema.tables " +
            "WHERE table_schema = 'reptile' " +
            "AND table_name LIKE CONCAT(#{prefix}, '%')")
    List<String> statisticsTable(@Param("prefix") String prefix);

}

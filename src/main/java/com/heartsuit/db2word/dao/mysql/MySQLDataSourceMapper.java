package com.heartsuit.db2word.dao.mysql;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MySQLDataSourceMapper {
    /**
     *  描述：根据表名称获取表的详细信息
     *
     *@创建人  lv617
     *@创建时间  2018/9/4 16:55
     */
    @Select("SHOW FULL FIELDS FROM ${tableName}")
    List<Map<String, Object>> getAllTableNames(@Param("tableName") String tableName);

    /**
     *  描述：根据数据库名称获取数据库中表的名称和注释
     *
     *@创建人  lv617
     *@创建时间  2018/9/4 16:55
     */
    @Select("select table_name,table_comment from information_schema.tables where table_schema = #{dbName}")
    List<Map<String, Object>> getTableColumnDetail(@Param("dbName") String dbName);
}

package com.heartsuit.db2word.dao.postgresql;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 8:30
 */
@Mapper
@Repository
public interface PostgreSQLDataSourceMapper {
    /**
     * 根据数据库名称获取数据库中表的名称和注释
     *
     * @return
     */
    @Select("select relname as table_name,(select description from pg_description where objoid=oid and objsubid=0) as table_comment from pg_class where relkind ='r' and relname NOT LIKE 'pg%' AND relname NOT LIKE 'sql_%' order by table_name;")
    List<Map<String, Object>> getAllTableNames();

    /**
     * 根据表名称获取表的详细信息
     *
     * @param tableName 数据表名
     * @return
     */
    @Select("select\n" +
            "a.attname as field,\n" +
            "format_type(a.atttypid,a.atttypmod) as type,\n" +
            "(case \n" +
            "when (select count(*) from pg_constraint where conrelid = a.attrelid and conkey[1]=attnum and contype='p')>0 then 'PRI' \n" +
            "when (select count(*) from pg_constraint where conrelid = a.attrelid and conkey[1]=attnum and contype='u')>0 then 'UNI'\n" +
            "when (select count(*) from pg_constraint where conrelid = a.attrelid and conkey[1]=attnum and contype='f')>0 then 'FRI'\n" +
            "else '' end) as key,\n" +
            "(case when a.attnotnull=true then 'NO' else 'YES' end) as null,\n" +
            "col_description(a.attrelid,a.attnum) as comment\n" +
            "from pg_attribute a\n" +
            "where attstattarget=-1 and attrelid = (select oid from pg_class where relname = '${tableName}');")
    List<Map<String, Object>> getTableColumnDetail(@Param("tableName") String tableName);
}

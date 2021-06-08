package com.heartsuit.db2word.mysql.service;

import com.lowagie.text.DocumentException;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public interface DataSourceDetailService {
    /**
     *  描述：根据表名称获取表的详细信息
     *
     *@创建人  lv617
     *@创建时间  2018/9/4 16:52
     */
    List<Map<String, Object>> getDataSourceDetail(String tableName);
    /**
     *  描述：根据数据库名称获取数据库中表的名称和注释
     *
     *@创建人  lv617
     *@创建时间  2018/9/4 16:52
     */
    List<Map<String, Object>> getAllDataSourceName(String dbName);
    /**
     *  描述：将数据写出到指定的word文档中
     *
     *@创建人  lv617
     *@创建时间  2018/9/4 16:52
     */
    void toWord(List<Map<String, Object>> listAll) throws FileNotFoundException, DocumentException;
}

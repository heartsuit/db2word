package com.heartsuit.db2word.service;

import com.lowagie.text.DocumentException;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 8:32
 */
public interface PostgreSQLDataSourceDetailService {
    /**
     * 获取数据库中表的名称和注释
     * @return
     */
    List<Map<String, Object>> getAllTableNames();

    /**
     * 根据表名称获取表的详细信息
     * @param tableName
     * @return
     */
    List<Map<String, Object>> getTableColumnDetail(String tableName);

    /**
     * 将数据写出到指定的word文档中
     * @param tables
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    void toWord(List<Map<String, Object>> tables) throws FileNotFoundException, DocumentException;
}

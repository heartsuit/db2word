package com.heartsuit.db2word.service.impl;

import com.heartsuit.db2word.service.PostgreSQLDataSourceDetailService;
import com.lowagie.text.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 16:22
 */
@SpringBootTest
class PostgreSQLDataSourceDetailServiceImplTest {

    @Autowired
    private PostgreSQLDataSourceDetailService postgreSQLDataSourceDetailService;

    @Test
    public void getAllTableNames() {
        List<Map<String, Object>> allTableNames = postgreSQLDataSourceDetailService.getAllTableNames();
        System.out.println(allTableNames);
        assertTrue(allTableNames.size() > 0);
    }

    @Test
    public void getTableColumnDetail() {
        List<Map<String, Object>> details = postgreSQLDataSourceDetailService.getTableColumnDetail("ok");
        System.out.println(details);
        assertTrue(details.size() > 0);
    }

    @Test
    public void toWord() throws FileNotFoundException, DocumentException {
        List<Map<String, Object>> tableNames = postgreSQLDataSourceDetailService.getAllTableNames();
        postgreSQLDataSourceDetailService.toWord(tableNames);
    }
}
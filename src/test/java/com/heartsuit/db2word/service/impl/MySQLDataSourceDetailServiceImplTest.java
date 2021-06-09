package com.heartsuit.db2word.service.impl;

import com.heartsuit.db2word.service.MySQLDataSourceDetailService;
import com.lowagie.text.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 18:38
 */
@SpringBootTest
class MySQLDataSourceDetailServiceImplTest {
    @Autowired
    private MySQLDataSourceDetailService mySQLDataSourceDetailService;

    @Test
    void getAllDataSourceName() {
        List<Map<String, Object>> tableNames = mySQLDataSourceDetailService.getAllDataSourceName("zaservice");
        System.out.println(tableNames);
        assertTrue(tableNames.size() > 0);
    }

    @Test
    void getDataSourceDetail() {
        List<Map<String, Object>> details = mySQLDataSourceDetailService.getDataSourceDetail("sys_log");
        System.out.println(details);
        assertTrue(details.size() > 0);
    }

    @Test
    void toWord() throws FileNotFoundException, DocumentException {
        mySQLDataSourceDetailService.toWord(mySQLDataSourceDetailService.getAllDataSourceName("zaservice"));
    }
}
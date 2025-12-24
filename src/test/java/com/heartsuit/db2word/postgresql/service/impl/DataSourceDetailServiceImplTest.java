package com.heartsuit.db2word.postgresql.service.impl;

import com.heartsuit.db2word.postgresql.service.Db2WordService;
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
 * Date:  2021/6/8 12:55
 */
@SpringBootTest
class DataSourceDetailServiceImplTest {

    @Autowired
    private Db2WordService db2WordService;

    @Test
    public void getAllTableNames() {
        List<Map<String, Object>> allTableNames = db2WordService.getAllTableNames();
        System.out.println(allTableNames);
        assertTrue(allTableNames.size() > 0);
    }

    @Test
    public void getTableColumnDetail() {
        List<Map<String, Object>> details = db2WordService.getTableColumnDetail("ceshi");
        System.out.println(details);
        assertTrue(details.size() > 0);
    }

    @Test
    public void toWord() throws FileNotFoundException, DocumentException {
        List<Map<String, Object>> tableNames = db2WordService.getAllTableNames();
        db2WordService.toWord(tableNames);
    }
}
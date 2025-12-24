package com.heartsuit.db2word.postgresql.controller;

import com.heartsuit.db2word.postgresql.service.Db2WordService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @Author Heartsuit
 * @Date 2025-12-22
 */
@Controller
public class Db2WordController {
    @Autowired
    private Db2WordService db2WordService;

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadDatabaseDoc() throws DocumentException, FileNotFoundException, UnsupportedEncodingException {
        List<Map<String, Object>> tables = db2WordService.getAllTableNames();
        ByteArrayInputStream bis = db2WordService.toWord(tables);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode("数据库设计文档", "UTF-8") + ".doc");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(bis));
    }

}

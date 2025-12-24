package com.heartsuit.db2word.postgresql.service.impl;

import com.heartsuit.db2word.postgresql.dao.Db2WordMapper;
import com.heartsuit.db2word.postgresql.service.Db2WordService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.rtf.RtfWriter2;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 8:33
 */
@Service
public class Db2WordServiceImpl implements Db2WordService {

    private final Db2WordMapper db2WordMapper;

    public Db2WordServiceImpl(Db2WordMapper dataSourceMapper) {
        this.db2WordMapper = dataSourceMapper;
    }

    @Override
    public List<Map<String, Object>> getAllTableNames() {
        return db2WordMapper.getAllTableNames();
    }

    @Override
    public List<Map<String, Object>> getTableColumnDetail(String tableName) {
        return db2WordMapper.getTableColumnDetail(tableName);
    }

    @Override
    public ByteArrayInputStream toWord(List<Map<String, Object>> tables) throws DocumentException {
        // 创建word文档,并设置纸张的大小
        Document document = new Document(PageSize.A4);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            RtfWriter2.getInstance(document, out);
            document.open();

            // 设置文档标题
            Paragraph p = new Paragraph("数据库表设计文档", new Font(Font.NORMAL, 24, Font.BOLD, new Color(0, 0, 0)));
            p.setAlignment(1);
            document.add(p);

            // 遍历所有表
            for (int i = 0; i < tables.size(); i++) {
                // 获取表名和注释
                String table_name = (String) tables.get(i).get("table_name");
                String table_comment = tables.get(i).get("table_comment") == null ? "" : (String) tables.get(i).get("table_comment");

                // 获取表结构详情
                List<Map<String, Object>> columns = this.getTableColumnDetail(table_name);

                // 构建表标题
                String all = "" + (i + 1) + ". 表名：" + table_name + " " + table_comment + "";

                // 创建6列表格
                Table table = new Table(6);
                document.add(new Paragraph(""));
                table.setBorderWidth(1);
                table.setPadding(0);
                table.setSpacing(0);

                // 添加表头
                Cell cell = new Cell("序号");
                cell.setHeader(true);
                table.addCell(cell);
                table.addCell(new Cell("名称"));
                table.addCell(new Cell("类型"));
                table.addCell(new Cell("允许为空"));
                table.addCell(new Cell("索引"));
                table.addCell(new Cell("说明"));
                table.endHeaders();

                // 填充表格数据
                for (int k = 0; k < columns.size(); k++) {
                    Map<String, Object> column = columns.get(k);
                    table.addCell(String.valueOf(k + 1));
                    table.addCell((String) column.get("field"));
                    table.addCell((String) column.get("type"));
                    table.addCell((String) column.get("null"));
                    table.addCell((String) column.get("key"));
                    table.addCell((String) column.get("comment"));
                }

                // 写入表说明和表格
                document.add(new Paragraph(all));
                document.add(table);
            }
        } finally {
            document.close();
            try {
                out.close();
            } catch (IOException e) {
                // 日志记录异常
                e.printStackTrace();
            }
        }

        // 返回 ByteArrayInputStream 供下载使用
        return new ByteArrayInputStream(out.toByteArray());
    }
}

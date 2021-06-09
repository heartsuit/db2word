package com.heartsuit.db2word.service.impl;

import com.heartsuit.db2word.config.DataSourceSwitcher;
import com.heartsuit.db2word.dao.postgresql.PostgreSQLDataSourceMapper;
import com.heartsuit.db2word.enums.DataSourceEnum;
import com.heartsuit.db2word.service.PostgreSQLDataSourceDetailService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.rtf.RtfWriter2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 8:33
 */
@Service
public class PostgreSQLDataSourceDetailServiceImpl implements PostgreSQLDataSourceDetailService {

    @Value("${doc.path}")
    private String path;

    private final PostgreSQLDataSourceMapper postgreSQLDataSourceMapper;

    public PostgreSQLDataSourceDetailServiceImpl(PostgreSQLDataSourceMapper postgreSQLDataSourceMapper) {
        this.postgreSQLDataSourceMapper = postgreSQLDataSourceMapper;
    }

    @Override
    @DataSourceSwitcher(DataSourceEnum.PG)
    public List<Map<String, Object>> getAllTableNames() {
        return postgreSQLDataSourceMapper.getAllTableNames();
    }

    @Override
    @DataSourceSwitcher(DataSourceEnum.PG)
    public List<Map<String, Object>> getTableColumnDetail(String tableName) {
        return postgreSQLDataSourceMapper.getTableColumnDetail(tableName);
    }

    @Override
    @DataSourceSwitcher(DataSourceEnum.PG)
    public void toWord(List<Map<String, Object>> tables) throws FileNotFoundException, DocumentException {
        // 创建word文档,并设置纸张的大小
        Document document = new Document(PageSize.A4);
        // 创建word文档
        RtfWriter2.getInstance(document, new FileOutputStream(path));
        document.open();// 设置文档标题
        Paragraph p = new Paragraph("数据库表设计文档", new Font(Font.NORMAL, 24, Font.BOLD, new Color(0, 0, 0)));
        p.setAlignment(1);
        document.add(p);

        /* * 创建表格 通过查询出来的表遍历 */
        for (int i = 0; i < tables.size(); i++) {
            // 表名
            String table_name = (String) tables.get(i).get("table_name");
            // 表说明
            String table_comment = tables.get(i).get("table_comment") == null ? "" : (String) tables.get(i).get("table_comment");

            //获取某张表的所有字段说明
            List<Map<String, Object>> columns = this.getTableColumnDetail(table_name);
            //构建表说明
            String all = "" + (i + 1) + ". 表名：" + table_name + " " + table_comment + "";
            //创建有6列的表格
            Table table = new Table(6);
            document.add(new Paragraph(""));
            table.setBorderWidth(1);
            table.setPadding(0);
            table.setSpacing(0);

            /*
             * 添加表头的元素
             */
            Cell cell = new Cell("序号");// 单元格
            cell.setHeader(true);
            table.addCell(cell);

            cell = new Cell("名称");// 单元格
            table.addCell(cell);

            cell = new Cell("类型");// 单元格
            table.addCell(cell);

            cell = new Cell("允许为空");// 单元格
            table.addCell(cell);

            cell = new Cell("索引");// 单元格
            table.addCell(cell);

            cell = new Cell("说明");// 单元格
            table.addCell(cell);

            table.endHeaders();// 表头结束

            // 表格的主体
            for (int k = 0; k < columns.size(); k++) {
                //获取某表每个字段的详细说明
                String Field = (String) columns.get(k).get("field");
                String Type = (String) columns.get(k).get("type");
                String Null = (String) columns.get(k).get("null");
                String Key = (String) columns.get(k).get("key");
                String Comment = (String) columns.get(k).get("comment");
                table.addCell((k + 1) + "");
                table.addCell(Field);
                table.addCell(Type);
                table.addCell(Null);
                table.addCell(Key);
                table.addCell(Comment);
            }
            Paragraph paragraph = new Paragraph(all);
            //写入表说明
            document.add(paragraph);
            //生成表格
            document.add(table);
        }
        document.close();
    }
}

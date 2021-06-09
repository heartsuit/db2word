package com.heartsuit.db2word.service.impl;

import com.heartsuit.db2word.config.DataSourceSwitcher;
import com.heartsuit.db2word.dao.mysql.MySQLDataSourceMapper;
import com.heartsuit.db2word.enums.DataSourceEnum;
import com.heartsuit.db2word.service.MySQLDataSourceDetailService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.rtf.RtfWriter2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * @ClassName：DataSourceDetailImpl
 * @Author：lv617
 * @Date：2018/9/4 14:22
 * @Version：1.0
 **/
@Service
public class MySQLDataSourceDetailServiceImpl implements MySQLDataSourceDetailService {

    @Autowired
    private MySQLDataSourceMapper dataSourceMapper;

    @Override
    @DataSourceSwitcher(DataSourceEnum.MYSQL)
    public List<Map<String, Object>> getDataSourceDetail(String tableName) {
        return dataSourceMapper.getAllTableNames(tableName);
    }

    @Override
    @DataSourceSwitcher(DataSourceEnum.MYSQL)
    public List<Map<String, Object>> getAllDataSourceName(String dbName) {
        return dataSourceMapper.getTableColumnDetail(dbName);
    }

    @Override
    @DataSourceSwitcher(DataSourceEnum.MYSQL)
    public void toWord(List<Map<String, Object>> listAll) throws FileNotFoundException, DocumentException {
        // 创建word文档,并设置纸张的大小
        Document document = new Document(PageSize.A4);
        // 创建word文档
        RtfWriter2.getInstance(document, new FileOutputStream("D:/data/dbDetail.doc"));
        document.open();// 设置文档标题
        Paragraph ph = new Paragraph();
        Font f = new Font();
        Paragraph p = new Paragraph("数据库表设计文档", new Font(Font.NORMAL, 24, Font.BOLDITALIC, new Color(0, 0, 0)));
        p.setAlignment(1);
        document.add(p);
        ph.setFont(f);/* * 创建表格 通过查询出来的表遍历 */
        for (int i = 0; i < listAll.size(); i++) {
            // 表名
            String table_name = (String) listAll.get(i).get("table_name");
            // 表说明
            String table_comment = (String) listAll.get(i).get("table_comment");
            //获取某张表的所有字段说明
            List<Map<String, Object>> list = this.getDataSourceDetail(table_name);
            //构建表说明
            String all = "" + (i + 1) + " 表名：" + table_name + " " + table_comment + "";
//            String all = "" + " 表名：" + table_name + " " + table_comment + "";
            //创建有6列的表格
            Table table = new Table(6);
            document.add(new Paragraph(""));
            table.setBorderWidth(1);
            // table.setBorderColor(Color.BLACK);
            table.setPadding(0);
            table.setSpacing(0);
            /*
             * 添加表头的元素，并设置表头背景的颜色
             */
            Color chade = new Color(176, 196, 222);
            Cell cell = new Cell("序号");// 单元格
//            cell.setBackgroundColor(chade);
            cell.setHeader(true);
            // cell.setColspan(3);//设置表格为三列
            // cell.setRowspan(3);//设置表格为三行
            table.addCell(cell);
            cell = new Cell("字段名");// 单元格
//            cell.setBackgroundColor(chade);
            table.addCell(cell);
            cell = new Cell("类型");// 单元格
//            cell.setBackgroundColor(chade);
            table.addCell(cell);
            cell = new Cell("是否为空");// 单元格
//            cell.setBackgroundColor(chade);
            table.addCell(cell);
            cell = new Cell("主键");// 单元格
//            cell.setBackgroundColor(chade);
            table.addCell(cell);
            cell = new Cell("字段说明");// 单元格
//            cell.setBackgroundColor(chade);
            table.addCell(cell);
            table.endHeaders();// 表头结束
            // 表格的主体，
            for (int k = 0; k < list.size(); k++) {
                //获取某表每个字段的详细说明
                String Field = (String) list.get(k).get("Field");
                String Type = (String) list.get(k).get("Type");
                String Null = (String) list.get(k).get("Null");
                String Key = (String) list.get(k).get("Key");
                String Comment = (String) list.get(k).get("Comment");
                table.addCell((k + 1) + "");
                table.addCell(Field);
                table.addCell(Type);
                table.addCell(Null);
                table.addCell(Key);
                table.addCell(Comment);
            }
            Paragraph pheae = new Paragraph(all);
            //写入表说明
            document.add(pheae);
            //生成表格
            document.add(table);
        }
        document.close();
    }
}

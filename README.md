# 数据库设计文档生成工具

## 共三个分支

* [mysql](https://github.com/heartsuit/db2word/tree/mysql)：将`MySQL`数据表结构导出为`word`（基本上原作者的代码）。
* [postgresql](https://github.com/heartsuit/db2word/tree/postgresql)：将PostgreSQL数据表结构导出为`word`。
* [combined](https://github.com/heartsuit/db2word/tree/master)： 通过多数据源方式，动态切换`MySQL`与`PostgreSQL`。

Note：

* 如果只是使用这个工具生成数据库设计文档，时间紧迫，则可直接使用`mysql`或者`postgresql`分支；

* 如果想了解下多数据源配置，动态切换数据源，则可以切到`combined`分支。

## 效果

- MySQL

![2021-06-09-ExportMySQL.png](https://github.com/heartsuit/heartsuit.github.io/raw/master/pictures/2021-06-09-ExportMySQL.png)

## mysql分支代码

导出mysql数据表结构到word文档，代码源自：https://github.com/BeliveYourSelf/lv617DbTest

## 生成word文档

- 启动Web应用，访问`http://localhost:8080/myTest/getDbDetail?dbName=your-db-name`；

- 生成地址：D:/data/dbDetail.doc（原作者是硬编码在代码中：DataSourceDetailServiceImpl.toWord方法）

## 参考SQL

* 查询所有表名

```sql
SELECT table_name, table_comment FROM information_schema.TABLES WHERE table_schema='zaservice';
```

![2021-06-09-TableNameMySQL.png](https://github.com/heartsuit/heartsuit.github.io/raw/master/pictures/2021-06-09-TableNameMySQL.png)

* 查询每个表的字段信息

```sql
SELECT
        COLUMN_NAME 字段名称,
        COLUMN_TYPE 字段类型,
        COLUMN_DEFAULT 默认值,
        CHARACTER_MAXIMUM_LENGTH AS 最大长度,
        (CASE WHEN is_nullable = 'NO' THEN
                        '否' ELSE
                        '是' END
        ) AS 是否可空,
        (CASE WHEN column_key = 'PRI' THEN
                        '是' ELSE
                        '否' END
        ) AS 是否主键,
        EXTRA 其他,
        COLUMN_COMMENT 字段说明
FROM
        INFORMATION_SCHEMA.COLUMNS
WHERE
 table_schema='zaservice' AND
        table_name = 'sys_log'
```

![2021-06-09-TableColumnMySQL.png](https://github.com/heartsuit/heartsuit.github.io/raw/master/pictures/2021-06-09-TableColumnMySQL.png)

## Reference

[https://github.com/BeliveYourSelf/lv617DbTest](https://github.com/BeliveYourSelf/lv617DbTest)

---

***If you have any questions or any bugs are found, please feel free to contact me.***

***Your comments and suggestions are welcome!***
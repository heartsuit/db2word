# 三个分支

* mysql：将`MySQL`数据表结构导出为`word`（基本上原作者的代码）。
* postgresql：将PostgreSQL数据表结构导出为`word`。
* combined： 通过多数据源方式，动态切换`MySQL`与`PostgreSQL`。

Note：

* 如果只是使用这个工具生成数据库设计文档，时间紧迫，则可直接使用`mysql`或者`postgresql`分支；

* 如果想了解下多数据源配置，动态切换数据源，则可以切到`combined`分支。

## postgresql分支代码

该分支基于原作者的代码，实现了将 `PostgreSQL` 数据表结构导出为word的功能。

* 作为一个小工具来实现，为避免引入复杂性（`MySQL`与`PostgreSQL`多数据源），这里直接单独建了一个项目；

* `.doc`生成地址也硬编码在代码中：`D:/data/dbDetail.doc`（DataSourceDetailServiceImpl.toWord方法）；

## 效果

这里以开源物联网平台 `jetlinks` 项目的数据表为例进行 `PostgreSQL` 数据库设计的导出。

![2021-06-09-ExportPostgreSQL.png](https://github.com/heartsuit/heartsuit.github.io/raw/master/pictures/2021-06-09-ExportPostgreSQL.png)

## 运行

`MySQL` 版作者的实现是写了一个 `Controller` ，提供了一个 `RESTful` 接口来生成文档，这里是在单元测试类中提供了一个方法：

运行 `com.heartsuit.db2word.postgresql.service.impl. DataSourceDetailServiceImplTest` 中的 `toWord()` 测试方法，可直接生成 `.doc` 文件。

## 参考SQL

- 查询所有表名
```sql
select relname as table_name,(select description from pg_description where objoid=oid and objsubid=0) 
as table_comment from pg_class where relkind ='r' and relname NOT LIKE 'pg%' AND relname NOT LIKE 'sql_%'
order by table_name;
```

![2021-06-09-TableName.png](https://github.com/heartsuit/heartsuit.github.io/raw/master/pictures/2021-06-09-TableName.png)

- 查询每个表的字段信息

```sql
select
a.attname as 字段名称,
format_type(a.atttypid,a.atttypmod) as 类型,
(case when atttypmod-4>0 then atttypmod-4 else 0 end) as 长度,
(case 
when (select count(*) from pg_constraint where conrelid = a.attrelid and conkey[1]=attnum and contype='p')>0 then 'PRI' 
when (select count(*) from pg_constraint where conrelid = a.attrelid and conkey[1]=attnum and contype='u')>0 then 'UNI'
when (select count(*) from pg_constraint where conrelid = a.attrelid and conkey[1]=attnum and contype='f')>0 then 'FRI'
else '' end) as 索引,
(case when a.attnotnull=true then 'NO' else 'YES' end) as 允许为空,
col_description(a.attrelid,a.attnum) as 说明
from pg_attribute a
where attstattarget=-1 and attrelid = (select oid from pg_class where relname ='ok');
```

![2021-06-09-TableColumn.png](https://github.com/heartsuit/heartsuit.github.io/raw/master/pictures/2021-06-09-TableColumn.png)

## Reference

- [https://github.com/BeliveYourSelf/lv617DbTest](https://github.com/BeliveYourSelf/lv617DbTest)

- [https://www.cnblogs.com/nami/p/4112339.html](https://www.cnblogs.com/nami/p/4112339.html)

---

***If you have any questions or any bugs are found, please feel free to contact me.***

***Your comments and suggestions are welcome!***

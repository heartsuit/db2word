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

- PostgreSQL   

这里以开源物联网平台 `jetlinks` 项目的数据表为例进行 `PostgreSQL` 数据库设计的导出。
   
![2021-06-09-ExportPostgreSQL.png](https://github.com/heartsuit/heartsuit.github.io/raw/master/pictures/2021-06-09-ExportPostgreSQL.png)

---

***If you have any questions or any bugs are found, please feel free to contact me.***

***Your comments and suggestions are welcome!***

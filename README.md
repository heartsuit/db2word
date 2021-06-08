# 数据库设计文档生成工具

## MySQL

导出mysql数据表结构到word文档，代码源自：https://github.com/BeliveYourSelf/lv617DbTest

## 生成word文档

启动Web应用，访问`http://localhost:8080/myTest/getDbDetail?dbName=your-db-name`；
生成地址：D:/data/dbDetail.doc（原作者是硬编码在代码中：DataSourceDetailServiceImpl.toWord方法）

## Reference

[https://github.com/BeliveYourSelf/lv617DbTest](https://github.com/BeliveYourSelf/lv617DbTest)
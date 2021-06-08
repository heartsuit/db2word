package com.heartsuit.db2word.mysql.controler;

import com.heartsuit.db2word.mysql.service.DataSourceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName：TestControler
 * @Description：生成数据库表设计文档
 * @Author：lv617
 * @Date：2018/9/4 11:29
 * @Version：1.0
 **/
@RestController
@RequestMapping("/myTest")
public class DataSourceDetailControler {

    @Autowired
    private DataSourceDetailService dataSourceDetailService;

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    /**
     *  描述：生成数据库表设计文档
     *
     *@创建人  lv617
     *@创建时间  2018/9/4 16:52
     */
    @RequestMapping("/getDbDetail")
    public String getDbDetail(String dbName){
        try {
            List<Map<String,Object>> list = this.dataSourceDetailService.getAllDataSourceName(dbName);
            this.dataSourceDetailService.toWord(list);
        } catch (Exception e) {
            e.printStackTrace();
            return "生成数据库表设计文档失败";
        }
        return "生成数据库表设计文档成功";
    }
}

package com.heartsuit.db2word.config;

import com.heartsuit.db2word.enums.DataSourceEnum;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 18:00
 */
public class DataSourceContext {
    private static final ThreadLocal<DataSourceEnum> context = new ThreadLocal<>();

    private DataSourceContext() throws Exception {
        throw new InstantiationException("实例化出错~");
    }

    /**
     * 设置数据源标识
     */
    public static void setDataSourceType(DataSourceEnum dataSourceEnum) {
        context.set(dataSourceEnum);
    }

    /**
     * 获取当前数据源的标识
     */
    public static DataSourceEnum getDataSourceType() {
        return context.get();
    }

    /**
     * 清空数据源标识
     */
    public static void clearDataSourceType() {
        context.remove();
    }
}

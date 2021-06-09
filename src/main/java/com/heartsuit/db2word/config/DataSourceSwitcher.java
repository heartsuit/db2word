package com.heartsuit.db2word.config;

import com.heartsuit.db2word.enums.DataSourceEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 18:01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface DataSourceSwitcher {
    DataSourceEnum value() default DataSourceEnum.PG;
}

package com.heartsuit.db2word.config;

import com.heartsuit.db2word.enums.DataSourceEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 18:02
 */
@Component
@Aspect
public class DataSourceSwitcherAspect {
    @Pointcut("execution(* com.heartsuit.db2word.service..*.*(..))")
    private void serviceMethod() {
    }

    /**
     * 在调用前切换数据源
     */
    @Before("serviceMethod()")
    public void transServiceMethod(JoinPoint joinPoint) {
        switchDataSource(joinPoint);
    }

    /**
     * 在调用后（包括有异常的情况下），清空数据源标识
     */
    @After("serviceMethod()")
    public void clearDs(JoinPoint joinPoint) {
        clearDataSource(joinPoint);
    }

    /**
     * 根据注解改变数据源
     */
    private void switchDataSource(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        DataSourceSwitcher dataSourceSwitcher = signature.getMethod().getAnnotation(DataSourceSwitcher.class);
        if (!Objects.isNull(dataSourceSwitcher)) {
            DataSourceEnum currentDataSource = dataSourceSwitcher.value();
            if (DataSourceEnum.PG == currentDataSource) {
                DataSourceContext.setDataSourceType(DataSourceEnum.PG);
            } else if (DataSourceEnum.MYSQL == currentDataSource) {
                DataSourceContext.setDataSourceType(DataSourceEnum.MYSQL);
            }
        }
    }

    /**
     * 在每次调用之后，清空数据源
     */
    private void clearDataSource(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        DataSourceSwitcher dataSourceSwitcher = signature.getMethod().getAnnotation(DataSourceSwitcher.class);
        if (!Objects.isNull(dataSourceSwitcher)) {
            DataSourceContext.clearDataSourceType();
        }
    }
}

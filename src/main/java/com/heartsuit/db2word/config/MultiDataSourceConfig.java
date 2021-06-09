package com.heartsuit.db2word.config;

import com.heartsuit.db2word.enums.DataSourceEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 18:08
 */
@Configuration
public class MultiDataSourceConfig {
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    /**
     * postgresql数据源
     */
    @Bean(name = "pgDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.pg")
    public DataSource pgDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * mysql数据源
     */
    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 多数据源动态切换
     */
    @Bean
    public DataSource multiDataSource(@Qualifier("mysqlDataSource") DataSource mysqlDataSource) {
        Map<Object, Object> target = new HashMap<>();
        target.put(DataSourceEnum.PG, pgDataSource());
        target.put(DataSourceEnum.MYSQL, mysqlDataSource);

        AbstractRoutingDataSource dataSource = new DynamicDataSource();
        dataSource.setDefaultTargetDataSource(mysqlDataSource);
        dataSource.setTargetDataSources(target);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("multiDataSource") DataSource multiDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multiDataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setMapperLocations(resolver.getResources(mapperLocations));
        return sqlSessionFactory.getObject();
    }
}

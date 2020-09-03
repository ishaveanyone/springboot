/**
 * Date: 2020-09-02 18:24
 * Author: xupp
 */

package com.xupp.springbootmybatis.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
@Configuration
public class MybatisConfig {

    @Autowired
    private DataSource dynamicDatasource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        // 给mybatis指定上面配置好的动态数据源
        sqlSessionFactoryBean.setDataSource(dynamicDatasource);
        // 自己配置mybatis的话，这个必须要指定mapper位置，在application.yml里配置的不会生效了
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }
}

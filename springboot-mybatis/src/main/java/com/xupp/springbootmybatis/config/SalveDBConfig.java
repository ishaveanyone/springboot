/**
 * Date: 2020-09-02 13:58
 * Author: xupp
 */

package com.xupp.springbootmybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.xupp.springbootmybatis.mapper.salve")
public class SalveDBConfig {

    @Bean(name = "salve")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slavedDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryOrg() throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(slavedDataSource());
        factoryBean.setConfigLocation(new ClassPathResource("mybatis/salve/mybatis-salve-configuration.xml"));
        return factoryBean.getObject();

    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateOrg() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryOrg()); // 使用上面配置的Factory
        return template;
    }

}

package com.xpp.springbootsharding;

import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.xpp.springbootsharding.mapper.local"}, sqlSessionFactoryRef = "sqlSessionFactoryFintek")
public class MyBatisLocalConfig {

    @Autowired
    @Qualifier(DataBaseConfig.LOCAL_DB)
    private DataSource ds;


    @Bean
    public SqlSessionFactory sqlSessionFactoryFintek() throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(ds);

        factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));

        return factoryBean.getObject();

    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateFintek() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryFintek()); // 使用上面配置的Factory
        return template;
    }
}
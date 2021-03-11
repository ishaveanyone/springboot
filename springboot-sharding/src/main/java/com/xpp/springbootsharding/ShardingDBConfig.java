package com.xpp.springbootsharding;

import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.xpp.springbootsharding.mapper.sharding"},
        sqlSessionFactoryRef = "sqlSessionFactoryShardingDB")
@AutoConfigureAfter(DataBaseConfig.class)
public class ShardingDBConfig {


    @Autowired
    @Qualifier(DataBaseConfig.LOCAL_DB)
    private DataSource ds;
    @Bean(name = "shardingDataSource")
    public DataSource dataSource() throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("local_sharding", ds);
        ShardingRuleConfiguration shardingRuleConfig = getShardingRuleConfiguration();
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
        return dataSource;
    }

    @Bean(name = "sqlSessionFactoryShardingDB")
    public SqlSessionFactory sqlSessionFactoryShardingDB(@Qualifier("shardingDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return factoryBean.getObject();

    }
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManagerShardingDB(@Qualifier("shardingDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateShardingDB(@Qualifier("sqlSessionFactoryShardingDB")SqlSessionFactory factory) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(factory);
        return template;
    }

    private ShardingRuleConfiguration getShardingRuleConfiguration(){
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getThirdpartyRequestTableRule());
        return shardingRuleConfig;
    }

    private static TableRuleConfiguration getThirdpartyRequestTableRule() {
        TableRuleConfiguration result = new TableRuleConfiguration("user", "local_sharding.user_20$->{20..30}0$->{1..9},local_sharding.user_20$->{20..30}$->{10..12}");
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("create_time",new TableShardingAlgorithm.MonthPreciseTableShardingAlgorithm("`test`.user_")));
        return result;
    }


}
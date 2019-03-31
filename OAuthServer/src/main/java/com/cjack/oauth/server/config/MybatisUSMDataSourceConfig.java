package com.cjack.oauth.server.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Properties;

/**
* springboot集成mybatis的基本入口
* 1）创建数据源
* 2）创建SqlSessionFactory
*/
@Configuration    //该注解类似于spring配置文件
@EnableTransactionManagement
@MapperScan(basePackages="com.cjack.oauth.server.dao.mapper",sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class MybatisUSMDataSourceConfig {

    @Value("${environment.database.mysql.driver}")
    private String driverClassName;
    @Value("${environment.database.mysql.usm.url}")
    private String url;
    @Value("${environment.database.mysql.usm.username}")
    private String username;
    @Value("${environment.database.mysql.usm.password}")
    private String password;

    /**
     * 创建数据源
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     */
    @Bean(name="mysqlDataSource")
    @Primary
    public DataSource getDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", driverClassName);
        props.put("url", url);
        props.put("username", username);
        props.put("password", password);
        return DruidDataSourceFactory.createDataSource(props);
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean(name="mysqlSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("mysqlDataSource") DataSource ds) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);//指定数据源(这个必须有，否则报错)
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        fb.setTypeAliasesPackage("com.cjack.oauth.server.dao.domain");//指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));//指定xml文件位置

        return fb.getObject();
    }

}

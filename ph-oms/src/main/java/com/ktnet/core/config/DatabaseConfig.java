package com.ktnet.core.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
 
@Configuration
@MapperScan(basePackages="com.ktnet.fta", sqlSessionFactoryRef="dbSqlSessionFactory")
@EnableTransactionManagement
public class DatabaseConfig { 
	
	@Bean(name="dbDataSource")
	@Primary
	@ConfigurationProperties(prefix="spring.datasource")
	DataSource dbDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="dbSqlSessionFactory")
	@Primary
	SqlSessionFactory sqlSessionFactory(@Qualifier("dbDataSource") DataSource dbDataSource, ApplicationContext applicationContext) throws Exception{
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dbDataSource);
		sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mapper/**/*Mapper.xml"));
		sessionFactory.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		return sessionFactory.getObject();
	}
	
	@Bean(name="dbSqlSessionTemplate")
	@Primary
	SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory dbsqlSessionFactory) throws Exception{
		return new SqlSessionTemplate(dbsqlSessionFactory);
	}

	@Bean(name = "dbtransactionManager")
	@Primary
	PlatformTransactionManager transactionManager(@Qualifier("dbDataSource") DataSource dbDataSource) {
	    return new DataSourceTransactionManager(dbDataSource);
	}
}
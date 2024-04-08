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

//@Configuration
//@MapperScan(basePackages="com.ktnet.fta", sqlSessionFactoryRef="db1SqlSessionFactory")
//@EnableTransactionManagement
public class Db1Config {
	
//	  @Bean(name="db1DataSource")
//	  @Primary
//	  @ConfigurationProperties(prefix="spring.datasource")
//	  DataSource db1DataSource() {
//	  	return DataSourceBuilder.create().build();
//	  }
//	  
//	  @Bean(name="db1SqlSessionFactory")
//	  @Primary
//	  SqlSessionFactory sqlSessionFactory(@Qualifier("db1DataSource") DataSource db1DataSource, ApplicationContext applicationContext) throws Exception{
//	  	final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//	  	sessionFactory.setDataSource(db1DataSource);
//	  	sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mapper/**/*Mapper.xml"));
//	  	sessionFactory.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
//	  	return sessionFactory.getObject();
//	  }
//	  
//	  @Bean(name="db1SqlSessionTemplate")
//	  @Primary
//	  SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory db1sqlSessionFactory) throws Exception{
//	  	return new SqlSessionTemplate(db1sqlSessionFactory);
//	  }
//	  
//	  @Bean(name = "db1transactionManager")
//	  @Primary
//	  PlatformTransactionManager transactionManager(@Qualifier("db1DataSource") DataSource db1DataSource) {
//	      return new DataSourceTransactionManager(db1DataSource);
//	  }
}

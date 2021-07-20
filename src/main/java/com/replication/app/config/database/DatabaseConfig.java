package com.replication.app.config.database;

import java.util.LinkedHashMap;


import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import com.replication.app.config.database.datasource.ReplicationRoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableJpaAuditing
@Configuration
@PropertySources(
		@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true)
		)
@AllArgsConstructor
@Slf4j
public class DatabaseConfig {

	/**
	 * @설명 : MasterDataSource Bean객체를 생성한다. 
	 * @return  dataSourceBuilder
	 */
	@Bean
	@ConfigurationProperties(prefix = "datasource.master")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	/**
	 * @설명 : SlaveDataSource Bean객체를 생성한다. 
	 * @return  dataSourceBuilder
	 */
	@Bean
	@ConfigurationProperties(prefix = "datasource.slave")
	public DataSource slaveDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	/**
	 * @설명 : AbstractRoutingDataSource를 상속받아 작성한 클래스
	 * 		master, slave 각각의 프로퍼티를 속성을 설정한 dataSource bean 객체를 파라미터로 받는다.
	 * 		setTargetDataSources, setDefaultTargetDataSource를 통해 dataSource 집합체를 설정하며,
	 * 		기본 dataSource 객체를 설정한다.
	 * 
	 * @param masterDataSource
	 * @param slaveDataSource
	 * 
	 * @return routingDataSource
	 */
	@Bean
	public DataSource routingDataSource(
			@Qualifier("masterDataSource") DataSource masterDataSource,
			@Qualifier("slaveDataSource") DataSource slaveDataSource) {

		ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();

		Map<Object, Object> dataSourceMap = new LinkedHashMap<>();
		dataSourceMap.put("master", masterDataSource);
		dataSourceMap.put("slave", slaveDataSource);

		log.debug("DataSource Config Set!!");
		
		replicationRoutingDataSource.setTargetDataSources(dataSourceMap);
		replicationRoutingDataSource.setDefaultTargetDataSource(masterDataSource);

		return replicationRoutingDataSource;
	}

	/**
	 * @설명 : DataSource Bean 객체
	 * 		@DependsOn({"routingDataSource"})를 명시함으로써 의존적인 관계임을 명확히 알린다.
	 * 		DataSource를 정하는 로직을 가능하게 하기 위해서는 쿼리를 실행하는 시점에서 DataSource를 
	 * 		각 서비스별로 명시된 트랜잭션 타입을 확인 후 어떤 DataSource를 사용할지 정해야하는데
	 * 		정하기전까지 DataSource 연결 시간을 늦추기 위해 LazyConnectionDataSourceProxy를 사용한다.
	 * 
	 * @param routingDataSource
	 * @return
	 */
	@Primary
	@Bean
	public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
		return new LazyConnectionDataSourceProxy(routingDataSource);
	}


}

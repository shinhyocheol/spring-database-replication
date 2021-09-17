package com.replication.app.config.database;

import java.util.LinkedHashMap;


import java.util.Map;

import javax.persistence.EntityManagerFactory;
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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

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
		log.debug("Master DataSource 객체 생성");
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	/**
	 * @설명 : SlaveDataSource Bean객체를 생성한다. 
	 * @return  dataSourceBuilder
	 */
	@Bean
	@ConfigurationProperties(prefix = "datasource.slave")
	public DataSource slaveDataSource() {
		log.debug("slave DataSource 객체 생성");
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

		log.debug("Datasource Replication 작업 시작");
		ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();

		Map<Object, Object> dataSourceMap = new LinkedHashMap<>();
		dataSourceMap.put("master", masterDataSource);
		dataSourceMap.put("slave", slaveDataSource);
		
		replicationRoutingDataSource.setTargetDataSources(dataSourceMap);
		replicationRoutingDataSource.setDefaultTargetDataSource(masterDataSource);

		log.debug("Datasoruce Replication 작업 종료");
		return replicationRoutingDataSource;
	}

	/**
	 * @설명 : DataSource Bean 객체
	 * 		@DependsOn({"routingDataSource"})를 명시함으로써 의존적인 관계임을 명확히 알린다.
	 * 		DataSource를 정하는 로직을 가능하게 하기 위해서는 쿼리를 실행하는 시점에서 DataSource를 
	 * 		각 서비스별로 명시된 트랜잭션 타입을 확인 후 어떤 DataSource를 사용할지 정해야하는데
	 * 		정하기전까지 DataSource 연결 시간을 늦추기 위해 LazyConnectionDataSourceProxy를 사용한다.
	 *
	 * @LazyConnectionDataSourceProxy : 실제 JDBC 연결을 하고자하는 Datasource에 대한 프록시이다.
	 * 자동 커밋 모드, 트랜잭션 격리 및 읽기 전용모드와 같은 연결 초기화 속성은 실제 연결을 가져오는 즉시 실제 JDBC연결에 유지되고,
	 * 적용된다. 결과적으로 트랜잭션을 시작하는 명령이 없다면 JDBC 연결을 시도하지 않으며, 커밋 및 롤백에 대한 호출이 무시된다.
	 * 이 Datasource 프록시를 사용하면 실제로 필요한 경우가 아니면 풀에서 JDBC 연결을 가져오는것을 방지할 수 있다.
	 * JDBC 트랜잭션 제어는 풀에서 연결을 가져오거나 데이터베이스와 통신하지 않고도 발생할 수 있다. 이 프록시는 JDBC 연결을 시도할 때
	 * 지연 수행한다. 이 프록시는 일반 트랜잭션 경계 환경에서 특히 유용하다. 실제 데이터 엑세스가 발생하지 않는 경우 성능 저하 없이
	 * 데이터 엑세스를 잠재적으로 수행할 수 있는 모든 방법에 대한 트랜잭션을 구분할 수 있다.
	 *
	 * @param routingDataSource
	 * @return
	 */
	@Primary
	@Bean
	public DataSource lazyRoutingDataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
		log.debug("Datasource를 호출하기 전 어떤 Datasource를 호출할 지 결정하기까지 잠시 시간을 지연시키는중입니다...");
		return new LazyConnectionDataSourceProxy(routingDataSource);
	}

	@Bean
	public PlatformTransactionManager transactionManager(@Qualifier(value = "routingDataSource") DataSource routingDataSource) {
		log.debug("트랜잭션 매니저에게 사용할 데이터 소스를 전달시작 합니다.");
		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setDataSource(routingDataSource);

		log.debug("트랜잭션 매니저에게 사용할 데이터 소스를 전달완료 하였습니다.");
		return tm;
	}


}

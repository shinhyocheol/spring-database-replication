package com.replication.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 
 * @설명 : DataSource를 직접 설정하므로 DataSourceAutoConfiguration을 제외시킨다.
 * @author shinhyocheol
 *
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringDatabaseReplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDatabaseReplicationApplication.class, args);
	}

}

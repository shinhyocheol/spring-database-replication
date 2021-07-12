package com.replication.app.config.database.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties("datasource")
public class DatabaseProperties {

	private String url;
	
	private List<Slave> slaveList;
	
	private String username;
	
	private String password;
	
	@Setter
	@Getter
	public static class Slave {
		
		private String name;
		
		private String url;
	}
}

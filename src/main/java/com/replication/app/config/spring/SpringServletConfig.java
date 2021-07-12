package com.replication.app.config.spring;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringServletConfig {

	@Bean 
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}

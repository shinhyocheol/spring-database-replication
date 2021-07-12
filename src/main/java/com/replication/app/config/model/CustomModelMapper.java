package com.replication.app.config.model;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomModelMapper {
	
	private ModelMapper modelMapper;
	
	public CustomModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public <T, V> T toMapping(V from, Class<T> to) {
		return to.cast(modelMapper.map(from, to));
	}
	
}

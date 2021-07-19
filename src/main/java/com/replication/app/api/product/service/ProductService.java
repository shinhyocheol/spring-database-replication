package com.replication.app.api.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.replication.app.api.product.domain.entity.Product;
import com.replication.app.api.product.domain.repository.ProductRepository;
import com.replication.app.api.product.dto.ProductResultDto;
import com.replication.app.config.model.CustomModelMapper;

import lombok.AllArgsConstructor;

@Service("productService")
@AllArgsConstructor
public class ProductService {

	private CustomModelMapper modelMapper;

	private ProductRepository productRepository;
	
	public List<ProductResultDto> getProducts() {

		List<Product> entityList = productRepository.findAll();

		return entityList.stream()
				.map(entity -> modelMapper.toMapping(entity, ProductResultDto.class))
				.collect(Collectors.toList());
	}

	public List<ProductResultDto> getProductsFromMaster() {
		List<Product> entityList = productRepository.findAll();

		return entityList.stream()
				.map(entity -> modelMapper.toMapping(entity, ProductResultDto.class))
				.collect(Collectors.toList());
	}

}

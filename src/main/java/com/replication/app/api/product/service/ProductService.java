package com.replication.app.api.product.service;

import java.util.List;
import java.util.stream.Collectors;

import com.replication.app.api.product.dto.ProductRegistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.replication.app.api.product.domain.entity.Product;
import com.replication.app.api.product.domain.repository.ProductRepository;
import com.replication.app.api.product.dto.ProductResultDto;
import com.replication.app.config.model.CustomModelMapper;

@Service("productService")
@RequiredArgsConstructor
public class ProductService {

	private final CustomModelMapper modelMapper;

	private final ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public List<ProductResultDto> getProducts() {

		List<Product> entityList = productRepository.findAll();

		return entityList.stream()
				.map(entity -> modelMapper.toMapping(entity, ProductResultDto.class))
				.collect(Collectors.toList());
	}

	@Transactional
	public void regProduct(ProductRegistDto registDto) {

		productRepository.save(registDto.toEntity());

	}
	
	@Transactional(readOnly = true)
	public List<ProductResultDto> getProductsFromMaster() {
		List<Product> entityList = productRepository.findAll();

		return entityList.stream()
				.map(entity -> modelMapper.toMapping(entity, ProductResultDto.class))
				.collect(Collectors.toList());
	}

}

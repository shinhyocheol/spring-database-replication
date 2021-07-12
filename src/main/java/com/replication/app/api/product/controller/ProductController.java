package com.replication.app.api.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.replication.app.api.product.dto.ProductResultDto;
import com.replication.app.api.product.service.ProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

	private ProductService productService;
	
	@GetMapping("")
	public ResponseEntity<List<ProductResultDto>> getProducts() {

		return ResponseEntity.ok()
							 .body(productService.getProducts());
	}
	
	@GetMapping("/master")
	public ResponseEntity<List<ProductResultDto>> getProductsFromMaster() {
		
		return ResponseEntity.ok()
							 .body(productService.getProductsFromMaster());
	}
}

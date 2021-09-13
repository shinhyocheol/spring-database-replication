package com.replication.app.api.product.controller;

import java.util.List;

import com.replication.app.api.product.dto.ProductRegistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.replication.app.api.product.dto.ProductResultDto;
import com.replication.app.api.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	@GetMapping("")
	public ResponseEntity<List<ProductResultDto>> getProducts() {

		return ResponseEntity.ok()
							 .body(productService.getProducts());
	}

	@PostMapping("")
	public void regProduct(@RequestBody ProductRegistDto registDto) {

		productService.regProduct(registDto);

	}
	
	@GetMapping("/master")
	public ResponseEntity<List<ProductResultDto>> getProductsFromMaster() {
		
		return ResponseEntity.ok()
							 .body(productService.getProductsFromMaster());
	}
}

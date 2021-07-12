package com.replication.app.api.product.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.replication.app.api.product.domain.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}

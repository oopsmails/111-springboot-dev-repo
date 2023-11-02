package com.oopsmails.exceptionhandling.product.repository;

import com.oopsmails.exceptionhandling.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

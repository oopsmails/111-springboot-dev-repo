package com.oopsmails.exceptionhandling.product.controller;

import com.oopsmails.exceptionhandling.product.entity.Product;
import com.oopsmails.exceptionhandling.product.repository.ProductRepository;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4300")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        //        if (existingProduct != null) {
        //            existingProduct.setName(updatedProduct.getName());
        //            productRepository.save(updatedProduct);
        //            return ResponseEntity.ok(updatedProduct);
        //        } else {
        //            return ResponseEntity.notFound().build();
        //        }

        if (existingProduct != null) {
//            try { // don't use try / catch to reproduce ObjectOptimisticLockingFailureException, @SneakyThrows added
                // Copy properties from updatedProduct to existingProduct
                BeanUtils.copyProperties(existingProduct, updatedProduct);
                productRepository.save(existingProduct);
                return ResponseEntity.ok(existingProduct);
//            } catch (Exception e) {
                // Handle the exception (if any)
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

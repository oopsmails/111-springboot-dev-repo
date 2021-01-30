package com.oopsmails.springangularauth.repositories;

import com.oopsmails.springangularauth.models.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductCrudRepository extends ReactiveCrudRepository<Product, String> {
    Flux<Product> findAllByProdName(String prodName);
    Mono<Product> findFirstByProdName(Mono<String> prodName);
}

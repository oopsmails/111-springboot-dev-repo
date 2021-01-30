package com.oopsmails.springangularauth.repositories;

import io.reactivex.Observable;
import io.reactivex.Single;

import com.oopsmails.springangularauth.models.Product;
import org.springframework.data.repository.reactive.RxJava2CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRxJavaRepository  extends RxJava2CrudRepository<Product, String> {
    Observable<Product> findAllByProdName(String prodName);
    Single<Product> findFirstByProdName(Single<String> prodName);
}

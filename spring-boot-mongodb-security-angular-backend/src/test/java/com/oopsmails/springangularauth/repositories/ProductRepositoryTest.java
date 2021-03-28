package com.oopsmails.springangularauth.repositories;

import com.oopsmails.springangularauth.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

//    @Disabled("Need to run Mongodb")
    @Test
    public void testFindAll() {
        List<Product> products = productRepository.findAll();
        assertTrue(products.size() > 0);
    }
}

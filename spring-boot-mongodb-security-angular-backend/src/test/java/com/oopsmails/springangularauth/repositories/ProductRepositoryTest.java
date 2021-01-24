package com.oopsmails.springangularauth.repositories;

import com.oopsmails.springangularauth.models.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@SpringBootTest()
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindOne() {
        List<Product> products = productRepository.findAll();
        assertTrue("Products existing in db", products.size() > 0);
    }
}

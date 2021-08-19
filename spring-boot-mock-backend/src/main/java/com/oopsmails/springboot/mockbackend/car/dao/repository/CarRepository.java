package com.oopsmails.springboot.mockbackend.car.dao.repository;

import com.oopsmails.springboot.mockbackend.car.dao.entiey.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}

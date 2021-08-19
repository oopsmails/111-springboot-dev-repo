package com.oopsmails.springboot.mockbackend.car.controller;

import com.oopsmails.springboot.mockbackend.car.dao.entiey.Car;
import com.oopsmails.springboot.mockbackend.car.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@RestController
@RequestMapping("/backendmock/car-api")
public class CarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    private static final Function<Throwable, ResponseEntity<? extends List<Car>>> handleGetCarFailure = throwable -> {
        LOGGER.error("Failed to read records: {}", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };
    @Autowired
    private CarService carService;

    @PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public String uploadFile(@RequestPart("user") String user, @RequestPart("files") MultipartFile files) {
//        try {
//            for (final MultipartFile file : files) {
//                carService.saveCars(file.getInputStream());
//            }
//            return HttpStatus.CREATED.toString();
//        } catch (final Exception e) {
//            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
//        }
        return "here";
    }

    @RequestMapping(method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    CompletableFuture<ResponseEntity> getAllCars() {
        return carService.getAllCars().<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(handleGetCarFailure);
    }
}

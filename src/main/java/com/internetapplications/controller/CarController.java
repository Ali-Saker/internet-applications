package com.internetapplications.controller;

import com.internetapplications.ApplicationInitializer;
import com.internetapplications.entity.Car;
import com.internetapplications.entity.Parameter;
import com.internetapplications.repository.CarRepository;
import com.internetapplications.repository.ParameterRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarRepository carRepository;
    private final ParameterRepository parameterRepository;
    private final String defaultSeatsNumberParamName = "Seats Number";
    private final String defaultProfitRateParamName = "Profit Rate";

    public CarController(CarRepository carRepository, ParameterRepository parameterRepository) {
        this.carRepository = carRepository;
        this.parameterRepository = parameterRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Car car) {

        // Check and set the seats number
        if(car.getSeatsNumber() == null) {
            Parameter defaultSeatsNumberParam = this.parameterRepository.findTopByName(defaultSeatsNumberParamName);
            int defaultSeatsNumber = Integer.parseInt(defaultSeatsNumberParam.getValue());
            car.setSeatsNumber(defaultSeatsNumber);
        }

        Parameter profitRateParam = parameterRepository.findTopByName(defaultProfitRateParamName);
        double profitRate = Double.parseDouble(profitRateParam.getValue());

        car.setRetailPrice(car.getPrice() * (1 + (profitRate / 100d)));

        return ResponseEntity.ok(this.carRepository.save(car));
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Car car) {
        Car origin = this.carRepository.findById(car.getId()).get();
        origin.setSeatsNumber(car.getSeatsNumber());
        origin.setName(car.getName());
        origin.setPrice(car.getPrice());
        this.carRepository.save(origin);
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/sell", method = RequestMethod.PUT)
    public ResponseEntity<?> sell(@RequestBody Car car) {
        Car origin = this.carRepository.findById(car.getId()).get();
        origin.setRetailPrice(car.getRetailPrice());
        origin.setBuyerName(car.getBuyerName());
        origin.setSold(true);
        origin.setSellDate(new Date());
        this.carRepository.save(origin);
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(this.carRepository.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        this.carRepository.deleteById(id);
        return ResponseEntity.ok("");
    }
}

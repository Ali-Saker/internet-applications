package com.internetapplications.controller;

import com.internetapplications.entity.Car;
import com.internetapplications.entity.Parameter;
import com.internetapplications.repository.CarRepository;
import com.internetapplications.repository.ParameterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarRepository carRepository;
    private final ParameterRepository parameterRepository;
    private final String defaultSeatsNumberParamName = "Seats Number";

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
        return ResponseEntity.ok(this.carRepository.save(car));
    }

//    @RequestMapping(value = "/", method = RequestMethod.PUT)
//    public ResponseEntity<?> update(@RequestBody Parameter parameter) {
//        Parameter origin = this.parameterRepository.findById(parameter.getId()).get();
//        origin.setValue(parameter.getValue());
//        this.parameterRepository.save(origin);
//        return ResponseEntity.ok("");
//    }

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

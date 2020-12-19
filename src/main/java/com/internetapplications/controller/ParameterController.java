package com.internetapplications.controller;

import com.internetapplications.entity.Parameter;
import com.internetapplications.repository.ParameterRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/params")
public class ParameterController {

    private final ParameterRepository parameterRepository;

    public ParameterController(ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }

    @CacheEvict(value = "internet-applications-cache", key = "'params'")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Parameter parameter) {
        return ResponseEntity.ok(this.parameterRepository.save(parameter));
    }

    @CacheEvict(value = "internet-applications-cache", key = "'params'")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Parameter parameter) {
        Parameter origin = this.parameterRepository.findById(parameter.getId()).get();
        origin.setValue(parameter.getValue());
        this.parameterRepository.save(origin);
        return ResponseEntity.ok("");
    }

//    @Cacheable(value = "internet-applications-cache", key = "'params'")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(this.parameterRepository.findAll());
    }

    @CacheEvict(value = "internet-applications-cache", key = "'params'")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        this.parameterRepository.deleteById(id);
        return ResponseEntity.ok("");
    }

}

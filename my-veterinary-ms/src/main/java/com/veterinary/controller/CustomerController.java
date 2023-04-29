package com.veterinary.controller;

import com.veterinary.dto.CustomerDto;
import com.veterinary.dto.PetDto;
import com.veterinary.manager.CustomerManager;
import com.veterinary.manager.PetManager;
import com.veterinary.repository.CustomerRepository;
import feign.Response;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
@Timed
public class CustomerController {

    private final CustomerManager customerManager;

    @ApiOperation("get all customers")
    @Timed
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/customers")
    ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> pets = customerManager.findAll();
        return ResponseEntity.ok(pets);
    }

    @ApiOperation("get customer by id")
    @Timed
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/customers/{id}")
    ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long id){
        CustomerDto customerDto = customerManager.findById(id);
        return customerDto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(customerDto);
    }

    @ApiOperation("save new customer")
    @Timed
    @PostMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/customers")
    ResponseEntity<CustomerDto> getCustomerById(@RequestBody CustomerDto customerDto){
        CustomerDto returnedCustomerDto = customerManager.save(customerDto);
        return returnedCustomerDto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(returnedCustomerDto);
    }

}

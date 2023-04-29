package com.veterinary.manager;

import com.veterinary.converter.CustomerConverter;
import com.veterinary.converter.CustomerDtoConverter;
import com.veterinary.converter.PetConverter;
import com.veterinary.converter.PetDtoConverter;
import com.veterinary.dto.CustomerDto;
import com.veterinary.dto.PetDto;
import com.veterinary.model.Customer;
import com.veterinary.model.Pet;
import com.veterinary.repository.CustomerRepository;
import com.veterinary.repository.PetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class CustomerManager {

    private final CustomerRepository customerRepository;

    public List<CustomerDto> findAll() {
        return customerRepository.findAll().stream()
                .map(customer -> CustomerConverter.newInstance().convert(customer))
                .collect(Collectors.toList());
    }

    public CustomerDto findById(Long id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return CustomerConverter.newInstance().convert(optionalCustomer.orElse(null));
    }

    public CustomerDto save(CustomerDto customerDto){
        Customer customer = CustomerDtoConverter.newInstance().convert(customerDto);
        Customer addedCustomer = customerRepository.save(customer);
        return CustomerConverter.newInstance().convert(addedCustomer);
    }

}

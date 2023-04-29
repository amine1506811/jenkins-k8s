package com.veterinary.converter;

import com.veterinary.dto.CustomerDto;
import com.veterinary.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;
@Data(staticConstructor = "newInstance")
public class CustomerDtoConverter implements Converter<CustomerDto, Customer> {

    @Override
    public Customer convert(CustomerDto customerDto) {
        if (Objects.isNull(customerDto)) {
            return null;
        }
        return Customer.builder()
                .id(customerDto.getId())
                .firstname(customerDto.getFirstname())
                .lastname(customerDto.getLastname())
                .idNumber(customerDto.getIdNumber())
                .mail(customerDto.getMail())
                .phoneNumber(customerDto.getPhoneNumber())
                .build();
    }
}
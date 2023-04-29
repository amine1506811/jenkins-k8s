package com.veterinary.converter;

import com.veterinary.dto.CustomerDto;
import com.veterinary.model.Customer;
import com.veterinary.model.Treatment;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import com.veterinary.dto.PetDto;
import com.veterinary.model.Pet;
import lombok.Data;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Data(staticConstructor = "newInstance")
public class PetDtoConverter implements Converter<PetDto, Pet> {

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Pet convert(PetDto petDto) {
        if (Objects.isNull(petDto)) {
            return null;
        }
        return Pet.builder()
                .id(petDto.getId())
                .name(petDto.getName())
                .entryDate(petDto.getEntryDate())
                .category(petDto.getCategory())
                .weight(petDto.getWeight())
                .customer(CustomerDtoConverter.newInstance().convert(petDto.getCustomerDto()))
                .treatments(petDto.getTreatments().stream()
                        .map(treatmentDto -> modelMapper.map(treatmentDto, Treatment.class))
                        .collect(Collectors.toList()))
                .build();

    }
}

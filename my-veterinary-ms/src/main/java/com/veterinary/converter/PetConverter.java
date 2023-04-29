package com.veterinary.converter;

import com.veterinary.dto.CustomerDto;
import com.veterinary.dto.PetDto;
import com.veterinary.dto.TreatmentDto;
import com.veterinary.model.Pet;
import com.veterinary.model.Treatment;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Data(staticConstructor = "newInstance")
public class PetConverter implements Converter<Pet, PetDto> {

    private final ModelMapper modelMapper = new ModelMapper();
    @Override
    public PetDto convert(Pet pet) {
        if (Objects.isNull(pet)) {
            return null;
        }
        return PetDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .entryDate(pet.getEntryDate())
                .category(pet.getCategory())
                .weight(pet.getWeight())
                .customerDto(Optional.ofNullable(pet.getCustomer()).map(e->CustomerConverter.newInstance().convert(e)).orElse(CustomerDto.builder().build()))
                .treatments(pet.getTreatments().stream().map(treatment -> modelMapper.map(treatment, TreatmentDto.class)).collect(Collectors.toList()))
                .build();
    }
}

package com.veterinary.manager;

import com.veterinary.converter.PetConverter;
import com.veterinary.converter.PetDtoConverter;
import com.veterinary.dto.PetDto;
import com.veterinary.dto.TreatmentDto;
import com.veterinary.model.Pet;
import com.veterinary.model.Treatment;
import com.veterinary.repository.TreatmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class TreatmentManager {

    private final TreatmentRepository treatmentRepository;
    private final ModelMapper modelMapper;

    public List<TreatmentDto> findAll() {
        return treatmentRepository.findAll().stream()
                .map(treatment -> modelMapper.map(treatment, TreatmentDto.class))
                .collect(Collectors.toList());
    }

    public TreatmentDto save(TreatmentDto treatmentDto){
        Treatment treatment = modelMapper.map(treatmentDto, Treatment.class);
        Treatment treatment1 = treatmentRepository.save(treatment);
        return modelMapper.map(treatment1, TreatmentDto.class);
    }

    public List<TreatmentDto> saveAll(List<TreatmentDto> treatmentDtoList){
        List<Treatment> treatments = new ArrayList<>();
        treatmentDtoList.forEach(treatmentDto -> treatments.add(modelMapper.map(treatmentDto, Treatment.class)));

        List<Treatment> returnedTreatments = treatmentRepository.saveAll(treatments);

        List<TreatmentDto> returnedTreatmentsDto = new ArrayList<>();
        returnedTreatments.forEach(treatment -> returnedTreatmentsDto.add(modelMapper.map(treatment, TreatmentDto.class)));
        return returnedTreatmentsDto;
    }
}


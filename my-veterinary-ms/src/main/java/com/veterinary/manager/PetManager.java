package com.veterinary.manager;

import com.veterinary.converter.PetConverter;
import com.veterinary.converter.PetDtoConverter;
import com.veterinary.dto.PetDto;
import com.veterinary.model.Pet;
import com.veterinary.repository.PetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class PetManager {

    private final PetRepository petRepository;

   public List<PetDto> findAll() {
        return petRepository.findAll().stream()
                .map(pet -> PetConverter.newInstance().convert(pet))
                .collect(Collectors.toList());
    }

    public PetDto findById(Long id){
       Optional<Pet> pet = Optional.of(petRepository.findById(id)).orElse(null);
        return PetConverter.newInstance().convert(pet.isPresent() ? pet.get() : null);
    }

    public PetDto addPet(PetDto petDto){
        Pet pet = PetDtoConverter.newInstance().convert(petDto);
        Pet pet1 = petRepository.save(pet);
        return PetConverter.newInstance().convert(pet1);
    }

    public PetDto updatePetById(Long id, PetDto petDto){
       PetDto existingPet = findById(id);
        existingPet.setName(petDto.getName());
        existingPet.setWeight(petDto.getWeight());
        existingPet.setCategory(petDto.getCategory());
        existingPet.setEntryDate(petDto.getEntryDate());
        existingPet.setCustomerDto(petDto.getCustomerDto());
        existingPet.setTreatments(petDto.getTreatments());
        return addPet(existingPet);
    }

    public void deletePetById(Long id){
        petRepository.deleteById(id);
    }

    //Function that gets names of pets whose weight is superior to value
    public List<String> getNamesOfPetsWhoseWeightIsSuperiorToValue(float value){
      /*  return petRepository.findAll().stream()
                .filter(pet -> pet.getWeight() > value)
                .map(Pet::getName)
                .collect(Collectors.toList());*/
        return petRepository.findByWeightGreaterThan(value)
                .stream()
                .map(pet -> pet.getName())
                .collect(Collectors.toList());
    }

    public List<Map<String, String>>  getPetNameAndCategoryFromIdList(List<Long> idList){
       /*List<Pet> pets = new ArrayList<Pet>();
       idList.forEach(id -> pets.add(petRepository.findById(id).get()));
       return pets.stream()
               .map(pet -> Map.of("name", pet.getName(), "category", pet.getCategory()))
               .collect(Collectors.toList());*/

        List<Pet> pets = petRepository.findAllById(idList);
        return pets.stream()
                .map(pet -> {
                    String name = pet.getName(), category = pet.getCategory();
                    return Map.of("name", name != null ? name : "" , "category", category != null ? category : "");
                }
                )
                .collect(Collectors.toList());
    }

    public String getCategoryById(Long id){
       Optional<Pet> pet = petRepository.findById(id);
       return pet.filter(value -> value.getCategory() != null).map(Pet::getCategory).orElse(null);

    }

}
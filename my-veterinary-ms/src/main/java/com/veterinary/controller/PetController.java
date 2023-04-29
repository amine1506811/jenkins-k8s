package com.veterinary.controller;

import brave.internal.Nullable;
import com.veterinary.dto.PetDto;
import com.veterinary.manager.PetManager;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
@Timed
public class PetController {

    private final PetManager petManager;


    @ApiOperation("get all pets")
    @Timed
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/pets")
    ResponseEntity<List<PetDto>> getAllPets() {
        List<PetDto> pets = petManager.findAll();
        return ResponseEntity.ok(pets);
    }

    @ApiOperation("get pet by id")
    @Timed
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/pets/{id}")
    ResponseEntity<PetDto> getPetbyId(@PathVariable("id") Long id) {
        PetDto pet = petManager.findById(id);
        return pet == null ?    ResponseEntity.notFound().build(): ResponseEntity.ok(pet);
    }

    @ApiOperation("add new pet")
    @Timed
    @PostMapping(path = "/v1/pets")
    ResponseEntity<PetDto> addNewPet(@RequestBody PetDto pet){
        return ResponseEntity.ok(petManager.addPet(pet)
        );
    }

    @ApiOperation("update existing pet")
    @Timed
    @PutMapping(path = "/v1/pets/{id}")
    ResponseEntity<PetDto> updatePet(@PathVariable("id") Long id, @RequestBody PetDto pet){
        return ResponseEntity.ok(petManager.updatePetById(id, pet));
    }

    @ApiOperation("delete existing pet")
    @Timed
    @DeleteMapping(path = "/v1/pets")
    void deletePet(@RequestParam("id") Long id){petManager.deletePetById(id);
    }

    @ApiOperation("gets names of pets whose weight is superior to value")
    @Timed
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/pets/overweight")
    ResponseEntity<List<String>> getNamesOfOverweightPet(@RequestParam("value") float value){
        return ResponseEntity.ok(petManager.getNamesOfPetsWhoseWeightIsSuperiorToValue(value));
    }

    @ApiOperation("get name and category of pets from id list")
    @Timed
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/pets/multiple")
    ResponseEntity<List<Map<String, String>>> getPetsNameAndCategoryWithIdList(@RequestBody List<Long> idList){
        return ResponseEntity.ok(petManager.getPetNameAndCategoryFromIdList(idList));
    }

    @ApiOperation("get category by id")
    @Timed
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/pets/{id}/category")
    ResponseEntity<String> getCategoryById(@PathVariable("id") Long id){
        String category = petManager.getCategoryById(id);
        return category == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(category);
    }



}

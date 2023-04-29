package com.veterinary.controller;

import com.veterinary.dto.PetDto;
import com.veterinary.dto.TreatmentDto;
import com.veterinary.manager.TreatmentManager;
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
public class TreatmentController {

    private final TreatmentManager treatmentManager;

    @ApiOperation("get all treatments")
    @Timed
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/treatments")
    ResponseEntity<List<TreatmentDto>> getAllTreatments() {
        List<TreatmentDto> treatments = treatmentManager.findAll();
        return ResponseEntity.ok(treatments);
    }

    @ApiOperation("save a new treatment")
    @Timed
    @PostMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/treatments")
    ResponseEntity<TreatmentDto> saveTreatment(@RequestBody TreatmentDto treatmentDto){
        return ResponseEntity.ok(treatmentManager.save(treatmentDto));
    }

    @ApiOperation("save a list of new treatments")
    @Timed
    @PostMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/treatments/batch")
    ResponseEntity<List<TreatmentDto>> saveAllTreatments(@RequestBody List<TreatmentDto> treatmentDtoList){
        return ResponseEntity.ok(treatmentManager.saveAll(treatmentDtoList));
    }
}

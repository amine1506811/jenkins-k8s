package com.veterinary.repository;

import com.veterinary.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByWeightGreaterThan(float value);

}

package com.veterinary.manager_tests;

import com.veterinary.manager.PetManager;
import com.veterinary.model.Pet;
import com.veterinary.repository.PetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PetManagerTests {

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetManager petManager;


    @Test
    public void getCategoryById_should_return_category(){
        //GIVEN
        Pet pet = Pet.builder().id(1L).category("dog").build();
        doReturn(Optional.of(pet)).when(petRepository).findById(1L);

        //WHEN
        String result = petManager.getCategoryById(1L);

        //THEN
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo("dog");
    }
}

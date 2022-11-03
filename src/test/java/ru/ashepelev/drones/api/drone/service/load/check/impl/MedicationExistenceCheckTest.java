package ru.ashepelev.drones.api.drone.service.load.check.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ashepelev.drones.dto.medication.MedicationLoadDto;
import ru.ashepelev.drones.entity.medication.Medication;
import ru.ashepelev.drones.exception.medication.NoSuchMedicationException;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({SpringExtension.class})
@Import({MedicationExistenceCheck.class})
class MedicationExistenceCheckTest {
    @Autowired
    private MedicationExistenceCheck check;

    @Test
    void checkOkTest() {
        var medicationLoads = List.of(
                MedicationLoadDto.builder().medicationCode("A").build(),
                MedicationLoadDto.builder().medicationCode("B").build()
        );
        var medicationsByCodes = Map.of(
                "A", Medication.builder().code("A").build(),
                "B", Medication.builder().code("B").build()
        );
        assertDoesNotThrow(()-> check.check(null, medicationLoads, medicationsByCodes));
    }

    @Test
    void checkErrorTest() {
        var medicationLoads = List.of(
                MedicationLoadDto.builder().medicationCode("A").build(),
                MedicationLoadDto.builder().medicationCode("B").build()
        );
        var medicationsByCodes = Map.of(
                "A", Medication.builder().code("A").build()
        );
        var exception = assertThrows(NoSuchMedicationException.class,
                ()-> check.check(null, medicationLoads, medicationsByCodes));
        assertThat(exception.getMessage()).isEqualTo("No such medication with code=B");
    }
}

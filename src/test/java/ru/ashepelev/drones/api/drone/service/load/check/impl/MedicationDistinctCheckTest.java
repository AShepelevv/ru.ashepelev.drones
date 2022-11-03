package ru.ashepelev.drones.api.drone.service.load.check.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ashepelev.drones.dto.medication.MedicationLoadDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({SpringExtension.class})
@Import({MedicationDistinctCheck.class})
class MedicationDistinctCheckTest {
    @Autowired
    private MedicationDistinctCheck check;

    @Test
    void checkOkTest() {
        var medicationLoads = List.of(
                MedicationLoadDto.builder().medicationCode("A").build(),
                MedicationLoadDto.builder().medicationCode("B").build(),
                MedicationLoadDto.builder().medicationCode("С").build()
        );
        assertDoesNotThrow(() -> check.check(null, medicationLoads, null));
    }

    @Test
    void checkErrorTest() {
        var medicationLoads = List.of(
                MedicationLoadDto.builder().medicationCode("A").build(),
                MedicationLoadDto.builder().medicationCode("A").build(),
                MedicationLoadDto.builder().medicationCode("B").build()
        );
        var exception = assertThrows(IllegalArgumentException.class, () -> check.check(null, medicationLoads, null));
        assertThat(exception.getMessage()).isEqualTo("Medication codes must be distinct");
    }
}

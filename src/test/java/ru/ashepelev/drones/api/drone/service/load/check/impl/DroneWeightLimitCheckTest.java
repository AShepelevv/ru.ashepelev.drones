package ru.ashepelev.drones.api.drone.service.load.check.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ashepelev.drones.dto.medication.MedicationLoadDto;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.medication.Medication;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({SpringExtension.class})
@Import({DroneWeightLimitCheck.class})
class DroneWeightLimitCheckTest {
    @Autowired
    private DroneWeightLimitCheck check;

    @Test
    void checkOkTest() {
        var drone = Drone.builder().weightLimit(100.0).build();
        var medicationLoads = List.of(
                MedicationLoadDto.builder().medicationCode("A").count(2).build(),
                MedicationLoadDto.builder().medicationCode("B").count(1).build()
        );
        var medicationsByCodes = Map.of(
                "A", Medication.builder().weight(30.0).build(),
                "B", Medication.builder().weight(10.0).build()
        );
        assertDoesNotThrow(() -> check.check(drone, medicationLoads, medicationsByCodes));
    }

    @Test
    void checkOkWithEqualTest() {
        var drone = Drone.builder().weightLimit(100.0).build();
        var medicationLoads = List.of(
                MedicationLoadDto.builder().medicationCode("A").count(2).build(),
                MedicationLoadDto.builder().medicationCode("B").count(1).build()
        );
        var medicationsByCodes = Map.of(
                "A", Medication.builder().weight(30.00000000001).build(),
                "B", Medication.builder().weight(40.0).build()
        );
        assertDoesNotThrow(() -> check.check(drone, medicationLoads, medicationsByCodes));
    }

    @Test
    void testErrorTest() {
        var drone = Drone.builder().weightLimit(100.0).build();
        var medicationLoads = List.of(
                MedicationLoadDto.builder().medicationCode("A").count(2).build(),
                MedicationLoadDto.builder().medicationCode("B").count(1).build()
        );
        var medicationsByCodes = Map.of(
                "A", Medication.builder().weight(31.0).build(),
                "B", Medication.builder().weight(40.0).build()
        );
        var exception = assertThrows(IllegalArgumentException.class,
                () -> check.check(drone, medicationLoads, medicationsByCodes));
        assertThat(exception.getMessage())
                .isEqualTo("Total weight of medication must be less than drone's weight limit");
    }
}

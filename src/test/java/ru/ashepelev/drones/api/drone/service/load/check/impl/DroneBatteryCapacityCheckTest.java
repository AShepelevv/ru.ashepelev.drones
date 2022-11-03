package ru.ashepelev.drones.api.drone.service.load.check.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ashepelev.drones.entity.drone.Drone;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({SpringExtension.class})
@Import({DroneBatteryCapacityCheck.class})
@TestPropertySource(properties = { "drone.min-battery-for-load=17.0" })
class DroneBatteryCapacityCheckTest {

    @Autowired
    private DroneBatteryCapacityCheck check;

    @Test
    void checkOkTest() {
        var drone = Drone.builder().batteryCapacity(19.0).build();
        assertDoesNotThrow(() -> check.check(drone, List.of(), Map.of()));
    }

    @Test
    void checkErrorTest() {
        var drone = Drone.builder().batteryCapacity(15.0).build();
        var exception = assertThrows(IllegalStateException.class, () -> check.check(drone,null, null));
        assertThat(exception.getMessage()).isEqualTo("Drone must have at least 17.00 battery capacity to be loaded");
    }
}

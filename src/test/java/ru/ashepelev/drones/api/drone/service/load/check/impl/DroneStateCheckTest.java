package ru.ashepelev.drones.api.drone.service.load.check.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ashepelev.drones.entity.drone.Drone;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ashepelev.drones.entity.drone.constants.DroneState.IDLE;
import static ru.ashepelev.drones.entity.drone.constants.DroneState.LOADING;

@ExtendWith({SpringExtension.class})
@Import({DroneStateCheck.class})
class DroneStateCheckTest {
    @Autowired
    private DroneStateCheck check;

    @Test
    void checkOkTest() {
        var drone = Drone.builder().state(IDLE).build();
        assertDoesNotThrow(() -> check.check(drone, List.of(), Map.of()));
    }

    @Test
    void checkErrorTest() {
        var drone = Drone.builder().state(LOADING).build();
        var exception = assertThrows(IllegalStateException.class, () -> check.check(drone, List.of(), Map.of()));
        assertThat(exception.getMessage()).isEqualTo("Drone must be in state IDLE to be loaded");
    }
}

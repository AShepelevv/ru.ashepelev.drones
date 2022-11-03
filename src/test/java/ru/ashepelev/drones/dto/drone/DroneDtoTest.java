package ru.ashepelev.drones.dto.drone;

import org.junit.jupiter.api.Test;
import ru.ashepelev.drones.entity.drone.Drone;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ashepelev.drones.entity.drone.constants.DroneModel.Lightweight;

class DroneDtoTest {
    @Test
    void from() {
        var drone = Drone.builder()
                .serialNumber("SN")
                .weightLimit(77.0)
                .batteryCapacity(37.0)
                .model(Lightweight)
                .build();
        assertThat(DroneDto.from(drone)).isEqualTo(DroneDto.builder()
                .serialNumber("SN")
                .model(Lightweight)
                .batteryCapacity(37.0)
                .weightLimit(77.0)
                .build());
    }
}

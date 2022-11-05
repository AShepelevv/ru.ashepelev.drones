package ru.ashepelev.drones.api.drone.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ashepelev.drones.dto.drone.DroneRegistrationDto;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.metrics.drone.DroneMetric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static ru.ashepelev.drones.entity.drone.constants.DroneModel.Lightweight;
import static ru.ashepelev.drones.entity.drone.constants.DroneState.IDLE;

@ExtendWith({SpringExtension.class})
@Import({DroneRegistrator.class})
class DroneRegistratorTest {
    @Autowired
    private DroneRegistrator droneRegistrator;
    @MockBean
    private DroneSaver droneSaver;

    private final static DroneRegistrationDto dto = DroneRegistrationDto.builder()
            .droneSerialNumber("A")
            .droneModel(Lightweight)
            .weightLimit(100.0)
            .build();

    @Test
    void registerDrone() {
        droneRegistrator.registerDrone(dto);
        verify(droneSaver).save(Drone.builder()
                .serialNumber("A")
                .model(Lightweight)
                .state(IDLE)
                .weightLimit(100.0)
                .batteryCapacity(0.0)
                .build());
        assertThat(droneRegistrator.getClass().getDeclaredMethods()[0].getDeclaredAnnotation(DroneMetric.class))
                .isNotNull();
    }
}

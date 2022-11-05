package ru.ashepelev.drones.api.drone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ashepelev.drones.dto.drone.DroneRegistrationDto;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.metrics.drone.DroneMetric;

import javax.validation.constraints.NotNull;

import static ru.ashepelev.drones.entity.drone.constants.DroneState.IDLE;

@Service
@RequiredArgsConstructor
public class DroneRegistrator {
    private final DroneSaver droneSaver;

    @DroneMetric
    public Boolean registerDrone(@NotNull DroneRegistrationDto dto) {
       droneSaver.save(Drone.builder()
                        .serialNumber(dto.getDroneSerialNumber())
                        .model(dto.getDroneModel())
                        .state(IDLE)
                        .weightLimit(dto.getWeightLimit())
                        .batteryCapacity(0.0)
                .build());
        return true;
    }
}

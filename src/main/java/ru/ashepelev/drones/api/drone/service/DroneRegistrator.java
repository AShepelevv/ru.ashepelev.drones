package ru.ashepelev.drones.api.drone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ashepelev.drones.dto.drone.DroneRegistrationDto;
import ru.ashepelev.drones.entity.drone.Drone;

import javax.persistence.EntityManager;

import static ru.ashepelev.drones.entity.drone.constants.DroneState.IDLE;

@Service
@RequiredArgsConstructor
public class DroneRegistrator {
    private final EntityManager entityManager;

    @Transactional
    public Boolean registerDrone(DroneRegistrationDto dto) {
       entityManager.persist(Drone.builder()
                        .serialNumber(dto.getDroneSerialNumber())
                        .model(dto.getDroneModel())
                        .state(IDLE)
                        .weightLimit(dto.getWeightLimit())
                        .batteryCapacity(0.0)
                .build());
        return true;
    }
}

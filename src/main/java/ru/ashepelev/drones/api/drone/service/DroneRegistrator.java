package ru.ashepelev.drones.api.drone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ashepelev.drones.dto.drone.DroneRegistrationDto;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.drone.DroneRepository;

import static ru.ashepelev.drones.entity.drone.constants.DroneState.IDLE;

@Service
@RequiredArgsConstructor
public class DroneRegistrator {
    private final DroneRepository droneRepository;

    @Transactional
    public Boolean registerDrone(DroneRegistrationDto dto) {
        droneRepository.save(Drone.builder()
                        .serialNumber(dto.getDroneSerialNumber())
                        .model(dto.getDroneModel())
                        .state(IDLE)
                        .weightLimit(dto.getWeightLimit())
                        .batteryCapacity(0.0)
                .build());
        return true;
    }
}

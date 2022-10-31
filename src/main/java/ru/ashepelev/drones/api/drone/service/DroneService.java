package ru.ashepelev.drones.api.drone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.ashepelev.drones.dto.drone.DroneDto;
import ru.ashepelev.drones.dto.medication.LoadedMedicationDto;
import ru.ashepelev.drones.entity.drone.DroneRepository;
import ru.ashepelev.drones.entity.droneMedication.DroneMedicationRepository;
import ru.ashepelev.drones.exception.drone.NoSuchDroneException;

import java.util.List;

import static ru.ashepelev.drones.entity.drone.constants.DroneState.IDLE;
import static ru.ashepelev.drones.utils.CollectionUtils.filterAndMap;
import static ru.ashepelev.drones.utils.CollectionUtils.map;
import static ru.ashepelev.drones.utils.Precision.of;

@Service
@RequiredArgsConstructor
public class DroneService {
    private final DroneRepository droneRepository;
    private final DroneMedicationRepository droneMedicationRepository;

    @Value("${drone.min-battery-for-load:25.0}")
    private double minimumLoadingBatteryLevel;

    public List<DroneDto> getAvailable() {
        return filterAndMap(droneRepository.findAllByState(IDLE),
                d -> of(d.getBatteryCapacity()).isGreaterOrEqualTo(minimumLoadingBatteryLevel),
                DroneDto::from);
    }

    public DroneDto getBySerialNumber(String serialNumber) {
        return droneRepository.findBySerialNumber(serialNumber)
                .map(DroneDto::from)
                .orElseThrow(() -> new NoSuchDroneException(serialNumber));
    }

    public List<LoadedMedicationDto> getLoadedMedication(String serialNumber) {
        if (droneRepository.findBySerialNumber(serialNumber).isEmpty())
            throw new NoSuchDroneException(serialNumber);
        return map(droneMedicationRepository.getDroneMedicationsByDroneSerialNumber(serialNumber),
                LoadedMedicationDto::from);
    }
}

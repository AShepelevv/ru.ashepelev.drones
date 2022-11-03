package ru.ashepelev.drones.api.drone.service.load;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ashepelev.drones.api.drone.service.load.check.DroneLoadingCheck;
import ru.ashepelev.drones.dto.medication.MedicationLoadDto;
import ru.ashepelev.drones.entity.drone.DroneRepository;
import ru.ashepelev.drones.entity.droneMedication.DroneMedication;
import ru.ashepelev.drones.entity.droneMedication.DroneMedicationRepository;
import ru.ashepelev.drones.entity.medication.Medication;
import ru.ashepelev.drones.entity.medication.MedicationRepository;
import ru.ashepelev.drones.exception.drone.NoSuchDroneException;

import java.util.List;

import static ru.ashepelev.drones.entity.drone.constants.DroneState.LOADING;
import static ru.ashepelev.drones.utils.CollectionUtils.mapToSet;
import static ru.ashepelev.drones.utils.CollectionUtils.toKeyMap;

@Service
@RequiredArgsConstructor
public class DroneLoader {
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final DroneMedicationRepository droneMedicationRepository;
    private final List<DroneLoadingCheck> checks;

    @Transactional
    public Boolean load(String droneSerialNumber, List<MedicationLoadDto> medicationLoads) {
        var medicationLoadCodes = mapToSet(medicationLoads, MedicationLoadDto::getMedicationCode);
        var medicationsByCodes = toKeyMap(
                medicationRepository.findAllByCodeIn(medicationLoadCodes), Medication::getCode);
        var drone = droneRepository.findBySerialNumber(droneSerialNumber)
                .orElseThrow(() -> new NoSuchDroneException(droneSerialNumber));

        checks.forEach(check -> check.check(drone, medicationLoads, medicationsByCodes));

        medicationLoads.forEach(medicationLoad -> droneMedicationRepository.save(DroneMedication.builder()
                .drone(drone)
                .medication(medicationsByCodes.get(medicationLoad.getMedicationCode()))
                .count(medicationLoad.getCount())
                .build()));

        drone.setState(LOADING);

        return true;
    }
}

package ru.ashepelev.drones.api.drone.service.load.check.impl;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.ashepelev.drones.api.drone.service.load.check.DroneLoadingCheck;
import ru.ashepelev.drones.dto.medication.MedicationLoadDto;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.medication.Medication;
import ru.ashepelev.drones.exception.medication.NoSuchMedicationException;

import java.util.List;
import java.util.Map;

import static ru.ashepelev.drones.utils.CollectionUtils.mapToSet;
import static ru.ashepelev.drones.utils.CollectionUtils.minus;

@Order(2)
@Component
public class MedicationExistenceCheck implements DroneLoadingCheck {
    @Override
    public void check(Drone drone,
                      List<MedicationLoadDto> medicationLoads,
                      Map<String, Medication> medicationsByCodes) {
        var medicationLoadCodes = mapToSet(medicationLoads, MedicationLoadDto::getMedicationCode);
        var diff = minus(medicationLoadCodes, medicationsByCodes.keySet());
        if (!diff.isEmpty()) {
            throw new NoSuchMedicationException(diff.get(0));
        }
    }
}

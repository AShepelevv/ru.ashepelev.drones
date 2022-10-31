package ru.ashepelev.drones.api.drone.service.load.check.impl;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.ashepelev.drones.api.drone.service.load.check.DroneLoadingCheck;
import ru.ashepelev.drones.dto.medication.MedicationLoadDto;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.medication.Medication;

import java.util.List;
import java.util.Map;

import static ru.ashepelev.drones.utils.CollectionUtils.mapToSet;

@Order(0)
@Component
public class MedicationDistinctCheck implements DroneLoadingCheck {
    @Override
    public void check(Drone drone,
                      List<MedicationLoadDto> medicationLoads,
                      Map<String, Medication> medicationsByCodes) {
        var medicationLoadCodes = mapToSet(medicationLoads, MedicationLoadDto::getMedicationCode);
        if (medicationLoads.size() != medicationLoadCodes.size())
            throw new IllegalArgumentException("Medication codes must be distinct");
    }
}

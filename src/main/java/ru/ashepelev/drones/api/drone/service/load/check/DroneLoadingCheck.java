package ru.ashepelev.drones.api.drone.service.load.check;

import ru.ashepelev.drones.dto.medication.MedicationLoadDto;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.medication.Medication;

import java.util.List;
import java.util.Map;

public interface DroneLoadingCheck {
    void check(Drone drone, List<MedicationLoadDto> medicationLoads, Map<String, Medication> medicationsByCodes);
}

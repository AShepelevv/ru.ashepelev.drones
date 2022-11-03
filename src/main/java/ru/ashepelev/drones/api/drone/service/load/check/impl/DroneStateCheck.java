package ru.ashepelev.drones.api.drone.service.load.check.impl;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.ashepelev.drones.api.drone.service.load.check.DroneLoadingCheck;
import ru.ashepelev.drones.dto.medication.MedicationLoadDto;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.medication.Medication;

import java.util.List;
import java.util.Map;

import static ru.ashepelev.drones.entity.drone.constants.DroneState.IDLE;

@Order(1)
@Component
public class DroneStateCheck implements DroneLoadingCheck {
    @Override
    public void check(Drone drone,
                      List<MedicationLoadDto> medicationLoads,
                      Map<String, Medication> medicationsByCodes) {
        if (drone.getState() != IDLE) {
            throw new IllegalStateException("Drone must be in state IDLE to be loaded");
        }
    }
}

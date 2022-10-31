package ru.ashepelev.drones.dto.drone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.drone.constants.DroneModel;

@Data
@Builder
@AllArgsConstructor
public class DroneDto {
    private String serialNumber;
    private DroneModel model;
    private double weightLimit;
    private double batteryCapacity;

    public static DroneDto from(Drone drone) {
        return DroneDto.builder()
                .serialNumber(drone.getSerialNumber())
                .model(drone.getModel())
                .weightLimit(drone.getWeightLimit())
                .batteryCapacity(drone.getBatteryCapacity())
                .build();
    }
}

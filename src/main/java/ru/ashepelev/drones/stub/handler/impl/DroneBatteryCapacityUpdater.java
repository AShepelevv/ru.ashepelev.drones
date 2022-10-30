package ru.ashepelev.drones.stub.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.drone.constants.DroneState;
import ru.ashepelev.drones.stub.handler.DroneUpdateHandler;

import java.util.Map;
import java.util.function.BiFunction;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Service
@RequiredArgsConstructor
public class DroneBatteryCapacityUpdater implements DroneUpdateHandler {
    private final Map<DroneState, Double> batteryChangeRateByDroneState;

    @Override
    public void update(Drone drone) {
        var batteryCapacityChangeRate = batteryChangeRateByDroneState.get(drone.getState());
        BiFunction<Double, Double, Double> aggregateFunction = batteryCapacityChangeRate > 0 ? Math::min : Math::max;
        var baseValue = batteryCapacityChangeRate > 0 ? 100.0 : 0.0;
        drone.setBatteryCapacity(aggregateFunction.apply(baseValue,
                drone.getBatteryCapacity() + batteryCapacityChangeRate));
    }
}

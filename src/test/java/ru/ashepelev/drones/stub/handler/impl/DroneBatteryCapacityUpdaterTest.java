package ru.ashepelev.drones.stub.handler.impl;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.drone.constants.DroneState;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;
import static ru.ashepelev.drones.entity.drone.constants.DroneState.*;

class DroneBatteryCapacityUpdaterTest {
    private static final Offset<Double> OFFSET = offset(0.000001);
    private final DroneBatteryCapacityUpdater updater = new DroneBatteryCapacityUpdater(Map.of(
            IDLE, +2.0,
            LOADING, -0.1,
            LOADED, -0.1,
            DELIVERING, -3.0,
            DELIVERED, -1.0,
            RETURNING, -1.0
    ));

    @Test
    void updateIdle() {
        var drone = Drone.builder().state(IDLE).batteryCapacity(10.0).build();
        updater.update(drone);
        assertThat(drone.getBatteryCapacity()).isEqualTo(12.0, OFFSET);
    }

    @Test
    void updateIdleMax() {
        var drone = Drone.builder().state(IDLE).batteryCapacity(99.0).build();
        updater.update(drone);
        assertThat(drone.getBatteryCapacity()).isEqualTo(100.0, OFFSET);
        assertThat(drone.getBatteryCapacity()).isLessThanOrEqualTo(100.0);
    }

    @ParameterizedTest
    @EnumSource(value = DroneState.class, names = {"LOADED", "LOADING", "DELIVERED", "RETURNING", "DELIVERING"})
    void min(DroneState state) {
        var drone = Drone.builder().state(state).batteryCapacity(0.01).build();
        updater.update(drone);
        assertThat(drone.getBatteryCapacity()).isEqualTo(0.0, OFFSET);
        assertThat(drone.getBatteryCapacity()).isGreaterThanOrEqualTo(0.0);
    }

    @ParameterizedTest
    @EnumSource(value = DroneState.class, names = {"LOADED", "LOADING"})
    void updateLoad(DroneState state) {
        var drone = Drone.builder().state(state).batteryCapacity(99.0).build();
        updater.update(drone);
        assertThat(drone.getBatteryCapacity()).isEqualTo(98.9, OFFSET);
    }

    @ParameterizedTest
    @EnumSource(value = DroneState.class, names = {"DELIVERED", "RETURNING"})
    void complete(DroneState state) {
        var drone = Drone.builder().state(state).batteryCapacity(99.0).build();
        updater.update(drone);
        assertThat(drone.getBatteryCapacity()).isEqualTo(98.0, OFFSET);
    }

    @ParameterizedTest
    @EnumSource(value = DroneState.class, names = {"DELIVERING"})
    void delivering(DroneState state) {
        var drone = Drone.builder().state(state).batteryCapacity(99.0).build();
        updater.update(drone);
        assertThat(drone.getBatteryCapacity()).isEqualTo(96.0, OFFSET);
    }
}

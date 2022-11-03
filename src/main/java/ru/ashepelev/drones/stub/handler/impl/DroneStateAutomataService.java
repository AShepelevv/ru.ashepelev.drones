package ru.ashepelev.drones.stub.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.drone.constants.DroneState;
import ru.ashepelev.drones.stub.entity.flight.Activity;
import ru.ashepelev.drones.stub.entity.flight.ActivityRepository;
import ru.ashepelev.drones.stub.entity.flight.constant.ActivityType;
import ru.ashepelev.drones.stub.handler.DroneUpdateHandler;

import static java.time.LocalDateTime.now;
import static ru.ashepelev.drones.entity.drone.constants.DroneState.*;
import static ru.ashepelev.drones.stub.entity.flight.constant.ActivityType.FLIGHT;
import static ru.ashepelev.drones.utils.RandomUtils.randomPositiveInteger;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
@Service
@RequiredArgsConstructor
public class DroneStateAutomataService implements DroneUpdateHandler {
    private final ActivityRepository activityRepository;

    @Transactional
    @Override
    public void update(Drone drone) {
        log.debug("Batteries levels updating...");

        switch (drone.getState()) {
            case LOADING:
                activityRepository.findByDroneSerialNumberAndType(drone.getSerialNumber(), ActivityType.LOADING)
                        .ifPresentOrElse(activity -> checkIfExpiredAndSwitchToNewFlight(activity, drone, DELIVERING),
                                () -> activityRepository.save(defaultLoadingActivity(drone.getSerialNumber())));
                return;

            // Skip LOADED state
            case DELIVERING:
                activityRepository.findByDroneSerialNumberAndType(drone.getSerialNumber(), FLIGHT)
                        .ifPresent(activity -> checkIfExpiredAndSwitchToNewFlight(activity, drone, RETURNING));
                return;

            // Skip DELIVERED state
            case RETURNING:
                activityRepository.findByDroneSerialNumberAndType(drone.getSerialNumber(), FLIGHT)
                        .ifPresent(activity -> {
                            if (activity.getStartTimestamp().plusSeconds(activity.getDurationInSec()).isBefore(now())) {
                                drone.setState(IDLE);
                                activityRepository.delete(activity);
                            }
                        });
                return;
            default:
        }
    }

    private void checkIfExpiredAndSwitchToNewFlight(Activity activity, Drone drone, DroneState returning) {
        if (activity.getStartTimestamp().plusSeconds(activity.getDurationInSec()).isBefore(now())) {
            drone.setState(returning);
            switchToDefaultFlight(activity);
        }
    }

    private void switchToDefaultFlight(Activity activity) {
        activity.setDurationInSec(randomPositiveInteger(30));
        activity.setType(FLIGHT);
        activity.setStartTimestamp(now());
    }

    private Activity defaultLoadingActivity(String droneSerialNumber) {
        return Activity.builder()
                .droneSerialNumber(droneSerialNumber)
                .type(ActivityType.LOADING)
                .startTimestamp(now())
                .durationInSec(randomPositiveInteger(30))
                .build();
    }
}

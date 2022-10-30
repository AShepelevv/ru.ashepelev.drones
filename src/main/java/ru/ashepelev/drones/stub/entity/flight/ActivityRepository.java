package ru.ashepelev.drones.stub.entity.flight;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ashepelev.drones.stub.entity.flight.constant.ActivityType;

import java.util.Optional;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    Optional<Activity>  findByDroneSerialNumberAndType(String droneSerialNumber, ActivityType activityType);
}

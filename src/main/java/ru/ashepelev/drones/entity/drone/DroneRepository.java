package ru.ashepelev.drones.entity.drone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ashepelev.drones.entity.drone.constants.DroneState;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
    List<Drone> findAllByState(DroneState state);

    Optional<Drone> findBySerialNumber(String serialNumber);
}

package ru.ashepelev.drones.entity.droneMedication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DroneMedicationRepository extends JpaRepository<DroneMedication, UUID> {
}

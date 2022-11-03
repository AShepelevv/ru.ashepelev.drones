package ru.ashepelev.drones.entity.droneMedication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DroneMedicationRepository extends JpaRepository<DroneMedication, UUID> {
    @Query("select dm from DroneMedication dm " +
            " inner join fetch dm.medication m" +
            " inner join dm.drone d " +
            " where d.serialNumber = :droneSerialNumber")
    List<DroneMedication> getDroneMedicationsByDroneSerialNumber(@Param("droneSerialNumber") String droneSerialNumber);
}

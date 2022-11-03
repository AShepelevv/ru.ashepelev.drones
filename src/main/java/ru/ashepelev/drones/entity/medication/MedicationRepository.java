package ru.ashepelev.drones.entity.medication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {
    List<Medication> findAllByCodeIn(Collection<String> code);
}

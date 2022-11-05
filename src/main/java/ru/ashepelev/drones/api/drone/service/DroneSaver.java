package ru.ashepelev.drones.api.drone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ashepelev.drones.entity.drone.Drone;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class DroneSaver {
    private final EntityManager entityManager;

    @Transactional
    public void save(Drone drone) {
        entityManager.persist(drone);
    }
}

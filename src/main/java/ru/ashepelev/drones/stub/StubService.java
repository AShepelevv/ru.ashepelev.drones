package ru.ashepelev.drones.stub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ashepelev.drones.entity.drone.DroneRepository;
import ru.ashepelev.drones.stub.handler.DroneUpdateHandler;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StubService {
    private final DroneRepository droneRepository;
    private final List<DroneUpdateHandler> handlers;

    @Transactional
    @Scheduled(cron = "0/5 * * * * *")
    public void playStubbingCycle() {
        log.debug("Batteries levels updating...");
        droneRepository.findAll()
                .forEach(d -> handlers.forEach(h -> h.update(d)));
    }
}

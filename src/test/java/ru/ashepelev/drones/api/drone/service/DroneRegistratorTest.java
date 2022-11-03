package ru.ashepelev.drones.api.drone.service;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.ashepelev.drones.dto.drone.DroneRegistrationDto;
import ru.ashepelev.drones.entity.drone.Drone;

import javax.persistence.EntityManager;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.BEFORE_EACH_TEST_METHOD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ashepelev.drones.entity.drone.constants.DroneModel.Lightweight;
import static ru.ashepelev.drones.entity.drone.constants.DroneState.IDLE;

@DataJpaTest
@Import({DroneRegistrator.class})
@AutoConfigureEmbeddedDatabase(provider = ZONKY, refresh = BEFORE_EACH_TEST_METHOD)
class DroneRegistratorTest {
    @Autowired
    private DroneRegistrator droneRegistrator;
    @Autowired
    private EntityManager entityManager;

    private final static DroneRegistrationDto dto = DroneRegistrationDto.builder()
            .droneSerialNumber("A")
            .droneModel(Lightweight)
            .weightLimit(100.0)
            .build();

    @Test
    void registerDrone() {
        assertDoesNotThrow(() -> droneRegistrator.registerDrone(dto));
        assertThrows(Exception.class, () -> droneRegistrator.registerDrone(dto));

        var drone = entityManager.find(Drone.class, "A");
        assertThat(drone.getSerialNumber()).isEqualTo("A");
        assertThat(drone.getState()).isEqualTo(IDLE);
        assertThat(drone.getModel()).isEqualTo(Lightweight);
        assertThat(drone.getWeightLimit()).isEqualTo(100.0);
        assertThat(drone.getBatteryCapacity()).isEqualTo(0.0);
    }
}

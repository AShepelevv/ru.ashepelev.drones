package ru.ashepelev.drones.api.drone.service;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.ashepelev.drones.entity.drone.Drone;

import javax.persistence.EntityManager;
import java.util.function.Supplier;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.BEFORE_EACH_TEST_METHOD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ashepelev.drones.entity.drone.constants.DroneModel.Lightweight;
import static ru.ashepelev.drones.entity.drone.constants.DroneState.IDLE;
import static ru.ashepelev.drones.entity.drone.constants.DroneState.LOADED;

@DataJpaTest
@Import({DroneSaver.class})
@AutoConfigureEmbeddedDatabase(provider = ZONKY, refresh = BEFORE_EACH_TEST_METHOD)
class DroneSaverTest {
    @Autowired
    private DroneSaver droneSaver;
    @Autowired
    private EntityManager entityManager;

    private final static Supplier<Drone> droneGetter = () -> Drone.builder()
            .serialNumber("TEST")
            .model(Lightweight)
            .state(IDLE)
            .weightLimit(100.0)
            .batteryCapacity(0.0)
            .build();

    @Test
    void update() {
        final Drone drone = droneGetter.get();
        assertDoesNotThrow(() -> droneSaver.save(drone));
        drone.setState(LOADED);
        assertDoesNotThrow(() -> droneSaver.save(drone));

        var droneFromDb = entityManager.find(Drone.class, "TEST");
        assertThat(droneFromDb.getSerialNumber()).isEqualTo("TEST");
        assertThat(droneFromDb.getModel()).isEqualTo(Lightweight);
        assertThat(droneFromDb.getState()).isEqualTo(LOADED);
        assertThat(droneFromDb.getWeightLimit()).isEqualTo(100.0);
        assertThat(droneFromDb.getBatteryCapacity()).isEqualTo(0.0);
    }

    @Test
    void save() {
        assertDoesNotThrow(() -> droneSaver.save(droneGetter.get()));
        assertThrows(Exception.class, () -> droneSaver.save(droneGetter.get()));

        var droneFromDb = entityManager.find(Drone.class, "TEST");
        assertThat(droneFromDb.getSerialNumber()).isEqualTo("TEST");
        assertThat(droneFromDb.getModel()).isEqualTo(Lightweight);
        assertThat(droneFromDb.getState()).isEqualTo(IDLE);
        assertThat(droneFromDb.getWeightLimit()).isEqualTo(100.0);
        assertThat(droneFromDb.getBatteryCapacity()).isEqualTo(0.0);
    }
}

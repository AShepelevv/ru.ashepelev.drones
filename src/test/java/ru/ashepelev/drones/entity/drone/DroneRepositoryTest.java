package ru.ashepelev.drones.entity.drone;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.BEFORE_EACH_TEST_METHOD;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.ashepelev.drones.entity.drone.constants.DroneModel.Middleweight;
import static ru.ashepelev.drones.entity.drone.constants.DroneState.*;

@DataJpaTest
@Sql("/droneRepositoryTest.sql")
@AutoConfigureEmbeddedDatabase(provider = ZONKY, refresh = BEFORE_EACH_TEST_METHOD)
class DroneRepositoryTest {
    @Autowired
    private DroneRepository repository;
    @Autowired
    private TestEntityManager entityManager;

    private static final Drone drone1 =
            Drone.builder().serialNumber("A").model(Middleweight).state(IDLE).weightLimit(100.0).batteryCapacity(50.0).build();

    private static final Drone drone2 =
            Drone.builder().serialNumber("B").model(Middleweight).state(IDLE).weightLimit(100.0).batteryCapacity(100.0).build();

    private static final Drone drone3 =
            Drone.builder().serialNumber("C").model(Middleweight).state(LOADING).weightLimit(100.0).batteryCapacity(100.0).build();

    private static final Drone drone4 =
            Drone.builder().serialNumber("D").model(Middleweight).state(LOADED).weightLimit(100.0).batteryCapacity(100.0).build();

    private static final Drone drone5 =
            Drone.builder().serialNumber("E").model(Middleweight).state(DELIVERING).weightLimit(100.0).batteryCapacity(100.0).build();

    private static final Drone drone6 =
            Drone.builder().serialNumber("F").model(Middleweight).state(DELIVERED).weightLimit(100.0).batteryCapacity(100.0).build();

    private static final Drone drone7 =
            Drone.builder().serialNumber("G").model(Middleweight).state(RETURNING).weightLimit(100.0).batteryCapacity(100.0).build();

    @Test
    void findAllByState() {
        assertThat(repository.findAllByState(IDLE)).containsExactlyInAnyOrder(drone1, drone2);
        assertThat(repository.findAllByState(LOADING)).containsExactlyInAnyOrder(drone3);
        assertThat(repository.findAllByState(LOADED)).containsExactlyInAnyOrder(drone4);
        assertThat(repository.findAllByState(DELIVERING)).containsExactlyInAnyOrder(drone5);
        assertThat(repository.findAllByState(DELIVERED)).containsExactlyInAnyOrder(drone6);
        assertThat(repository.findAllByState(RETURNING)).containsExactlyInAnyOrder(drone7);
    }

    @Test
    void findBySerialNumber() {
        assertThat(repository.findBySerialNumber("F")).isPresent().get().isEqualTo(drone6);
        assertThat(repository.findBySerialNumber("NOT_EXISTING_SN")).isEmpty();
    }
}

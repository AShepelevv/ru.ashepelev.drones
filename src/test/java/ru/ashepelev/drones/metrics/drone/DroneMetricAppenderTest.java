package ru.ashepelev.drones.metrics.drone;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ashepelev.drones.api.drone.service.DroneService;
import ru.ashepelev.drones.dto.drone.DroneDto;
import ru.ashepelev.drones.entity.drone.Drone;
import ru.ashepelev.drones.entity.drone.DroneRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class})
class DroneMetricAppenderTest {
    private DroneMetricAppender droneMetricAppender;
    private final MeterRegistry registry= new SimpleMeterRegistry();
    @MockBean
    private DroneRepository droneRepository;
    @MockBean
    private DroneService droneService;

    @BeforeEach
    void constructor() {
        var drone1 = Drone.builder().serialNumber("TEST1").build();
        var drone2 = Drone.builder().serialNumber("TEST2").build();
        when(droneRepository.findAll()).thenReturn(List.of(drone1, drone2));

        droneMetricAppender = new DroneMetricAppender(registry, droneService, droneRepository);
        var meters = registry.getMeters();
        assertThat(meters).asList().hasSize(2);
        assertId(meters.get(0), "TEST1");
        assertId(meters.get(1), "TEST2");
    }

    @Test
    void append() {
        var droneDto = spy(DroneDto.builder().serialNumber("TEST3").build());
        doReturn(droneDto).when(droneService).getBySerialNumber(any());

        droneMetricAppender.append("TEST3");

        Gauge meter = (Gauge) registry.getMeters().get(2);
        assertId(meter,"TEST3");

        meter.value();
        verifySupplier("TEST3", droneDto);
    }

    private void assertId(Meter actual, String serialNumber) {
        assertThat(actual.getId().getName()).isEqualTo("drone_battery");
        assertThat(actual.getId().getTag("drone_serial_number")).isNotNull().isEqualTo(serialNumber);
    }

    private void verifySupplier(String serialNumber, DroneDto droneDto) {
        verify(droneService).getBySerialNumber(serialNumber);
        verify(droneDto).getBatteryCapacity();
    }
}

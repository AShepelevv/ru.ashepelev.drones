package ru.ashepelev.drones.api.drone;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.ashepelev.drones.api.common.Response;
import ru.ashepelev.drones.api.drone.service.DroneRegistrator;
import ru.ashepelev.drones.api.drone.service.DroneService;
import ru.ashepelev.drones.api.drone.service.load.DroneLoader;
import ru.ashepelev.drones.dto.drone.DroneDto;
import ru.ashepelev.drones.dto.drone.DroneRegistrationDto;
import ru.ashepelev.drones.dto.medication.LoadedMedicationDto;
import ru.ashepelev.drones.dto.medication.MedicationDto;
import ru.ashepelev.drones.dto.medication.MedicationLoadDto;
import ru.ashepelev.drones.exception.drone.NoSuchDroneException;

import java.util.List;

import static java.util.List.of;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.ashepelev.drones.entity.drone.constants.DroneModel.Heavyweight;
import static ru.ashepelev.drones.entity.drone.constants.DroneModel.Lightweight;

@ExtendWith({SpringExtension.class})
@WebFluxTest(controllers = DroneController.class)
class DroneControllerTest {
    @Autowired
    private WebTestClient webClient;
    @MockBean
    private DroneService droneService;
    @MockBean
    private DroneRegistrator droneRegistrator;
    @MockBean
    private DroneLoader droneLoader;

    @Test
    void register() {
        var droneDto1 = DroneRegistrationDto.builder()
                .droneSerialNumber("SN")
                .droneModel(Lightweight)
                .weightLimit(1.0)
                .build();

        doReturn(true).when(droneRegistrator).registerDrone(any());

        webClient.put()
                .uri("/api/drone/register")
                .bodyValue(droneDto1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Boolean, ?>>() {})
                .value(r -> {
                    assertThat(r.getError()).isNull();
                    assertThat(r.getData()).isNotNull().isTrue();
                    verify(droneRegistrator).registerDrone(droneDto1);
                });
    }

    @Test
    void validationTest() {
        var droneDto2 = DroneRegistrationDto.builder()
                .droneSerialNumber("SN")
                .droneModel(Lightweight)
                .weightLimit(-1.0)
                .build();

        webClient.put()
                .uri("/api/drone/register")
                .bodyValue(droneDto2)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(new ParameterizedTypeReference<Response<Boolean, ?>>() {})
                .value(r -> {
                    assertThat(r.getData()).isNull();
                    assertThat(r.getError()).isNotNull();
                    verifyNoInteractions(droneRegistrator);
                });
    }

    @Test
    void available() {
        var dto1 = DroneDto.builder()
                .serialNumber("SN1")
                .model(Lightweight)
                .weightLimit(77.0)
                .batteryCapacity(34.5)
                .build();
        var dto2 = DroneDto.builder()
                .serialNumber("SN2")
                .model(Heavyweight)
                .weightLimit(79.0)
                .batteryCapacity(31.5)
                .build();

        doReturn(of(dto1, dto2)).when(droneService).getAvailable();
        webClient.get()
                .uri("/api/drone/check/available")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<DroneDto>, ?>>() {})
                .value(r -> {
                    assertThat(r.getError()).isNull();
                    assertThat(r.getData()).isNotNull();
                    assertThat(r.getData()).isNotNull().asList().hasSize(2).containsExactlyInAnyOrder(dto1, dto2);
                });
    }

    @Test
    void checkBatteryCapacity() {
        var dto1 = DroneDto.builder()
                .serialNumber("SN1")
                .model(Lightweight)
                .weightLimit(77.0)
                .batteryCapacity(34.5)
                .build();
        doReturn(dto1).when(droneService).getBySerialNumber(any());

        webClient.get()
                .uri("/api/drone/check/SN1/battery")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Double, ?>>() {})
                .value(r -> {
                    assertThat(r.getError()).isNull();
                    assertThat(r.getData()).isNotNull();
                    assertThat(r.getData()).isEqualTo(34.5, offset(0.01));
                    verify(droneService).getBySerialNumber("SN1");
                });
    }

    @Test
    void checkLoadedMedication() {
        var imageId1 = randomUUID();
        var imageId2 = randomUUID();
        var medication1Dto = LoadedMedicationDto.builder()
                .count(2)
                .medicationDto(MedicationDto.builder().code("A").name("a").weight(77.0).imageId(imageId1).build())
                .build();
        var medication2Dto = LoadedMedicationDto.builder()
                .count(2)
                .medicationDto(MedicationDto.builder().code("B").name("b").weight(35.0).imageId(imageId2).build())
                .build();

        doReturn(of(medication1Dto, medication2Dto)).when(droneService).getLoadedMedication(any());

        webClient.get()
                .uri("/api/drone/check/SN1/medications")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<LoadedMedicationDto>, ?>>() {})
                .value(r -> {
                    assertThat(r.getError()).isNull();
                    assertThat(r.getData()).isNotNull().asList().hasSize(2)
                            .containsExactlyInAnyOrder(medication1Dto, medication2Dto);
                    verify(droneService).getLoadedMedication("SN1");
                });
    }

    @Test
    void load() {
        var medicationLoad1 = MedicationLoadDto.builder().medicationCode("A").count(2).build();
        var medicationLoad2 = MedicationLoadDto.builder().medicationCode("B").count(1).build();

        doReturn(true).when(droneLoader).load(any(), any());

        webClient.put()
                .uri("/api/drone/load/SN1")
                .bodyValue(of(medicationLoad1, medicationLoad2))
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Boolean, ?>>() {})
                .value(r -> {
                    assertThat(r.getError()).isNull();
                    assertThat(r.getData()).isNotNull().isTrue();
                    verify(droneLoader).load("SN1", of(medicationLoad1, medicationLoad2));
                });
    }

    @Test
    void handleNotFound() {
        doThrow(new NoSuchDroneException("TEST")).when(droneService).getBySerialNumber(any());

        webClient.get()
                .uri("/api/drone/check/SN1/battery")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(new ParameterizedTypeReference<Response<?, String>>() {})
                .value(r -> {
                    assertThat(r.getData()).isNull();
                    assertThat(r.getError()).isNotNull().isEqualTo("No such registered drone with serialNumber=TEST");
                    verify(droneService).getBySerialNumber("SN1");
                });
    }

    @Test
    void handleBadRequest() {
        doThrow(new IllegalArgumentException("TEST")).when(droneService).getBySerialNumber(any());

        webClient.get()
                .uri("/api/drone/check/SN1/battery")
            .exchange()
                .expectStatus().isBadRequest()
                .expectBody(new ParameterizedTypeReference<Response<?, String>>() {})
                .value(r -> {
                    assertThat(r.getData()).isNull();
                    assertThat(r.getError()).isNotNull().isEqualTo("TEST");
                    verify(droneService).getBySerialNumber("SN1");
                });
    }

    @Test
    void handleException() {
        var medicationLoad1 = MedicationLoadDto.builder().medicationCode("A").count(2).build();
        var medicationLoad2 = MedicationLoadDto.builder().medicationCode("B").count(1).build();

        doThrow(new NullPointerException("TEST")).when(droneLoader).load(any(), any());

        webClient.put()
                .uri("/api/drone/load/SN1")
                .bodyValue(of(medicationLoad1, medicationLoad2))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(new ParameterizedTypeReference<Response<Boolean, ?>>() {})
                .value(r -> {
                    assertThat(r.getData()).isNull();
                    assertThat(r.getError()).isNotNull().isEqualTo("TEST");
                    verify(droneLoader).load("SN1", of(medicationLoad1, medicationLoad2));
                });
    }
}

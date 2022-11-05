package ru.ashepelev.drones.metrics.drone;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ashepelev.drones.dto.drone.DroneRegistrationDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class})
@Import({DroneMetricAspect.class})
class DroneMetricAspectTest {
    @Autowired
    private DroneMetricAspect aspect;
    @MockBean
    private DroneMetricAppender appender;

    @Test
    void adviceOk() throws Throwable {
        var droneRegistrationDto = DroneRegistrationDto.builder().droneSerialNumber("TEST1").build();
        var pjp = mock(ProceedingJoinPoint.class);
        doReturn(true).when(pjp).proceed();
        doReturn(new Object[]{droneRegistrationDto}).when(pjp).getArgs();
        Boolean actual = (Boolean) aspect.advice(pjp);

        assertThat(actual).isTrue();
        verify(appender).append("TEST1");
    }

    @Test
    void adviceException() throws Throwable {
        var pjp = mock(ProceedingJoinPoint.class);
        doThrow(new Throwable()).when(pjp).proceed();
        assertThrows(Throwable.class, () -> aspect.advice(pjp));
        verifyNoInteractions(appender);
    }
}

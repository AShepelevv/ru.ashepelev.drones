package ru.ashepelev.drones.metrics.drone;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.ashepelev.drones.dto.drone.DroneRegistrationDto;

@Aspect
@Component
@RequiredArgsConstructor
public class DroneMetricAspect {
    private final DroneMetricAppender droneMetricAppender;

    @Around("@annotation(ru.ashepelev.drones.metrics.drone.DroneMetric) && " +
            "execution(* *(ru.ashepelev.drones.dto.drone.DroneRegistrationDto))")
    public Object advice(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        droneMetricAppender.append(((DroneRegistrationDto) pjp.getArgs()[0]).getDroneSerialNumber());
        return result;
    }
}

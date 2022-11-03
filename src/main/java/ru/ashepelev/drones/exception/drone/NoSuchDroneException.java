package ru.ashepelev.drones.exception.drone;

import java.util.NoSuchElementException;

import static java.lang.String.format;

public class NoSuchDroneException extends NoSuchElementException {
    public NoSuchDroneException(String serialNumber) {
        super(format("No such registered drone with serialNumber=%s", serialNumber));
    }
}

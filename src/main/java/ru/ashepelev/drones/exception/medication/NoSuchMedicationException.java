package ru.ashepelev.drones.exception.medication;

import java.util.NoSuchElementException;

import static java.lang.String.format;

public class NoSuchMedicationException extends NoSuchElementException {
    public NoSuchMedicationException(String medicationCode) {
        super(format("No such medication with code=%s", medicationCode));
    }
}

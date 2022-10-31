package ru.ashepelev.drones.utils;

import static java.lang.Math.abs;

public class Precision {
    private static final double EPS = 0.000001d;
    private double accuracy;
    private final double value;

    private Precision(double value) {
        this.value = value;
        this.accuracy = EPS;
    }

    public static Precision of(double value) {
        return new Precision(value);
    }

    public Precision withAccuracy(double accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public boolean isLessThan(double another) {
        return value <= another - accuracy;
    }

    public boolean isLessOrEqualTo(double another) {
        return value < another + accuracy;
    }

    public boolean isGreaterThan(double another) {
        return value >= another + accuracy;
    }

    public boolean isGreaterOrEqualTo(double another) {
        return value > another - accuracy;
    }

    public boolean isEqualTo(double another) {
        return abs(value - another) < accuracy;
    }
}

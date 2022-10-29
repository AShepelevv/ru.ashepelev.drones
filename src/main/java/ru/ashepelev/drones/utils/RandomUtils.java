package ru.ashepelev.drones.utils;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.floor;

public class RandomUtils {
    private static final Random random = new Random();

    public static String randomLowerString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static double randomDouble(double limit) {
        var positiveDouble = abs(random.nextDouble());
        return (positiveDouble - floor(positiveDouble)) * limit;
    }
}

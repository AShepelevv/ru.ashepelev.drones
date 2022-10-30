package ru.ashepelev.drones.utils;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.floor;

public class RandomUtils {
    private static final Random random = new Random();

    public static String randomLowerLetterString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static double randomPositiveDouble(double limit) {
        var eps = 1^-9;
        var notNegative = abs(random.nextDouble());
        var scale = notNegative - floor(notNegative);
        if (scale < eps) scale += eps;
        return scale * limit;
    }

    public static int randomPositiveInteger(int limit) {
        if (limit <= 0) throw new IllegalArgumentException("Limit value must be greater than zero");
        int value = random.nextInt(limit);
        return abs(value == 0 ? 1 : value);
    }
}

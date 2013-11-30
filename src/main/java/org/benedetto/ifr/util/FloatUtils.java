package org.benedetto.ifr.util;

public class FloatUtils {

    public static boolean floatEqual(float first, float second) {
        return Math.abs(first - second) < 0.0001;
    }

}

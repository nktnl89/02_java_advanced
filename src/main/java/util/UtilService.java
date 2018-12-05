package util;

import java.util.Random;

public class UtilService {
    public static long getRandomLong() {
        return (long) (new Random().nextDouble() * (1500));
    }

    public static int getRandomInt(int range) {
        return new Random().nextInt() * range;
    }

    public static int getRandomInt(int start, int finish) {
        return start + new Random().nextInt(finish - start);
    }
}

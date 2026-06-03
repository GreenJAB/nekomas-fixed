package net.greenjab.nekomasfixed.util;

import java.util.Random;

public class ModMathHelper {
    private static final Random RANDOM = new Random();

    public static boolean percentRandom(int percentValue) {
        //this percentValue is the value for generating the result eg. if i want a random boolean on the basis of 35%, it would be like percentRandom(35)
        if (percentValue <= 0) return false;
        if (percentValue >= 100) return true;
        return RANDOM.nextInt(100) < percentValue;
    }

    public static double getDouble(){
        return new Random().nextDouble()*100;
    }
}
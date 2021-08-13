package server;

import java.util.Random;

public class Randomizer
{
    private static final Random rand;
    
    public static int nextInt() {
        return Randomizer.rand.nextInt();
    }
    
    public static int nextInt(final int arg0) {
        return Randomizer.rand.nextInt(arg0);
    }
    
    public static void nextBytes(final byte[] bytes) {
        Randomizer.rand.nextBytes(bytes);
    }
    
    public static boolean nextBoolean() {
        return Randomizer.rand.nextBoolean();
    }
    
    public static double nextDouble() {
        return Randomizer.rand.nextDouble();
    }
    
    public static float nextFloat() {
        return Randomizer.rand.nextFloat();
    }
    
    public static long nextLong() {
        return Randomizer.rand.nextLong();
    }
    
    public static int rand(final int lbound, final int ubound) {
        return (int)(Randomizer.rand.nextDouble() * (ubound - lbound + 1) + lbound);
    }
    
    static {
        rand = new Random();
    }
}

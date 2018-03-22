/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.lessons.random;

import java.util.Random;

/**
 *
 * @author bfries
 */
public class RandomGenerator 
{
    private static Random generator = new Random(System.nanoTime());
    
    private RandomGenerator() {        
    }
        
    public static Long nextLong() {
        return generator.nextLong();
    }

    public static Long nextAbsLong() {
        return Math.abs(nextLong());
    }

    public static int nextInt()
    {
        return generator.nextInt();
    }

    public static int nextInt(int max)
    {
        return generator.nextInt(max);
    }    
}

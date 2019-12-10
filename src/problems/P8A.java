package problems;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Antoni
 */
public class P8A extends util.Problem {
    
    private static final int WIDTH = 25;
    private static final int HEIGHT = 6;
    private static int LAYERS;
    
    public void run() {
        try {
            List<String> input = readInput();

            String line = input.get(0);
            char[] bytes = line.toCharArray();
            
            final int SIZE = WIDTH * HEIGHT;
            
            LAYERS = line.length() / SIZE;
            
            int leastZeroes = Integer.MAX_VALUE;
            int layerIndex = -1;
            int valueForThatLayer = -1;
            
            for (int i = 0; i < LAYERS; i++) {
                int[] values = new int[] {0, 0, 0};
                for (int j = 0; j < SIZE; j++) {
                    int value = bytes[i * SIZE + j];
                    switch (value) {
                        case '0':
                        case '1':
                        case '2':
                            System.out.println(value);
                            values[value - '0']++;
                            break;
                        default:
                            System.out.println("Found value " + value + " in layer " + i);
                            break;
                    }
                }
                if (values[0] < leastZeroes) {
                    leastZeroes = values[0];
                    layerIndex = i;
                    valueForThatLayer = values[1] * values[2];
                }
            }
            
            System.out.println("Layer " + layerIndex + " has " + leastZeroes + " zeroes, with value = " + valueForThatLayer);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

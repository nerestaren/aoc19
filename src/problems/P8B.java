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
public class P8B extends util.Problem {
    
    private static final int WIDTH = 25;
    private static final int HEIGHT = 6;
    private static int LAYERS;
    private static final char BLACK = '0';
    private static final char WHITE = '1';
    private static final char TRANSPARENT = '2';
    
    public void run() {
        try {
            List<String> input = readInput();

            String line = input.get(0);
            char[] bytes = line.toCharArray();
            
            final int SIZE = WIDTH * HEIGHT;
            
            LAYERS = line.length() / SIZE;
            
            int[] result = new int[SIZE];
            for (int j = 0; j < SIZE; j++) {
                result[j] = bytes[j];
            }
            
            for (int i = 1; i < LAYERS; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (result[j] == TRANSPARENT) {
                        result[j] = bytes[i * SIZE + j];
                    }
                }
            }
            
            for (int j = 0; j < HEIGHT; j++) {
                for (int i = 0; i < WIDTH; i++) {
                    switch(result[j * WIDTH + i]) {
                        case BLACK:
                            System.out.print('▓');
                            break;
                        case WHITE:
                            System.out.print('░');
                            break;
                        default:
                            System.out.print('?');
                            break;
                    }
                }
                System.out.print('\n');
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

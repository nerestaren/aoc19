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
public class P10A extends util.Problem {
    
    private static int WIDTH;
    private static int HEIGHT;
    private static final char ASTEROID = '#';
    private static boolean[][] MAP;
    
    private void initializeMap(List<String> input) {
        WIDTH = input.get(0).length();
        HEIGHT = input.size();

        MAP = new boolean[HEIGHT][WIDTH];

        int y = 0;
        for (String line : input) {
            char[] chars = line.toCharArray();
            for (int x = 0; x < WIDTH; x++) {
                MAP[y][x] = chars[x] == ASTEROID;
            }
            y++;
        }
    }
    
    private int getValueBestLocation() {
        int maxSeen = 0;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (MAP[y][x]) {
                    int seen = getSeenFrom(x, y);
                    if (seen > maxSeen) {
                        maxSeen = seen;
                    }
                }
            }
        }
        return maxSeen;
    }
    
    private int getSeenFrom(int x0, int y0) {
        int seen = 0;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (!(y == y0 && x == x0) && MAP[y][x]) {
                    int dx = x - x0;
                    int dy = y - y0;
                    int gcd = gcd(Math.abs(dx), Math.abs(dy));
                    boolean found = false;
                    for (int i = 1; i < gcd && !found; i++) {
                        found = MAP[y0 + (dy / gcd * i)][x0 + (dx / gcd * i)];
                    }
                    if (!found)
                        seen++;
                }
            }
        }
        return seen;
    }
    
    private int gcd(int a, int b) {
        int c;
        if (a > b) {
            c = b;
            b = a;
            a = c;
        }
        
        while (b != 0) {
            c = b;
            b = a % b;
            a = c;
        }
        
        return a;
    }
    
    public void run() {
        try {
            List<String> input = readInput();
            
            initializeMap(input);
                                    
            int numAsteroidsSeen = getValueBestLocation();
            System.out.println("Best number of asteroids seen: " + numAsteroidsSeen);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

package problems;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Antoni
 */
public class P12B extends util.Problem {
    
    public static long gcd(long a, long b) {
        long temp;
        if (b > a) {
            temp = a;
            a = b;
            b = temp;
        }
        while (b > 0) {
            temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }
    
    public static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    public static long lcm(long... input) {
        long result = input[0];
        for(int i = 1; i < input.length; i++) result = lcm(result, input[i]);
        return result;
    }
    
    private class Moon {
        private int[] originalPos;
        private int[] pos;
        private int[] vel;
        private long[] cycle;
        
        public Moon(int[] pos) {
            this.originalPos = pos;
            this.pos = Arrays.copyOf(pos, pos.length);
            this.vel = new int[3];
            this.cycle = new long[3];
        }
        
        public int energy() {
            return (Math.abs(this.pos[0]) + Math.abs(this.pos[1]) + Math.abs(this.pos[2])) *
                   (Math.abs(this.vel[0]) + Math.abs(this.vel[1]) + Math.abs(this.vel[2]));
        }
        
        public void applyGravity(Moon other) {
            for (int i = 0; i < this.pos.length; i++) {
                if (this.pos[i] < other.pos[i]) {
                    this.vel[i]++;
                    other.vel[i]--;
                } else if (this.pos[i] > other.pos[i]) {
                    this.vel[i]--;
                    other.vel[i]++;
                }
            }
        }
        
        public void applyVelocity() {
            for (int i = 0; i < this.pos.length; i++) {
                this.pos[i] += this.vel[i];
            }
        }
        
        @Override
        public String toString() {
            return String.format("pos=<x=%3d, y=%3d, z=%3d>, vel=<x=%3d, y=%3d, z=%3d>, repeats=<x=%3d, y=%3d, z=%3d>",
                    this.pos[0], this.pos[1], this.pos[2], this.vel[0], this.vel[1], this.vel[2], this.cycle[0], this.cycle[1], this.cycle[2]);
        }
        
        public void checkCycle(long c) {
            for (int i = 0; i < this.pos.length; i++) {
                if (this.pos[i] == this.originalPos[i] && this.vel[i] == 0) {
                    this.cycle[i] = c;
                }
            }
        }
        
        public boolean done() {
            for (long c : this.cycle) {
                if (c == 0)
                    return false;
            }
            return true;
        }
        
        public long getCycle() {
            return lcm(cycle);
        }
        
        public boolean isAxisOrigin(int axis) {
            return this.pos[axis] == this.originalPos[axis] && this.vel[axis] == 0;
        }
    }
    
    public void run() {
        try {
            List<String> input = readInput();

            Pattern regex = Pattern.compile("<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>");
            Moon[] moons = new Moon[input.size()];
            int[][] original = new int[input.size()][3];
            int index = 0;
            for (String s : input) {
                Matcher m = regex.matcher(s);
                if (m.find()) {
                    original[index] = new int[]{Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3))};
                    moons[index] = new Moon(original[index]);
                    index++; 
                }
            }
            
            long i = 0;
            System.out.printf("After %d steps:\n", i);
            for (int a = 0; a < moons.length; a++) {
                System.out.println(moons[a]);
            }
            long[] cycles = new long[] {0, 0, 0};
            while (!(cycles[0] != 0 && cycles[1] != 0 && cycles[2] != 0)) {
                for (int a = 0; a < moons.length; a++) {
                    for (int b = a + 1; b < moons.length; b++) {
                        moons[a].applyGravity(moons[b]);
                    }
                }
                for (int a = 0; a < moons.length; a++) {
                    moons[a].applyVelocity();
                }
                i++;
                /*for (int a = 0; a < moons.length; a++) {
                    moons[a].checkCycle(i);
                }*/
                for (int axis = 0; axis < 3; axis++) {
                    if (cycles[axis] == 0) {
                        boolean foundNotOrigin = false;
                        for (int a = 0; a < moons.length && !foundNotOrigin; a++) {
                            foundNotOrigin = !moons[a].isAxisOrigin(axis);
                        }
                        if (!foundNotOrigin) {
                            cycles[axis] = i;
                        }
                    }
                }
            }
            
            System.out.printf("After %d steps:\n", i);
            for (int a = 0; a < moons.length; a++) {
                System.out.println(moons[a]);
            }
            
            int total = 0;
            for (Moon m : moons) {
                total += m.energy();
            }
            System.out.printf("LCM of the cycles: %d\n", lcm(cycles));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

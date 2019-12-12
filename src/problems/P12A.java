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
public class P12A extends util.Problem {
    
    private class Moon {
        private int[] pos;
        private int[] vel;
        
        public Moon(int[] pos) {
            this.pos = Arrays.copyOf(pos, pos.length);
            this.vel = new int[3];
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
            return String.format("pos=<x=%3d, y=%3d, z=%3d>, vel=<x=%3d, y=%3d, z=%3d>, energy=%4d",
                    this.pos[0], this.pos[1], this.pos[2], this.vel[0], this.vel[1], this.vel[2], this.energy());
        }
    }
    
    public void run() {
        try {
            List<String> input = readInput();

            Pattern regex = Pattern.compile("<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>");
            Moon[] moons = new Moon[input.size()];
            int i = 0;
            for (String s : input) {
                Matcher m = regex.matcher(s);
                if (m.find())
                    moons[i++] = new Moon(new int[]{Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3))});
            }
            
            for (i = 0; i < 1000; i++) {
                if (i / 100 == 0 && i % 10 == 0 || i == 100) {
                    System.out.printf("After %d steps:\n", i);
                    for (int a = 0; a < moons.length; a++) {
                        System.out.println(moons[a]);
                    }
                }
                for (int a = 0; a < moons.length; a++) {
                    for (int b = a + 1; b < moons.length; b++) {
                        moons[a].applyGravity(moons[b]);
                    }
                }
                for (int a = 0; a < moons.length; a++) {
                    moons[a].applyVelocity();
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
            System.out.printf("Total energy in the system: %d\n", total);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

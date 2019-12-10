package problems;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author Antoni
 */
public class P10B extends util.Problem {
    private class Point implements Comparable<Point> {
        private int x;
        private int y;
        private Point origin;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
            this.origin = null;
        }
        
        public Point(int x, int y, Point origin) {
            this.x = x;
            this.y = y;
            this.origin = origin;
        }
        
        public boolean equals(Point b) {
            return this.x == b.x && this.y == b.y;
        }
        
        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }
        
        public int getX() {
            return x;
        }
        
        public int getY() {
            return y;
        }
        
        public double angle() {
            double angle = Math.atan2(y - origin.y, x - origin.x) + Math.PI / 2;
            if (angle < 0) {
                return angle + 2 * Math.PI;
            } else
                return angle;
        }

        @Override
        public int compareTo(Point o) {
            return ((angle() - o.angle() < 0) ? -1 : +1);
        }
    }
    
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
    
    private Point getBestLocation() {
        int maxSeen = 0;
        Point location = null;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (MAP[y][x]) {
                    int seen = getNumberSeenFrom(x, y);
                    if (seen > maxSeen) {
                        maxSeen = seen;
                        location = new Point(x, y);
                    }
                }
            }
        }
        return location;
    }
    
    private int getNumberSeenFrom(int x0, int y0) {
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
    
    private PriorityQueue<Point> getSeenFrom(Point o) {
        int x0 = o.getX();
        int y0 = o.getY();
        PriorityQueue<Point> seen = new PriorityQueue<>();
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
                        seen.add(new Point(x, y, o));
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
            
            Point base = getBestLocation();
            PriorityQueue<Point> seenFromBase = getSeenFrom(base);
            int found = 0;
            final int desired = 200;
            System.out.println("Base: " + base);
            System.out.println("");
            Point p = null;
            while (found < desired) {
                while (!seenFromBase.isEmpty() && found < desired) {
                    p = seenFromBase.poll();
                    found++;
                    MAP[p.getY()][p.getX()] = false; // pew pew
                    System.out.println(found + ": " + p + ", " + p.angle() * 180 / Math.PI);
                }
                if (found < desired) {
                    seenFromBase = getSeenFrom(base);
                }
            }
            
            System.out.println(p.getX() * 100 + p.getY());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

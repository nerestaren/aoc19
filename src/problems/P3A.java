/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Antoni
 */
public class P3A extends util.Problem {

    private class Point {
        private int x;
        private int y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public boolean equals(Point b) {
            return this.x == b.x && this.y == b.y;
        }
        
        public int manhattanDistance(Point b) {
            return Math.abs(this.x - b.x) + Math.abs(this.y - b.y);
        }
        
        public Point up() {
            return new Point(this.x, this.y + 1);
        }
        
        public Point right() {
            return new Point(this.x + 1, this.y);
        }
        
        public Point down() {
            return new Point(this.x, this.y - 1);
        }
        
        public Point left() {
            return new Point(this.x - 1, this.y);
        }
        
        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }
    }
    
    public void run() {
        try {
            List<String> input = readInput();

            ArrayList<LinkedList<Point>> cables = new ArrayList<>(input.size());
            
            for (String s : input) {
                String[] split = s.split(",");
                Point p = new Point(0, 0);
                LinkedList<Point> positions = new LinkedList<>();
                for (String instruction: split) {
                    char direction = instruction.charAt(0);
                    int steps = Integer.parseInt(instruction.substring(1));
                    switch (direction) {
                        case 'U':
                            for (int i = 0; i < steps; i++) {
                                positions.add(p = p.up());
                            }
                            break;
                        case 'R':
                            for (int i = 0; i < steps; i++) {
                                positions.add(p = p.right());
                            }
                            break;
                        case 'D':
                            for (int i = 0; i < steps; i++) {
                                positions.add(p = p.down());
                            }
                            break;
                        case 'L':
                            for (int i = 0; i < steps; i++) {
                                positions.add(p = p.left());
                            }
                            break;
                        default:
                            throw new Exception("Direcció desconeguda: " + direction);
                    }
                }
                cables.add(positions);
            }
            
            Point origin = new Point(0, 0);
            Point found = null;
            
            for (Point a : cables.get(0)) {
                for (Point b : cables.get(1)) {
                    if (a.equals(b)) {
                        if (found == null || (found.manhattanDistance(origin) > a.manhattanDistance(origin))) {
                            found = a;
                            System.out.println("FOUND " + a);
                        }
                    }
                }
            }
            
            
            
            System.out.println("Intersecció: " + found + ", amb distància de Manhattan = " + found.manhattanDistance(origin));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

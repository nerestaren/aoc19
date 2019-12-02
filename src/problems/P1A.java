/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problems;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Antoni
 */
public class P1A extends util.Problem {
    public void run() {
        try {
            List<String> input = readInput();
            
            int total = 0;
            for (String line : input) {
                int value = Integer.parseInt(line);
                total += Math.floor(value / 3.0) - 2;
            };
            
            System.out.println(total);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
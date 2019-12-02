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
public class P2A extends util.Problem {

    public void run() {
        try {
            List<String> input = readInput();

            String inputLine = input.get(0);
            String[] split = inputLine.split(",");
            int[] memory = new int[split.length];
            for (int i = 0; i < memory.length; i++) {
                memory[i] = Integer.parseInt(split[i]);
            }
            
            memory[1] = 12;
            memory[2] = 2;

            int ip = 0;
            loop:
            while (true) {
                switch (memory[ip]) {
                    case 1: {
                        // ADDITION
                        int pa, pb, pc;
                        pa = memory[ip + 1];
                        pb = memory[ip + 2];
                        pc = memory[ip + 3];
                        int a, b, c;
                        a = memory[pa];
                        b = memory[pb];
                        c = a + b;
                        memory[pc] = c;
                        ip += 4;
                        break;
                    }
                    case 2: {
                        // PRODUCT
                        
                        int pa, pb, pc;
                        pa = memory[ip + 1];
                        pb = memory[ip + 2];
                        pc = memory[ip + 3];
                        int a, b, c;
                        a = memory[pa];
                        b = memory[pb];
                        c = a * b;
                        memory[pc] = c;
                        ip += 4;
                        break;
                    }
                    case 99: 
                        // EXIT
                        break loop;
                    default:
                        System.err.println("ERROR");
                        break;
                }
            }
            
            System.out.println(memory[0]);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

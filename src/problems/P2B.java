/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problems;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Antoni
 */
public class P2B extends util.Problem {

    public void run() {
        try {
            List<String> input = readInput();

            String inputLine = input.get(0);
            String[] split = inputLine.split(",");
            int[] MEMORY = new int[split.length];
            for (int i = 0; i < MEMORY.length; i++) {
                MEMORY[i] = Integer.parseInt(split[i]);
            }
            
            all:
            for (int noun = 0; noun < 100; noun++) {
                for (int verb = 0; verb < 100; verb++) {
                    int[] memory = Arrays.copyOf(MEMORY, MEMORY.length);
            
                    memory[1] = noun;
                    memory[2] = verb;

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
                    
                    if (memory[0] == 19690720) {
                        System.out.println(100 * noun + verb);
                        break all;
                    }
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

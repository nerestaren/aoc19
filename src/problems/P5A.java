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
public class P5A extends util.Problem {
    
    int[] memory;
    int ip;
    
    public void run() {
        try {
            List<String> input = readInput();

            String inputLine = input.get(0);
            String[] split = inputLine.split(",");
            int[] MEMORY = new int[split.length];
            for (int i = 0; i < MEMORY.length; i++) {
                MEMORY[i] = Integer.parseInt(split[i]);
            }
            
            memory = Arrays.copyOf(MEMORY, MEMORY.length);
            
            LinkedList<Integer> programInput = new LinkedList<>();
            programInput.push(1);
            
            LinkedList<Integer> programOutput = new LinkedList<>();

            ip = 0;
            boolean exit = false;
            while (!exit) {
                int opcodeAndParameters = memory[ip++];
                int opcode = opcodeAndParameters % 100;
                int parameterModes = opcodeAndParameters / 100;
                switch (opcode) {
                    case 1: {
                        // ADDITION
                        int ma, mb, mc;
                        ma = parameterModes % 10;
                        parameterModes /= 10;
                        mb = parameterModes % 10;
                        parameterModes /= 10;
                        mc = parameterModes % 10;
                        parameterModes /= 10;
                        int pa, pb, pc;
                        pa = memory[ip++];
                        pb = memory[ip++];
                        pc = memory[ip++];
                        int a, b, c;
                        if (ma == 0)
                            a = memory[pa];
                        else
                            a = pa;
                        if (mb == 0)
                            b = memory[pb];
                        else
                            b = pb;
                        c = a + b;
                        memory[pc] = c;
                        break;
                    }
                    case 2: {
                        // PRODUCT
                        int ma, mb, mc;
                        ma = parameterModes % 10;
                        parameterModes /= 10;
                        mb = parameterModes % 10;
                        parameterModes /= 10;
                        mc = parameterModes % 10;
                        parameterModes /= 10;
                        int pa, pb, pc;
                        pa = memory[ip++];
                        pb = memory[ip++];
                        pc = memory[ip++];
                        int a, b, c;
                        if (ma == 0)
                            a = memory[pa];
                        else
                            a = pa;
                        if (mb == 0)
                            b = memory[pb];
                        else
                            b = pb;
                        c = a * b;
                        memory[pc] = c;
                        break;
                    }
                    case 3: {
                        // INPUT
                        int pa;
                        pa = memory[ip++];
                        memory[pa] = programInput.pop();
                        break;
                    }
                    case 4: {
                        // OUTPUT
                        int ma;
                        ma = parameterModes % 10;
                        parameterModes /= 10;
                        int pa;
                        pa = memory[ip++];
                        int a;
                        if (ma == 0)
                            a = memory[pa];
                        else
                            a = pa;
                        programOutput.add(a);
                        break;
                    }
                    case 99: 
                        // EXIT
                        exit = true;
                        break;
                    default:
                        System.err.println("ERROR");
                        break;
                }
            }
            
            while (!programOutput.isEmpty()) {
                System.out.println(programOutput.removeFirst());
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

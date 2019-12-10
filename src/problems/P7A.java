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
public class P7A extends util.Problem {
        
    private LinkedList<Integer> runProgram(LinkedList<Integer> input, int[] memory) {
        
        LinkedList<Integer> output = new LinkedList<Integer>();
        
        int ip = 0;
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
                    memory[pa] = input.pop();
                    break;
                }
                case 4: {
                    // OUTPUT
                    int ma;
                    ma = parameterModes % 10;
                    int pa;
                    pa = memory[ip++];
                    int a;
                    if (ma == 0)
                        a = memory[pa];
                    else
                        a = pa;
                    output.add(a);
                    break;
                }
                case 5: {
                    // JUMP-IF-TRUE
                    int ma, mb;
                    ma = parameterModes % 10;
                    parameterModes /= 10;
                    mb = parameterModes % 10;
                    int pa, pb;
                    pa = memory[ip++];
                    pb = memory[ip++];
                    int a, b;
                    if (ma == 0)
                        a = memory[pa];
                    else
                        a = pa;
                    if (mb == 0)
                        b = memory[pb];
                    else
                        b = pb;
                    if (a != 0) {
                        ip = b;
                    }
                    break;
                }
                case 6: {
                    // JUMP-IF-FALSE
                    int ma, mb;
                    ma = parameterModes % 10;
                    parameterModes /= 10;
                    mb = parameterModes % 10;
                    int pa, pb;
                    pa = memory[ip++];
                    pb = memory[ip++];
                    int a, b;
                    if (ma == 0)
                        a = memory[pa];
                    else
                        a = pa;
                    if (mb == 0)
                        b = memory[pb];
                    else
                        b = pb;
                    if (a == 0) {
                        ip = b;
                    }
                    break;
                }
                case 7: {
                    // LESS-THAN
                    int ma, mb;
                    ma = parameterModes % 10;
                    parameterModes /= 10;
                    mb = parameterModes % 10;
                    int pa, pb, pc;
                    pa = memory[ip++];
                    pb = memory[ip++];
                    pc = memory[ip++];
                    int a, b;
                    if (ma == 0)
                        a = memory[pa];
                    else
                        a = pa;
                    if (mb == 0)
                        b = memory[pb];
                    else
                        b = pb;
                    memory[pc] = a < b ? 1 : 0;
                    break;
                }
                case 8: {
                    // EQUALS
                    int ma, mb;
                    ma = parameterModes % 10;
                    parameterModes /= 10;
                    mb = parameterModes % 10;
                    int pa, pb, pc;
                    pa = memory[ip++];
                    pb = memory[ip++];
                    pc = memory[ip++];
                    int a, b;
                    if (ma == 0)
                        a = memory[pa];
                    else
                        a = pa;
                    if (mb == 0)
                        b = memory[pb];
                    else
                        b = pb;
                    memory[pc] = a == b ? 1 : 0;
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
        
        return output;
    }
    
    public void run() {
        try {
            List<String> input = readInput();

            String inputLine = input.get(0);
            String[] split = inputLine.split(",");
            int[] MEMORY = new int[split.length];
            for (int i = 0; i < MEMORY.length; i++) {
                MEMORY[i] = Integer.parseInt(split[i]);
            }
                                    
            LinkedList<Integer> programInput = new LinkedList<>();
            
            LinkedList<Integer> programOutput;

            int maxSignal = Integer.MIN_VALUE;
            int pa, pb, pc, pd, pe;
            pa = 0;
            for (int a = 0; a < 5; a++) {
                programInput.add(a);
                programInput.add(pa);
                programOutput = runProgram(programInput, Arrays.copyOf(MEMORY, MEMORY.length));
                pb = programOutput.removeFirst();
                for (int b = 0; b < 5; b++) {
                    if (b == a)
                        continue;
                    programInput.add(b);
                    programInput.add(pb);
                    programOutput = runProgram(programInput, Arrays.copyOf(MEMORY, MEMORY.length));
                    pc = programOutput.removeFirst();
                    for (int c = 0; c < 5; c++) {
                        if (c == b || c == a)
                            continue;
                        programInput.add(c);
                        programInput.add(pc);
                        programOutput = runProgram(programInput, Arrays.copyOf(MEMORY, MEMORY.length));
                        pd = programOutput.removeFirst();
                        for (int d = 0; d < 5; d++) {
                            if (d == c || d == b || d == a)
                                continue;
                            programInput.add(d);
                            programInput.add(pd);
                            programOutput = runProgram(programInput, Arrays.copyOf(MEMORY, MEMORY.length));
                            pe = programOutput.removeFirst();
                            for (int e = 0; e < 5; e++) {
                                if (e == d || e == c || e == b || e == a)
                                    continue;
                                programInput.add(e);
                                programInput.add(pe);
                                programOutput = runProgram(programInput, Arrays.copyOf(MEMORY, MEMORY.length));
                                int signal = programOutput.removeFirst();
                                if (signal > maxSignal) {
                                    maxSignal = signal;
                                }
                            }
                        }
                    }
                }
            }
            
            System.out.println("Max signal: " + maxSignal);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

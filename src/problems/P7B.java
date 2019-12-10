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
public class P7B extends util.Problem {
    
    private class IntcodeComputer {
        private int[] memory;
        private int ip;
        protected LinkedList<Integer> input;
        protected LinkedList<Integer> output;
        private boolean finished;
        
        public IntcodeComputer(int[] memory) {
            this.memory = memory;
            this.ip = 0;
            this.input = new LinkedList<>();
            this.output = new LinkedList<>();
            this.finished = false;
        }
        
        public void executeInstruction() {
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
                    this.finished = true;
                    break;
                default:
                    System.err.println("ERROR");
                    break;
            }
        }
        
        public boolean hasFinished() {
            return finished;
        }
        
        public boolean hasOutputAvailable() {
            return this.output.size() > 0;
        }
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
            
            int maxSignal = Integer.MIN_VALUE;
            int settingStart = 5;
            int settingEnd = 9;
            for (int a = settingStart; a <= settingEnd; a++) {
                for (int b = settingStart; b <= settingEnd; b++) {
                    if (b == a)
                        continue;
                    for (int c = settingStart; c <= settingEnd; c++) {
                        if (c == b || c == a)
                            continue;
                        for (int d = settingStart; d <= settingEnd; d++) {
                            if (d == c || d == b || d == a)
                                continue;
                            for (int e = settingStart; e <= settingEnd; e++) {
                                if (e == d || e == c || e == b || e == a)
                                    continue;
                                
                                IntcodeComputer[] computers = new IntcodeComputer[5];
                                for (int i = 0; i < computers.length; i++) {
                                    computers[i] = new IntcodeComputer(Arrays.copyOf(MEMORY, MEMORY.length));
                                }
                                
                                int[] settings = new int[] {a, b, c, d, e};
                                for (int i = 0; i < computers.length; i++) {
                                    computers[i].input.add(settings[i]);
                                }
                                computers[0].input.add(0);
                                int signal = -1;
                                while (!computers[4].hasFinished()) {
                                    for (int i = 0; i < computers.length; i++) {
                                        while (!computers[i].hasFinished() && !computers[i].hasOutputAvailable()) {
                                            computers[i].executeInstruction();
                                        }
                                        if (computers[i].hasOutputAvailable()) {
                                            int result = computers[i].output.pop();
                                            computers[(i + 1) % computers.length].input.add(result);
                                            if (i == 4) {
                                                signal = result;
                                            }
                                        }
                                    }
                                }
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

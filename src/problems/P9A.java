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
public class P9A extends util.Problem {
    
    private class IntcodeComputer {
        private HashMap<Long, Long> memory;
        private long ip;
        private long base;
        protected LinkedList<Long> input;
        protected LinkedList<Long> output;
        private boolean finished;
        
        private static final int OP_ADDITION = 1;
        private static final int OP_PRODUCT = 2;
        private static final int OP_INPUT = 3;
        private static final int OP_OUTPUT = 4;
        private static final int OP_BRANCH_IF_TRUE = 5;
        private static final int OP_BRANCH_IF_FALSE = 6;
        private static final int OP_LESS_THAN = 7;
        private static final int OP_EQUALS = 8;
        private static final int OP_ADJUST_BASE = 9;
        private static final int OP_HALT = 99;
        
        private static final int MODE_POSITION = 0;
        private static final int MODE_IMMEDIATE = 1;
        private static final int MODE_RELATIVE = 2;
        
        public IntcodeComputer(long[] memory) {
            this.memory = new HashMap<>();
            for (int i = 0; i < memory.length; i++) {
                this.memory.put((long)i, memory[i]);
            }
            this.ip = 0;
            this.base = 0;
            this.input = new LinkedList<>();
            this.output = new LinkedList<>();
            this.finished = false;
        }
        
        private int obtainOpcode(long opcodeAndParameters) {
            return (int) opcodeAndParameters % 100;
        }
        
        private int[] obtainParameterModes(long opcodeAndParameters, int numParameters) {
            int parameterModes = (int) opcodeAndParameters / 100;
            int[] result = new int[numParameters];
            for (int i = 0; i < numParameters; i++) {
                result[i] = parameterModes % 10;
                parameterModes /= 10;
            }
            return result;
        }
        
        private long[] obtainParameters(int numParameters) {
            long[] parameters = new long[numParameters];
            for (int i = 0; i < numParameters; i++) {
                parameters[i] = memory.get(ip++);
            }
            return parameters;
        }
        
        private long load(long parameter, int parameterMode) {
            if (parameterMode == MODE_IMMEDIATE) {
                return parameter;
            } else {
                long base;
                if (parameterMode == MODE_RELATIVE) {
                    base = this.base;
                } else {
                    base = 0;
                }
                Long value = this.memory.get(base + parameter);
                if (value != null) {
                    return value;
                } else {
                    return 0;
                }
            }
        }
        
        private void store(long parameter, long parameterMode, long value) {
            if (parameterMode == MODE_IMMEDIATE) {
                System.out.println("Storing with parameterMode immediate");
            } else {
                long base;
                if (parameterMode == MODE_RELATIVE) {
                    base = this.base;
                } else {
                    base = 0;
                }
                this.memory.put(base + parameter, value);
            }
        }
        
        public void executeInstruction() {
            long opcodeAndParameters = memory.get(ip++);
            int opcode = obtainOpcode(opcodeAndParameters);
            switch (opcode) {
                case OP_ADDITION: {
                    int[] parameterModes = obtainParameterModes(opcodeAndParameters, 3);
                    long[] parameters = obtainParameters(3);
                    long a, b, c;
                    a = load(parameters[0], parameterModes[0]);
                    b = load(parameters[1], parameterModes[1]);
                    c = a + b;
                    store(parameters[2], parameterModes[2], c);
                    break;
                }
                case OP_PRODUCT: {
                    int[] parameterModes = obtainParameterModes(opcodeAndParameters, 3);
                    long[] parameters = obtainParameters(3);
                    long a, b, c;
                    a = load(parameters[0], parameterModes[0]);
                    b = load(parameters[1], parameterModes[1]);
                    c = a * b;
                    store(parameters[2], parameterModes[2], c);
                    break;
                }
                case OP_INPUT: {
                    int[] parameterModes = obtainParameterModes(opcodeAndParameters, 1);
                    long[] parameters = obtainParameters(1);
                    long value = input.pop();
                    store(parameters[0], parameterModes[0], value);
                    break;
                }
                case OP_OUTPUT: {
                    int[] parameterModes = obtainParameterModes(opcodeAndParameters, 1);
                    long[] parameters = obtainParameters(1);
                    long value = load(parameters[0], parameterModes[0]);
                    output.add(value);
                    break;
                }
                case OP_BRANCH_IF_TRUE: {
                    int[] parameterModes = obtainParameterModes(opcodeAndParameters, 2);
                    long[] parameters = obtainParameters(2);
                    long a, b;
                    a = load(parameters[0], parameterModes[0]);
                    b = load(parameters[1], parameterModes[1]);
                    if (a != 0) {
                        ip = b;
                    }
                    break;
                }
                case OP_BRANCH_IF_FALSE: {
                    int[] parameterModes = obtainParameterModes(opcodeAndParameters, 2);
                    long[] parameters = obtainParameters(2);
                    long a, b;
                    a = load(parameters[0], parameterModes[0]);
                    b = load(parameters[1], parameterModes[1]);
                    if (a == 0) {
                        ip = b;
                    }
                    break;
                }
                case OP_LESS_THAN: {
                    int[] parameterModes = obtainParameterModes(opcodeAndParameters, 3);
                    long[] parameters = obtainParameters(3);
                    long a, b;
                    a = load(parameters[0], parameterModes[0]);
                    b = load(parameters[1], parameterModes[1]);
                    store(parameters[2], parameterModes[2], a < b ? 1 : 0);
                    break;
                }
                case OP_EQUALS: {
                    int[] parameterModes = obtainParameterModes(opcodeAndParameters, 3);
                    long[] parameters = obtainParameters(3);
                    long a, b;
                    a = load(parameters[0], parameterModes[0]);
                    b = load(parameters[1], parameterModes[1]);
                    store(parameters[2], parameterModes[2], a == b ? 1 : 0);
                    break;
                }
                case OP_ADJUST_BASE: {
                    int[] parameterModes = obtainParameterModes(opcodeAndParameters, 1);
                    long[] parameters = obtainParameters(1);
                    long value = load(parameters[0], parameterModes[0]);
                    this.base += value;
                    break;
                }
                case OP_HALT: 
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
            long[] MEMORY = new long[split.length];
            for (int i = 0; i < MEMORY.length; i++) {
                MEMORY[i] = Long.parseLong(split[i]);
            }
            
            IntcodeComputer computer = new IntcodeComputer(MEMORY);
            computer.input.add((long)1);
            
            while (!computer.hasFinished()) {
                while (!computer.hasFinished() && !computer.hasOutputAvailable()) {
                    computer.executeInstruction();
                }
                if (computer.hasOutputAvailable()) {
                    System.out.println(computer.output.pop());
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

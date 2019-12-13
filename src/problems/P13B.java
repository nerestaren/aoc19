package problems;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Antoni
 */
public class P13B extends util.Problem {

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
                this.memory.put((long) i, memory[i]);
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

        public void enterValue(long value) {
            this.input.add(value);
        }

        public Long obtainValue() {
            while (!this.hasFinished() && !this.hasOutputAvailable()) {
                this.executeInstruction();
            }
            if (this.hasOutputAvailable()) {
                return this.output.pop();
            } else {
                return null;
            }
        }
    }

    private class Point {

        private int x;
        private int y;
        private double angle;

        public Point() {
            this.x = 0;
            this.y = 0;
            this.angle = 0;
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
            this.angle = 0;
        }

        public Point(int x, int y, double angle) {
            this.x = x;
            this.y = y;
            this.angle = angle;
        }

        @Override
        public boolean equals(Object b) {
            if (!(b instanceof Point)) {
                return false;
            }
            return this.x == ((Point) b).x && this.y == ((Point) b).y;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 59 * hash + this.x;
            hash = 59 * hash + this.y;
            return hash;
        }

        public void turnLeft() {
            this.angle -= Math.PI / 2;
        }

        public void turnRight() {
            this.angle += Math.PI / 2;
        }

        public void forward() {
            this.x += (int) Math.sin(this.angle);
            this.y += -((int) Math.cos(this.angle));
        }

        @Override
        public String toString() {
            return "(" + this.x + ", " + this.y + ") " + this.angle * 180 / Math.PI;
        }

        public Point copy() {
            return new Point(this.x, this.y, this.angle);
        }
    }

    public int blocksRemaining(HashMap<Point, Integer> display) {
        int total = 0;
        for (Integer id : display.values()) {
            if (id == ID_BLOCK) {
                total++;
            }
        }
        return total;
    }

    public void show(HashMap<Point, Integer> display, Point from, Point to, int score) throws InterruptedException {
        StringBuilder sb = new StringBuilder();
        sb.append("\033[H\033[2J");
        sb.append(String.format("Score: %6d\tBlocks remaining: %6d\n\n", score, blocksRemaining(display)));
        for (int y = from.y; y <= to.y; y++) {
            for (int x = from.x; x <= to.x; x++) {
                Integer id = display.get(new Point(x, y));
                if (id == null) {
                    sb.append(0);
                } else {
                    sb.append(CHARS[id]);
                }
            }
            sb.append('\n');
        }
        System.out.println(sb);
        Thread.sleep(5);
    }

    private static final int ID_EMPTY = 0;
    private static final int ID_WALL = 1;
    private static final int ID_BLOCK = 2;
    private static final int ID_PADDLE = 3;
    private static final int ID_BALL = 4;

    private static final char[] CHARS = new char[]{' ', 'X', 'B', '_', 'O'};

    public void run() {
        try {
            List<String> input = readInput();

            String inputLine = input.get(0);
            String[] split = inputLine.split(",");
            long[] MEMORY = new long[split.length];
            for (int i = 0; i < MEMORY.length; i++) {
                MEMORY[i] = Long.parseLong(split[i]);
            }

            MEMORY[0] = 2;

            IntcodeComputer computer = new IntcodeComputer(MEMORY);
            HashMap<Point, Integer> display = new HashMap<>();

            Point from = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
            Point to = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

            Point lastPaddlePosition = null;

            int score = 0;
            computer.enterValue(0);
            while (!computer.hasFinished()) {
                try {
                    long[] values = new long[3];
                    for (int i = 0; i < values.length; i++) {
                        values[i] = computer.obtainValue();
                    }

                    if (values[0] == -1 && values[1] == 0) {
                        score = (int) values[2];
                    } else {
                        Point position = new Point((int) values[0], (int) values[1]);
                        int id = (int) values[2];

                        display.put(position, id);

                        if (id == ID_BALL && lastPaddlePosition != null) {
                            if (position.x < lastPaddlePosition.x) {
                                computer.enterValue(-1);
                            } else if (position.x > lastPaddlePosition.x) {
                                computer.enterValue(+1);
                            } else {
                                computer.enterValue(0);
                            }
                        } else if (id == ID_PADDLE) {
                            lastPaddlePosition = position;
                            show(display, from, to, score);
                        }

                        if (position.x > to.x) {
                            to.x = position.x;
                        }
                        if (position.x < from.x) {
                            from.x = position.x;
                        }
                        if (position.y > to.y) {
                            to.y = position.y;
                        }
                        if (position.y < from.y) {
                            from.y = position.y;
                        }
                    }
                } catch (NullPointerException e) {

                }
            }

            System.out.println("Score: " + score);

            System.out.println("Entries painted: " + display.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

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
public class P4B extends util.Problem {
    
    private static boolean isValidPassword(int pass) {
        int exp = 6;
        int previousDigit = pass / (int) (Math.pow(10, exp - 1));
        pass = pass % (int) (Math.pow(10, exp - 1));
        int digit;
        exp--;
        
        int numberAdjacent = 1;
        boolean exactlyTwoAdjacentFound = false;
        
        while (exp > 0) {
            digit = pass / (int) (Math.pow(10, exp - 1));
            pass = pass % (int) (Math.pow(10, exp - 1));
            if (digit < previousDigit) {
                return false;
            }
            if (previousDigit == digit) {
                numberAdjacent++;
            } else {
                if (numberAdjacent == 2) {
                    exactlyTwoAdjacentFound = true;
                }
                numberAdjacent = 1;
            }
            previousDigit = digit;
            exp--;
        }
        
        if (numberAdjacent == 2) {
            exactlyTwoAdjacentFound = true;
        }
        
        return exactlyTwoAdjacentFound;
    }
    
    public void run() {
        try {
            List<String> input = readInput();

            String line = input.get(0);
            String[] split = line.split("-");
            
            int min, max;
            min = Integer.parseInt(split[0]);
            max = Integer.parseInt(split[1]);
            
            System.out.println("112233 " + isValidPassword(112233));
            System.out.println("123444 " + isValidPassword(123444));
            System.out.println("111122 " + isValidPassword(111122));
            
            int validPasswords = 0;
                        
            for (int pass = min; pass <= max; pass++) {
                if (isValidPassword(pass)) {
                    validPasswords++;
                }
            }
            
            System.out.println("Number of valid passwords: " + validPasswords);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

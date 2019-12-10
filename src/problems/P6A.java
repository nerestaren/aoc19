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
public class P6A extends util.Problem {
        
    public void run() {
        try {
            List<String> input = readInput();

            HashMap<String, String> orbits = new HashMap<String, String>();
            
            for (String s : input) {
                String[] split = s.split("\\)");
                String orbitee = split[0];
                String orbitant = split[1];
                
                orbits.put(orbitant, orbitee);
            }
            
            int sumChainLength = 0;
            int i = 0;
            for (String orbitant : orbits.keySet()) {
                int length = 1;
                String orbitee = orbits.get(orbitant);
                while (!orbitee.equals("COM")) {
                    length++;
                    orbitant = orbitee;
                    orbitee = orbits.get(orbitant);
                }
                sumChainLength += length;
                i++;
                System.out.println(i + " / " + orbits.size());
            }
            
            System.out.println("Suma de les òrbites: " + sumChainLength);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

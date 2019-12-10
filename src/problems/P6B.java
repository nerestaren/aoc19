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
public class P6B extends util.Problem {
        
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
            
            String planetFromYou = orbits.get("YOU");
            String planetFromSanta = null;
            int fromYouToMiddle, fromMiddleToSanta;
            fromYouToMiddle = 0;
            fromMiddleToSanta = 0;
            while (!planetFromYou.equals("COM") && !planetFromYou.equals(planetFromSanta)) {
                fromMiddleToSanta = 0;
                planetFromSanta = orbits.get("SAN");
                while (!planetFromSanta.equals("COM") && !planetFromYou.equals(planetFromSanta)) {
                    planetFromSanta = orbits.get(planetFromSanta);
                    fromMiddleToSanta++;
                }
                if (!planetFromYou.equals(planetFromSanta)) {
                    planetFromYou = orbits.get(planetFromYou);
                    fromYouToMiddle++;
                }
            }
            
            System.out.println("From YOU to SAN: " + (fromYouToMiddle + fromMiddleToSanta));
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

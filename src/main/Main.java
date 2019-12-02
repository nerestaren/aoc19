package main;

import java.lang.reflect.Constructor;
import util.LT;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Antoni
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        System.out.println("Advent of Code 2019");
        System.out.println("----------------------------------------");
        String pName;
        do {
            do {
                System.out.println("Escriu el nom d'un problema (P1A, P1B, P2A...), o surt per sortir.");
                char[] line = LT.readLine();
                pName = new String(line).toUpperCase();
            } while (pName.isEmpty() || !(pName.equals("SURT") || pName.matches("P\\d+[AB]")));
            if (pName.equals("SURT")) {
                System.out.println("Fins aviat!");
            } else {
                Class c = Class.forName("problems." + pName);
                Constructor constructor = c.getConstructor();
                Object p = constructor.newInstance();
                Method m = c.getMethod("run");
                m.invoke(p);
            }
        } while (!pName.equals("SURT"));
    }
    
}

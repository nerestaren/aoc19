/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author Antoni
 */
public abstract class Problem {
    protected String getProblemName() {
        return this.getClass().getSimpleName();
    }
    
    protected List<String> readInput() throws IOException {
        String pName = getProblemName().toLowerCase().substring(0, 2);
        return Files.readAllLines(Paths.get("input/" + pName + "/input"));
    }
    
    public abstract void run();
}
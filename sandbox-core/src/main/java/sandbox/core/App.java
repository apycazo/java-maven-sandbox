package sandbox.core;

import sandbox.core.experiments.CommaDelimitedStringToArray;
import sandbox.core.experiments.J8FunctionalInterfaces;

public class App {
    
    public static void main (String [] args) 
    {
        System.out.println("Ready");
        
        Experiment [] experiments = {
            new J8FunctionalInterfaces(),
            new CommaDelimitedStringToArray()
        };
        
        for (Experiment experiment : experiments) {
            System.out.printf("%n> Experiment:  [%s]%n", experiment.getClass().getSimpleName());
            experiment.tryout();
        }
        
    }
}



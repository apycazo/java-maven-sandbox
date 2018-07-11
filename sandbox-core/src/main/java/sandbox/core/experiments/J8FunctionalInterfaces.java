package sandbox.core.experiments;

import sandbox.core.Experiment;

public class J8FunctionalInterfaces implements Experiment {
    
    public interface Named {
        String getName();
    }

    @Override
    public void tryout() {
     
        Named john = () -> "john";
        Named jane = () -> "jane";
        
        print("The name is: '{}'.", john.getName());
        print("The name is: '{}'.", jane.getName());        
    }
    
}

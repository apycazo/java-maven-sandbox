package sandbox.core;

public interface Experiment {
    
    void tryout();
    
    default void print(String text, Object ... args) 
    {
        if (args != null) {
            for (Object arg : args) {
                text = text.replaceFirst("\\{\\}", arg.toString());
            }
        }
        
        System.out.println(text);
    }
}

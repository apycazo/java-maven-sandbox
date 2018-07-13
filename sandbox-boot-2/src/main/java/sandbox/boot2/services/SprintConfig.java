package sandbox.boot2.services;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sprint")
public class SprintConfig {
    
    private String name;
    private int number;
    
    public void setName (String name) { this.name = name; }
    public String getName () { return name; }
    
    public void setNumber (int number) { this.number = number; }
    public int getNumber () { return number; }
}

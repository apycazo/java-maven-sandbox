package sandbox.springjersey;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class RestConfig extends ResourceConfig {
    
    public RestConfig () {
        packages("sandbox.springjersey");
    }
    
    /*
    public Set<Class<?>> getClasses() {
        
        Set<Class<?>> resources = new HashSet<>();
        resources.add(EchoController.class);
        
        return resources;
    }
    */
}

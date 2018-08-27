package sandbox.springjersey;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class RestConfig extends ResourceConfig {
    
    public RestConfig () {
        packages("sandbox.springjersey");
    }
}

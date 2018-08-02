package sandbox.springjersey;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("echo")
public class EchoController {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get () {
        
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", Instant.now().toEpochMilli());
        return map;        
    }    
}

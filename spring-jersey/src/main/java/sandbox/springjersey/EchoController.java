package sandbox.springjersey;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

@Path("echo")
public class EchoController {
    
    @Autowired
    private DummyService dummyService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get () {
        
        Map<String, Object> map = dummyService.generateMapResponse();
        map.put("src", "echoController.get");
        return map;        
    }    
}

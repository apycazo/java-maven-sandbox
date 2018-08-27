package sandbox.springjersey;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class DummyService {
    
    public Map<String, Object> generateMapResponse () {
        
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", Instant.now().toEpochMilli());
        return map;        
    } 
}

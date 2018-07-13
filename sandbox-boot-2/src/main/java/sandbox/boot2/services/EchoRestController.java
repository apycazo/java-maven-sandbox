package sandbox.boot2.services;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoRestController {
    
    @Autowired
    private SprintConfig sprintConfig;
    
    @GetMapping("/echo")
    public Map<String, Object> echo (HttpServletRequest request) 
    {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("outcome","success");
        map.put("timestamp", Instant.now().toEpochMilli());
        map.put("url", request.getRequestURL().toString());
        map.put("query", request.getQueryString());
        map.put("sprint.name", sprintConfig.getName());
        map.put("sprint.number", sprintConfig.getNumber());
        
        return map;
    }
}

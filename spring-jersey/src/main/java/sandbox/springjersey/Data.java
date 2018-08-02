package sandbox.springjersey;

public class Data {
    
    private String name;
    private Long id;
    private Object payload;
    
    public String getName () { return name; }
    public void setName (String name) { this.name = name; }
    
    public Long getId () { return id; }
    public void setId (Long id) { this.id = id; }
    
    public Object getPayload () { return payload; }
    public void setPayload (Object payload) { this.payload = payload; }
}

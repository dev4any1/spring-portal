package net.dev4any1.pojo;
import java.util.Date;

public class Publisher {
	private Long id;
    private User user;
    private String name;
    
    @Override
	public String toString() {
		return "Publisher [id=" + id + ", user=" + user + ", name=" + name + "]";
	}

	public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
        
    public User getUser() {
    	return user;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }
}

package net.dev4any1.pojo;

import java.io.Serializable;


public class Category implements Serializable {
	private static final long serialVersionUID = Category.class.getName().hashCode();
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	@Override
	public String toString() {
		return "Category [name=" + name + "]";
	}
	
}

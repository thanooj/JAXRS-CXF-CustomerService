package com.jaxrs.cxf.rest.bo;

import org.springframework.stereotype.Component;

@Component
public class Customer {

	private Integer id;
	private String name;
	private String location;

	public Customer() {
	}

	public Customer(Integer id, String name, String location) {
		this.id = id;
		this.name = name;
		this.location = location;

	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", location=" + location + "]";
	}

}

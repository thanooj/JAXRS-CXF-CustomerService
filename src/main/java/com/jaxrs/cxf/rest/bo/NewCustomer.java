package com.jaxrs.cxf.rest.bo;

import org.springframework.stereotype.Component;

@Component
public class NewCustomer {

	private String name;
	private String location;

	public NewCustomer() {
	}

	public NewCustomer(String name, String location) {
		this.name = name;
		this.location = location;

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

	@Override
	public String toString() {
		return "Customer [name=" + name + ", location=" + location + "]";
	}

}

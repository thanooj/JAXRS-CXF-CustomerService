package com.jaxrs.cxf.rest.dao;

import java.util.List;

import javax.ws.rs.core.Response;

import com.jaxrs.cxf.rest.bo.Customer;
import com.jaxrs.cxf.rest.bo.NewCustomer;

public interface CustomerDAO {
	
	public Customer getCustomerByID(Integer id);
	
	public List<Customer> getCustomers();
	
	public Response createCustomer(final List<NewCustomer> newCustomer);

}

package com.jaxrs.cxf.rest.dao;

import java.util.List;

import com.jaxrs.cxf.rest.bo.Customer;
import com.jaxrs.cxf.rest.bo.NewCustomer;

public interface CustomerDAO {

	public Customer getCustomerByID(Integer id);

	public List<Customer> getCustomers();

	public int[] createCustomer(final List<NewCustomer> newCustomer);

	public int updateCustomer(final Customer customer);

	public int deleteCustomerByID(Integer id);

}

package com.jaxrs.cxf.rest.util;

import java.util.List;

import com.jaxrs.cxf.rest.bo.Customer;
import com.jaxrs.cxf.rest.bo.NewCustomer;

public interface TransactionBo {

	Customer getCustomerByID(Integer id);

	List<Customer> getCustomers();

	String createCustomer(List<NewCustomer> newCustomer);

	String updateCustomer(Customer customer);

	String deleteCustomerByID(Integer id);

}
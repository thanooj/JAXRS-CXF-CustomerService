package com.jaxrs.cxf.rest.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jaxrs.cxf.rest.bo.Customer;
import com.jaxrs.cxf.rest.bo.NewCustomer;

@Component("transactionBo")
public class TransactionBoImpl implements TransactionBo {

	public Customer getCustomerByID(Integer id) {
		return CustomerFactory.getCustomerByID(id);
	}

	@Override
	public List<Customer> getCustomers() {
		return new ArrayList<Customer>(CustomerFactory.getCustomers().values());
	}

	@Override
	public String createCustomer(List<NewCustomer> newCustomers) {
		String responseStr = null;
		int size = CustomerFactory.getCustomers().size();
		Map<Integer, Customer> customersMap = new HashMap<>();
		for (NewCustomer newCustomer : newCustomers) {
			Integer id = size + 1;
			customersMap.put(id, CustomerFactory.newCustomer(id, newCustomer.getName(), newCustomer.getLocation()));
		}
		CustomerFactory.getCustomers().putAll(customersMap);
		if (newCustomers.size() + size == CustomerFactory.getCustomers().size()) {
			responseStr = "All new customers are successfully created.";
		} else if (newCustomers.size() + size > CustomerFactory.getCustomers().size()) {
			responseStr = "NOT all new customers are successfully created.";
		} else {
			responseStr = "new customers creation was NOT successful.";
		}
		return responseStr;
	}

	@Override
	public String updateCustomer(Customer customer) {
		String responseStr = null;
		if (customer != null && customer.getId() > 0) {
			Customer customerFromMap = CustomerFactory.getCustomers().get(customer.getId());
			if (customerFromMap != null) {
				CustomerFactory.getCustomers().put(customer.getId(), customer);
				responseStr = "A customer details has successfully updated.";
			} else {
				CustomerFactory.getCustomers().put(customer.getId(), customer);
				responseStr = "A new customers creation was NOT successful.";
			}
		} else {
			responseStr = "Please provide valid request.";
		}
		return responseStr;
	}

	@Override
	public String deleteCustomerByID(Integer id) {
		String msg = "Customer NOT FOUND.";
		boolean deleteCustomerByID = CustomerFactory.deleteCustomerByID(id);
		if(deleteCustomerByID){
			msg = "Customer has been deleted successfully.";
		}
		return msg;
	}

}
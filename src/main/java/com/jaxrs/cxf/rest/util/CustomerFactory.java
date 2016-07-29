package com.jaxrs.cxf.rest.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jaxrs.cxf.rest.bo.Customer;

@Component("customerFactory")
public class CustomerFactory {
	private static final Logger logger = LoggerFactory.getLogger(CustomerFactory.class);

	private static Map<Integer, Customer> customersMap = new HashMap<>();

	static {
		logger.debug("constructing customer's Map with ID as key and Entity as value.");
		customersMap.put(1, new Customer(1, "ram", "ayodhya"));
		customersMap.put(2, new Customer(2, "seeta", "midhila"));
		customersMap.put(3, new Customer(3, "lakshman", "ayodhya"));
	}

	public static Customer newCustomer(Integer id, String name, String location) {
		return new Customer(id, name, location);
	}

	public static Map<Integer, Customer> getCustomers() {
		return customersMap;
	}

	public static Customer getCustomerByID(Integer id) {
		return customersMap.get(id);
	}

	public static boolean deleteCustomerByID(Integer id) {
		boolean flag = false;
		Customer customer = customersMap.get(id);
		if (customer != null) {
			customersMap.remove(id);
			flag = true;
		}
		return flag;
	}

}

package com.jaxrs.cxf.rest.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jaxrs.cxf.rest.bo.Customer;
import com.jaxrs.cxf.rest.bo.NewCustomer;

@Component("transactionBo")
public class TransactionBoImpl implements TransactionBo {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionBoImpl.class);

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
		int sizeBeforeCreateNewCustomer = CustomerFactory.getCustomers().size();
		for (NewCustomer newCustomer : newCustomers) {
			logger.info(newCustomer.toString());
			int size = CustomerFactory.getCustomers().size();
			logger.info("CustomerFactory.getCustomers().size()" + size);
			Integer id = size + 1;
			CustomerFactory.getCustomers().put(id, CustomerFactory.newCustomer(id, newCustomer.getName(), newCustomer.getLocation()));
		}
		if (newCustomers.size() + sizeBeforeCreateNewCustomer == CustomerFactory.getCustomers().size()) {
			responseStr = "All new customers are successfully created.";
		} else if (newCustomers.size() + sizeBeforeCreateNewCustomer > CustomerFactory.getCustomers().size()) {
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
		}else{
			msg = "Customer NOT FOUND.";
		}
		return msg;
	}

}
package com.jaxrs.cxf.rest.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaxrs.cxf.rest.bo.Customer;
import com.jaxrs.cxf.rest.bo.Greeting;
import com.jaxrs.cxf.rest.bo.NewCustomer;
import com.jaxrs.cxf.rest.dao.CustomerDAOImpl;

@Component("customerService")
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private CustomerDAOImpl customerDAO;

	@Override
	public Greeting greeting(String name) {
		logger.info("GreetingController.greeting()");
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@Override
	public Response getCustomerByID(Integer id) {
		Response response = null;
		logger.info("CustomerService.getCustomerByID() : " + id);
		Customer customer = customerDAO.getCustomerByID(id);
		if (customer != null) {
			response = Response.status(Status.OK).entity(customer).build();
		} else {
			response = Response.status(Status.NOT_FOUND).entity(null).build();
		}
		return response;
	}

	@Override
	public Response getCustomers() {
		Response response = null;
		logger.info("CustomerService.getCustomers()");
		List<Customer> customers = customerDAO.getCustomers();
		if (customers != null && !customers.isEmpty()) {
			response = Response.status(Status.OK).entity(customers).build();
		} else {
			response = Response.status(Status.NOT_FOUND).entity(null).build();
		}
		return response;
	}

	@Override
	public Response createCustomer(final List<NewCustomer> newCustomer) {
		Response response = null;
		String msg = "BAD REQUEST!";
		if (newCustomer != null && !newCustomer.isEmpty()) {
			logger.info("newCustomer != null && newCustomer.isEmpty() == false  :: "
					+ (newCustomer != null && newCustomer.isEmpty() == false));
			int[] createCustomer = customerDAO.createCustomer(newCustomer);
			if (createCustomer.length == newCustomer.size()) {
				msg = "All new customers are successfully created.";
				response = Response.status(Status.CREATED).entity(msg).build();
			} else if (createCustomer.length < newCustomer.size()) {
				msg = "NOT all new customers are successfully created.";
				response = Response.status(Status.CREATED).entity(msg).build();
			} else {
				response = Response.status(Status.BAD_REQUEST).entity(msg).build();
			}
		} else {
			logger.info("else");
			response = Response.status(Status.BAD_REQUEST).entity(msg).build();
		}
		return response;
	}

	@Override
	public Response updateCustomer(Customer customer) {
		Response response = null;
		String msg = "BAD REQUEST";
		if (customer != null && customer.getId() != null) {
			logger.info("newCustomer != null");
			int updateCustomer = customerDAO.updateCustomer(customer);
			if (updateCustomer > 0) {
				msg = "A customer has been updated/created successfully.";
				response = Response.status(Status.CREATED).entity(msg).build();
			} else {
				response = Response.status(Status.BAD_REQUEST).entity(msg).build();
			}
		} else {
			logger.info("else");
			response = Response.status(Status.BAD_REQUEST).entity(msg).build();
		}
		return response;
	}

	@Override
	public Response deleteCustomerByID(Integer id) {
		Response response = null;
		String msg = "BAD REQUEST";
		logger.info("CustomerService.deleteCustomerByID() : " + id);
		int deleteCustomerByID = customerDAO.deleteCustomerByID(id);
		if (deleteCustomerByID > 0) {
			msg = "A customer has been deleted successfully.";
			response = Response.status(Status.OK).entity(msg).build();
		} else {
			response = Response.status(Status.NOT_FOUND).entity(msg).build();
		}
		return response;
	}
}

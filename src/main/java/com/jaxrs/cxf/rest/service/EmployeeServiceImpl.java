package com.jaxrs.cxf.rest.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaxrs.cxf.rest.bo.Employee;
import com.jaxrs.cxf.rest.bo.Greeting;
import com.jaxrs.cxf.rest.bo.NewEmployee;
import com.jaxrs.cxf.rest.dao.EmployeeDAOImpl;

@Component("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private EmployeeDAOImpl employeeDAO;

	@Override
	public Greeting greeting(String name) {
		logger.info("GreetingController.greeting()");
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@Override
	public Response getEmployeeByID(Integer id) {
		Response response = null;
		logger.info("EmployeeService.getEmployeeByID() : " + id);
		Employee employee = employeeDAO.getEmployeeByID(id);
		if (employee != null) {
			response = Response.status(Status.OK).entity(employee).build();
		} else {
			response = Response.status(Status.NOT_FOUND).entity(null).build();
		}
		return response;
	}

	@Override
	public Response getEmployees() {
		Response response = null;
		logger.info("EmployeeService.getEmployees()");
		List<Employee> employees = employeeDAO.getEmployees();
		if (employees != null && !employees.isEmpty()) {
			response = Response.status(Status.OK).entity(employees).build();
		} else {
			response = Response.status(Status.NOT_FOUND).entity(null).build();
		}
		return response;
	}

	@Override
	public Response createEmployee(final List<NewEmployee> newEmployee) {
		Response response = null;
		String msg = "BAD REQUEST!";
		if (newEmployee != null && !newEmployee.isEmpty()) {
			logger.info("newEmployee != null && newEmployee.isEmpty() == false  :: "
					+ (newEmployee != null && newEmployee.isEmpty() == false));
			int[] createEmployee = employeeDAO.createEmployee(newEmployee);
			if (createEmployee.length == newEmployee.size()) {
				msg = "All new employees are successfully created.";
				response = Response.status(Status.CREATED).entity(msg).build();
			} else if (createEmployee.length < newEmployee.size()) {
				msg = "NOT all new employees are successfully created.";
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
	public Response updateEmployee(Employee employee) {
		Response response = null;
		String msg = "BAD REQUEST";
		if (employee != null && employee.getId() != null) {
			logger.info("newEmployee != null");
			int updateEmployee = employeeDAO.updateEmployee(employee);
			if (updateEmployee > 0) {
				msg = "A employee has been updated/created successfully.";
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
	public Response deleteEmployeeByID(Integer id) {
		Response response = null;
		String msg = "BAD REQUEST";
		logger.info("EmployeeService.deleteEmployeeByID() : " + id);
		int deleteEmployeeByID = employeeDAO.deleteEmployeeByID(id);
		if (deleteEmployeeByID > 0) {
			msg = "A employee has been deleted successfully.";
			response = Response.status(Status.OK).entity(msg).build();
		} else {
			response = Response.status(Status.NOT_FOUND).entity(msg).build();
		}
		return response;
	}
}

package com.jaxrs.cxf.rest.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jaxrs.cxf.rest.bo.Employee;
import com.jaxrs.cxf.rest.bo.Greeting;
import com.jaxrs.cxf.rest.bo.NewEmployee;

@Path("/employee")
public interface EmployeeService {
	
	@GET
	@Path("/greeting/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Greeting greeting(@PathParam("name") String name);
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	@Path("/getEmployeeByID/{id}")
	public Response getEmployeeByID(@PathParam("id") Integer id);
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON/*, MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML */})
	@Path("/getEmployees")
	public Response getEmployees();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/createEmployee")
	public Response createEmployee(final List<NewEmployee> newEmployee);
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateEmployee")
	public Response updateEmployee(final Employee employee);
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteEmployeeByID/{id}")
	public Response deleteEmployeeByID(@PathParam("id") Integer id);
	
}

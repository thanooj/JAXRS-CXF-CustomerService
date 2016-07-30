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

import com.jaxrs.cxf.rest.bo.Customer;
import com.jaxrs.cxf.rest.bo.Greeting;
import com.jaxrs.cxf.rest.bo.NewCustomer;

@Path("/customer")
public interface CustomerService {
	
	@GET
	@Path("/greeting/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Greeting greeting(@PathParam("name") String name);
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	@Path("/getCustomerByID/{id}")
	public Response getCustomerByID(@PathParam("id") Integer id);
	
	/*@GET
	@Produces({ MediaType.APPLICATION_JSON})
	@Path("/getCustomerByID/{id}")
	public Response getCustomerByID(@PathParam("id") Integer id);*/
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON/*, MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML */})
	@Path("/getCustomers")
	public Response getCustomers();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/createCustomer")
	public Response createCustomer(final List<NewCustomer> newCustomer);
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateCustomer")
	public Response updateCustomer(final Customer customer);
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteCustomerByID/{id}")
	public Response deleteCustomerByID(@PathParam("id") Integer id);
	
}

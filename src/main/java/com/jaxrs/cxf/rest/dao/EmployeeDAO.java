package com.jaxrs.cxf.rest.dao;

import java.util.List;

import com.jaxrs.cxf.rest.bo.Employee;
import com.jaxrs.cxf.rest.bo.NewEmployee;

public interface EmployeeDAO {

	public Employee getEmployeeByID(Integer id);

	public List<Employee> getEmployees();

	public int[] createEmployee(final List<NewEmployee> newEmployee);

	public int updateEmployee(final Employee Employee);

	public int deleteEmployeeByID(Integer id);

}

package com.jaxrs.cxf.rest.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Repository;

import com.jaxrs.cxf.rest.bo.Employee;
import com.jaxrs.cxf.rest.bo.EmployeeMapper;
import com.jaxrs.cxf.rest.bo.NewEmployee;

@Repository("employeeDAO")
public class EmployeeDAOImpl extends JdbcDaoSupport implements EmployeeDAO {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);

	@Autowired
	private DataSource employeeDataSource;

	@PostConstruct
	private void initialize() {
		logger.info("*START****EmployeeDAOImpl.initialize()*******");
		setDataSource(employeeDataSource);
		logger.info("CALLING statup() to clean up the data in employee database.");
		statup();
		logger.info("END statup() to clean up the data in employee database.");
		logger.info("*END****EmployeeDAOImpl.initialize()*******");
	}

	@Override
	public Employee getEmployeeByID(Integer id) {
		Employee employee = null;
		String SQL = "select * from employee.employee where id = ?";
		List<Employee> employees = getJdbcTemplate().query(SQL, new Object[] { id },
				new BeanPropertyRowMapper<Employee>(Employee.class));
		if (employees != null && (!employees.isEmpty() && employees.size() == 1)) {
			employee = employees.get(0);
		}
		return employee;
	}

	@Override
	public List<Employee> getEmployees() {
		String SQL = "select * from employee.employee";
		return getJdbcTemplate().query(SQL, new EmployeeMapper());
	}

	@Override
	public int[] createEmployee(final List<NewEmployee> newEmployees) {
		String SQL = "insert into employee.employee (name, location) values (?, ?)";
		return getJdbcTemplate().batchUpdate(SQL, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				NewEmployee newEmployee = newEmployees.get(i);
				ps.setString(1, newEmployee.getName());
				ps.setString(2, newEmployee.getLocation());
			}

			@Override
			public int getBatchSize() {
				return newEmployees.size();
			}
		});
	}

	@Override
	public int updateEmployee(Employee employee) {
		Employee employeeByID = getEmployeeByID(employee.getId());
		if (employeeByID != null) {
			String SQL = "update employee.employee set name = ?, location = ? where id = ?";
			return getJdbcTemplate().update(SQL,
					new Object[] { employee.getName(), employee.getLocation(), employee.getId() });
		} else {
			String SQL = "insert into employee.employee  (name, location) values (?, ?)";
			return getJdbcTemplate().update(SQL, new Object[] { employee.getName(), employee.getLocation() });
		}
	}

	@Override
	public int deleteEmployeeByID(Integer id) {
		String SQL = "delete from employee.employee where id = ?";
		return getJdbcTemplate().update(SQL, new Object[] { id });
	}

	public void dropTable() throws SQLException {
		String SQL = "drop table employee.employee";
		getJdbcTemplate().execute(SQL);
	}

	public void statup() {
		try {
			dropTable();
			logger.info("drop employee table sql statement has been successfully executed. --- in try block");
			ScriptUtils.executeSqlScript(getConnection(), new ClassPathResource("employee_db-schema.sql"));
			logger.info("create employee table sql statement has been successfully executed. --- in try block");
		} catch (Exception e) {
			ScriptUtils.executeSqlScript(getConnection(), new ClassPathResource("employee_db-schema.sql"));
			logger.info("create employee table sql statement has been successfully executed. --- in catch block");
		}
	}
}

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

import com.jaxrs.cxf.rest.bo.Customer;
import com.jaxrs.cxf.rest.bo.CustomerMapper;
import com.jaxrs.cxf.rest.bo.NewCustomer;

@Repository("customerDAO")
public class CustomerDAOImpl extends JdbcDaoSupport implements CustomerDAO {

	private static final Logger logger = LoggerFactory.getLogger(CustomerDAOImpl.class);

	@Autowired
	private DataSource customerDataSource;

	@PostConstruct
	private void initialize() {
		logger.info("*START****CustomerDAOImpl.initialize()*******");
		setDataSource(customerDataSource);
		logger.info("CALLING statup() to clean up the data in customer database.");
		statup();
		logger.info("END statup() to clean up the data in customer database.");
		logger.info("*END****CustomerDAOImpl.initialize()*******");
	}

	@Override
	public Customer getCustomerByID(Integer id) {
		Customer customer = null;
		String SQL = "select * from customer.customer where id = ?";
		List<Customer> customers = getJdbcTemplate().query(SQL, new Object[] { id },
				new BeanPropertyRowMapper<Customer>(Customer.class));
		if (customers != null && (!customers.isEmpty() && customers.size() == 1)) {
			customer = customers.get(0);
		}
		return customer;
	}

	@Override
	public List<Customer> getCustomers() {
		String SQL = "select * from customer.customer";
		return getJdbcTemplate().query(SQL, new CustomerMapper());
	}

	@Override
	public int[] createCustomer(final List<NewCustomer> newCustomers) {
		String SQL = "insert into customer.customer (name, location) values (?, ?)";
		return getJdbcTemplate().batchUpdate(SQL, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				NewCustomer newCustomer = newCustomers.get(i);
				ps.setString(1, newCustomer.getName());
				ps.setString(2, newCustomer.getLocation());
			}

			@Override
			public int getBatchSize() {
				return newCustomers.size();
			}
		});
	}

	@Override
	public int updateCustomer(Customer customer) {
		Customer customerByID = getCustomerByID(customer.getId());
		if (customerByID != null) {
			String SQL = "update customer.customer set name = ?, location = ? where id = ?";
			return getJdbcTemplate().update(SQL,
					new Object[] { customer.getName(), customer.getLocation(), customer.getId() });
		} else {
			String SQL = "insert into customer.customer  (name, location) values (?, ?)";
			return getJdbcTemplate().update(SQL, new Object[] { customer.getName(), customer.getLocation() });
		}
	}

	@Override
	public int deleteCustomerByID(Integer id) {
		String SQL = "delete from customer.customer where id = ?";
		return getJdbcTemplate().update(SQL, new Object[] { id });
	}

	public void dropTable() throws SQLException {
		String SQL = "drop table customer.customer";
		getJdbcTemplate().execute(SQL);
	}

	public void statup() {
		try {
			dropTable();
			logger.info("drop customer table sql statement has been successfully executed. --- in try block");
			ScriptUtils.executeSqlScript(getConnection(), new ClassPathResource("customer_db-schema.sql"));
			logger.info("create customer table sql statement has been successfully executed. --- in try block");
		} catch (Exception e) {
			ScriptUtils.executeSqlScript(getConnection(), new ClassPathResource("customer_db-schema.sql"));
			logger.info("create customer table sql statement has been successfully executed. --- in catch block");
		}
	}
}

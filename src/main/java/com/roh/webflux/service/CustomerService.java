package com.roh.webflux.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roh.webflux.dao.CustomerDao;
import com.roh.webflux.dto.Customer;

import reactor.core.publisher.Flux;

@Service
public class CustomerService {
	
	public static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);
	
	@Autowired
	private CustomerDao customerDao;
	
	public List<Customer>  loadAllCustomers() {
		long start = System.currentTimeMillis();
		List<Customer> customers = customerDao.getCustomers();
		long end = System.currentTimeMillis();
		LOG.error("Check logging at info level");
		System.out.println("Total time difference " + (end - start));
		return customers;
	}
	
	public Flux<Customer>  loadAllCustomersStream() {
		long start = System.currentTimeMillis();
		Flux<Customer> customers = customerDao.getCustomersStream();
		long end = System.currentTimeMillis();
		LOG.error("Check logging at info level flux");
		System.out.println("Total time difference " + (end - start));
		return customers;
	}

}

package com.roh.webflux.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roh.webflux.dto.Customer;
import com.roh.webflux.service.CustomerService;

import reactor.core.publisher.Flux;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping
	public List<Customer> getAllCustomers() {
		return customerService.loadAllCustomers();
	}
	
	@GetMapping(path = "allCustomers", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Customer> getAllCustomersStream() {
		return customerService.loadAllCustomersStream();
	}
	
}

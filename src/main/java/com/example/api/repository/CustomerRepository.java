package com.example.api.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.example.api.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findAllByOrderByNameAsc(Pageable pageable);
	
	List<Customer> findByName(String name);

	List<Customer> findByEmail(String email);

	List<Customer> findByGender(String gender);
	
	List<Customer> findByAddresses_city(String city);
	
	List<Customer> findByAddresses_state(String city);

}

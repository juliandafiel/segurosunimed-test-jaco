package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.domain.ViaCepDTO;
import com.example.api.repository.CustomerRepository;

import javassist.NotFoundException;


@Service
public class CustomerService {

	private CustomerRepository repository;
	
	@Autowired
	private ViaCepFeignService viaCepService;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public List<Customer> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, 
				size);
		return repository.findAllByOrderByNameAsc(pageable);
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	public List<Customer> findByName(String name) {
		return repository.findByName(name);
	}

	public List<Customer> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	public List<Customer> findByGender(String gender) {
		return repository.findByGender(gender);
	}

	public List<Customer> findByCity(String city) {
		return repository.findByAddresses_city(city);
	}

	public List<Customer> findByState(String state) {
		return repository.findByAddresses_state(state);
	}

	
	@Transactional
    public Customer save(Customer customer) {
        return this.repository.save(customer);
    }

	@Transactional
    public Customer update(Customer customer) {
        return this.repository.save(customer);
    }	
	
	@Transactional
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
	
	@Transactional
    public Customer saveAddress(Long idCustomer, String cep) throws NotFoundException{
		
		ViaCepDTO cepDTO = viaCepService.consultarCep(cep);
		Address address = cepDTO.toAddress();
		return saveAddress(idCustomer, address);
    }
	
	
	@Transactional
    public Customer saveAddress(Long idCustomer, Address address) throws NotFoundException{
		
		Customer customer = repository.findById(idCustomer).orElse(null);
		if ( customer == null ) {
			throw new NotFoundException("Customer not found");
		}
		customer.getAddresses().add(address);
        return this.repository.save(customer);
    }
}

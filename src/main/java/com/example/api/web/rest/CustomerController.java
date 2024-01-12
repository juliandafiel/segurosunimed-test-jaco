package com.example.api.web.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService service;

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping()
	public List<Customer> findAll(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "999999999", required = false) int size) {
		return service.findAll(page, size);
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}

	@GetMapping("/name/{name}")
	public List<Customer> findByName(@PathVariable String name) {
		return service.findByName(name);
	}

	@GetMapping("/email/{email}")
	public List<Customer> findByEmail(@PathVariable String email) {
		return service.findByEmail(email);
	}

	@GetMapping("/gender/{gender}")
	public List<Customer> findByGender(@PathVariable String gender) {
		return service.findByGender(gender	);
	}
	
	@GetMapping("/city/{city}")
	public List<Customer> findByCity(@PathVariable String city) {
		return service.findByCity(city);
	}
	
	@GetMapping("/state/{state}")
	public List<Customer> findByState(@PathVariable String state) {
		return service.findByState(state);
	}
	
    @PostMapping
    public ResponseEntity<Customer> save(@Valid @RequestBody Customer customer) {
        return ResponseEntity.ok(this.service.save(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @Valid @RequestBody Customer customer) {
    	customer.setId(id);
        return ResponseEntity.ok(this.service.update(customer));
    }
    
    @PostMapping("/address/{idCustomer}")
    public ResponseEntity<?> saveAddress(@PathVariable Long idCustomer, @Valid @RequestBody Address address) {
        try {        	
        	return ResponseEntity.ok(this.service.saveAddress(idCustomer, address));
        } catch (NotFoundException ex) {
        	return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @PostMapping("/address/{idCustomer}/cep/{cep}")
    public ResponseEntity<?> saveAddress(@PathVariable Long idCustomer, @PathVariable String cep) {
        try {        	
        	return ResponseEntity.ok(this.service.saveAddress(idCustomer, cep));
        } catch (NotFoundException ex) {
        	return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}

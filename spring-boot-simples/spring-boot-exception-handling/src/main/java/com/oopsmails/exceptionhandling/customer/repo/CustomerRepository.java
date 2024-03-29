package com.oopsmails.exceptionhandling.customer.repo;

import com.oopsmails.exceptionhandling.customer.domain.Customer;
import com.oopsmails.exceptionhandling.customer.entity.CustomerEntity;
import com.oopsmails.exceptionhandling.customer.repo.jpa.CustomerJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CustomerRepository {
	
	@Autowired
	private CustomerJpaRepository customerJpaRepository;

	public Optional<Customer> getCustomerById(Integer customerId) {
		Optional<CustomerEntity> customerEntity = customerJpaRepository.findById(customerId);
		return customerEntity.map(this::customerEntityToCustomer);
	}

	private Customer customerEntityToCustomer(CustomerEntity customerEntity) {
		Customer customer = new Customer();
		customer.setCustomerId(customerEntity.getCustomerId());
		customer.setFirstName(customerEntity.getFirstName());
		customer.setLastName(customerEntity.getLastName());
		customer.setAge(customerEntity.getAge());

		return customer;
	}
	
	public Customer saveCustomer(Customer customer) {
		CustomerEntity customerEntity = customerToCustomerEntity(customer);		
		CustomerEntity savedCustomerEntity = customerJpaRepository.save(customerEntity);
		
		return customerEntityToCustomer(savedCustomerEntity);
	}
	
	private CustomerEntity customerToCustomerEntity(Customer customer) {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setCustomerId(customer.getCustomerId());
		customerEntity.setFirstName(customer.getFirstName());
		customerEntity.setLastName(customer.getLastName());
		customerEntity.setAge(customer.getAge());
		
		return customerEntity;		
	}
	
	public List<Customer> getCustomersAll() {
		List<CustomerEntity> customerEntity = customerJpaRepository.findAll();
		return customerEntity.stream().map(this::customerEntityToCustomer).collect(Collectors.toList());
	}

	public List<Customer> getCustomersByFirstName(String firstName) {
		List<CustomerEntity> customerEntity = customerJpaRepository.findByFirstName(firstName);
		return customerEntity.stream().map(this::customerEntityToCustomer).collect(Collectors.toList());
	}
	
	public Optional<Customer> deleteCustomerById(Integer customerId) {
		Optional<Customer> deletedCustomerOptional = Optional.empty();
		Optional<CustomerEntity> customerEntity = customerJpaRepository.findById(customerId);
		
		if(customerEntity.isPresent()) {
			customerJpaRepository.deleteById(customerId);
			deletedCustomerOptional = customerEntity.map(this::customerEntityToCustomer);
		}
		
		return deletedCustomerOptional;		
	}

//	@Query("SELECT new CustomerCount(c.year, COUNT(c.year)) "
//			+ "FROM Comment AS c GROUP BY c.year ORDER BY c.year DESC")
//	List<Customer> countTotalCommentsByYearClass();
}

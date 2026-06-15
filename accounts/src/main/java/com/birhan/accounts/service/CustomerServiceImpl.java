package com.birhan.accounts.service;

import com.birhan.accounts.dto.CustomerDto;
import com.birhan.accounts.mapper.CustomerMapper;
import com.birhan.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService{

    private CustomerRepository customerRepository;

    @Override
    public void createCustomer(CustomerDto customerDto) {
        customerRepository.save(CustomerMapper.toCustomer(customerDto));
    }
}

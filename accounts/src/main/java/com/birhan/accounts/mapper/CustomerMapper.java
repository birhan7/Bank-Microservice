package com.birhan.accounts.mapper;

import com.birhan.accounts.dto.CustomerDto;
import com.birhan.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDto toCustomerDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail(customer.getEmail());
        customerDto.setName(customer.getName());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer toCustomer(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setEmail(customerDto.getEmail());
        customer.setName(customerDto.getName());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}

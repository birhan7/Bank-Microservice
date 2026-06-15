package com.birhan.accounts.service;

import com.birhan.accounts.constants.AccountConstants;
import com.birhan.accounts.dto.CustomerDto;
import com.birhan.accounts.entity.Account;
import com.birhan.accounts.entity.Customer;
import com.birhan.accounts.exception.CustomerAlreadyExistsException;
import com.birhan.accounts.mapper.CustomerMapper;
import com.birhan.accounts.repository.AccountRepository;
import com.birhan.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService{
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer Already registered with the give mobile number " + customerDto.getMobileNumber());
        }
        Customer customer = CustomerMapper.toCustomer(customerDto);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));

    }

    private Account createNewAccount(Customer customer){
        Account newAccount = new Account();
        long accountNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(accountNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setCreatedBy("Anonymous");
        newAccount.setCreatedAt(LocalDateTime.now());
        return newAccount;
    }
}

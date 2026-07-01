package com.birhan.accounts.service;

import com.birhan.accounts.constants.AccountConstants;
import com.birhan.accounts.dto.AccountDto;
import com.birhan.accounts.dto.CustomerDto;
import com.birhan.accounts.entity.Account;
import com.birhan.accounts.entity.Customer;
import com.birhan.accounts.exception.CustomerAlreadyExistsException;
import com.birhan.accounts.exception.ResourceNotFoundException;
import com.birhan.accounts.mapper.AccountMapper;
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
        Customer customer = CustomerMapper.toCustomer(customerDto, new Customer());
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));

    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(()-> new ResourceNotFoundException("Account","mobileNumber", mobileNumber));
        CustomerDto customerDto = new CustomerDto();
        customerDto = CustomerMapper.toCustomerDto(customer, customerDto);
        customerDto.setAccountDto(AccountMapper.toAccountDto(account, new AccountDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccountDto();
        if(accountDto != null) {
            Account account = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountDto.getAccountNumber().toString()));
            long customerId = account.getCustomerId();
            account = AccountMapper.toAccount(accountDto, account);
            accountRepository.save(account);

            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", String.valueOf(customerId)));
            customer = CustomerMapper.toCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
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

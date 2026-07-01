package com.birhan.accounts.service;

import com.birhan.accounts.dto.CustomerDto;

public interface IAccountService {

    /*
    * @param CustomerDto - CustomerDto Object
    * */
    void createAccount(CustomerDto customerDto);

    /*
     * @param mobileNumber - Input a string MobileNumber
     * @return Account Details based on the given mobile number
     * */
    CustomerDto fetchAccount(String mobileNumber);

    /*
     * @param CustomerDto - CustomerDto object
     * */
    boolean updateAccount(CustomerDto customerDto);

}

package com.example.bank.service;

import com.example.bank.entities.Account;
import com.example.bank.entities.Operation;


import java.util.List;

public interface BankService {

    public void deposit(String code, double amount);
    public void withdrawal(String code, double amount);
    public List<Operation> listOperation(String code);
    public void deleteOperation(String code);
    public Account findAccount(String code);
}

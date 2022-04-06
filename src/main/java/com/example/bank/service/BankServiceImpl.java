package com.example.bank.service;

import com.example.bank.entities.Account;
import com.example.bank.entities.Deposit;
import com.example.bank.entities.Operation;
import com.example.bank.entities.Withdrawal;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.OperationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BankServiceImpl implements BankService {

    private static Logger logger = LogManager.getLogger(BankServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OperationRepository operationRepository;

    @Override
    /**
     * deposit money in account
     *
     * @param code
     * @param amount
     */
    public void deposit(String code, double amount) {
        logger.info("Start BankServiceImpl.deposit method");
        Account account = findAccount(code);
        if (account != null) {
            Deposit deposit = new Deposit();
            operationRepository.save(deposit);
            account.setAmount(account.getAmount() + amount);
            accountRepository.save(account);
        }
        logger.info("End BankServiceImpl.deposit method");
    }

    @Override
    /**
     * withdrawal money from  account
     *
     * @param code
     * @param amount
     */
    public void withdrawal(String code, double amount){
        logger.info("Start BankServiceImpl.withdrawal method");
        Account account = findAccount(code);
        if (account != null) {
            double overdraft = account.getOverdraft();
            if (account.getAmount() + overdraft < amount) {
                logger.error("This amount : {} not exist in your account: {} : " , amount, code);
                throw new RuntimeException("Not enough money in your account !");
            }
            Withdrawal withdrawal = new Withdrawal();
            operationRepository.save(withdrawal);
            account.setAmount(account.getAmount() - amount);
            accountRepository.save(account);
        }
        logger.info("Start BankServiceImpl.withdrawal method");
    }

    @Override
    /**
     * Check history of operations
     *
     * @param code
     * @return List of Operations
     */
    public List<Operation> listOperation(String code) {
        return operationRepository.listOperation(code);

    }

    @Override
    /**
     * delete operation by code
     *
     * @param code
     */
    public void deleteOperation(String code) {
        operationRepository.deleteOperationByAccount(code);
    }


    @Override
    /**
     * Find account client by code
     *
     * @param code
     * @return account
     */
    public Account findAccount(String code) {
        return accountRepository.findById(code).orElse(null);
    }
}

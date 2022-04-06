package com.example.bank.rest;

import com.example.bank.entities.Account;
import com.example.bank.entities.Deposit;
import com.example.bank.entities.Operation;
import com.example.bank.entities.Withdrawal;
import com.example.bank.service.BankService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/services")
public class BankServiceRest {

    private static Logger logger = LogManager.getLogger(BankServiceRest.class);

    @Autowired
    private BankService bankService;


    /**
     * Return account by code account
     *
     * @param code code account
     * @return ResponseEntity<Account>
     */
    @GetMapping(value = "/account/{code}")
    public ResponseEntity<Account> findAccount(@PathVariable("code") String code) {
        logger.info("Start find account code  : {}", code);
        Account account = new Account();
        try {
            account = bankService.findAccount(code);
        } catch (Exception e) {
            return new ResponseEntity<>(account, HttpStatus.NOT_FOUND);
        }
        logger.info("End find account code  : {}", code);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    /**
     * deposit money in account
     *
     * @param operation Deposit
     * @return ResponseEntity
     */
    @PutMapping(value = "/deposit")
    public ResponseEntity<Boolean> deposit(@RequestBody Deposit operation) {
        logger.info("Start deposit in account ");
        try {
            String code = operation.getAccount().getCode();
            double amount = operation.getAmount();
            bankService.deposit(code, amount);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        logger.info("End deposit in account ");
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * withdrawal money from account
     *
     * @param operation Withdrawal
     * @return ResponseEntity
     */
    @PutMapping(value = "/withdrawal")
    public ResponseEntity<Boolean> withdrawal(@RequestBody Withdrawal operation) {
        logger.info("Start withdrawal from account");
        try {
            String code = operation.getAccount().getCode();
            double amount = operation.getAmount();
            bankService.withdrawal(code, amount);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        logger.info("End withdrawal from account");
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * Check history of operations (operation, date, amount, balance) of my operations
     *
     * @param code
     * @return ResponseEntity
     */
    @PutMapping(value = "/operations/{code}")
    public ResponseEntity<List<Operation>> listOperation(@PathVariable String code) {
        logger.info("Start getting history of operations from account : {}", code);
        List<Operation> operations = null;
        try {
            operations = bankService.listOperation(code);
        } catch (Exception e) {
            return new ResponseEntity<>(operations, HttpStatus.NOT_FOUND);
        }
        logger.info("End getting history of operations from account : {}", code);
        return new ResponseEntity<>(operations, HttpStatus.OK);

    }
}

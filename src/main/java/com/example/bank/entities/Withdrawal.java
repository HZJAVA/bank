package com.example.bank.entities;

import java.time.LocalDate;

public class Withdrawal extends Operation {

    public Withdrawal() {
        super();
    }

    public Withdrawal(LocalDate dateOperation, double amount, Account account) {
        super(dateOperation, amount, account);
    }
}

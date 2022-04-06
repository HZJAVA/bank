package com.example.bank.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("DEPOSIT")
public class Deposit extends Operation {

    public Deposit() {
        super();
    }

    public Deposit(LocalDate dateOperation, double amount, Account account) {
        super(dateOperation, amount, account);
    }


}

package com.example.bank.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Operation implements Serializable {
    @Id
    @GeneratedValue
    private int code;
    private LocalDate dateOperation;
    private double amount;
    private String typeOperation;
    @ManyToOne
    @JoinColumn(name = "CODE_ACCOUNT")
    private Account account;

    protected  Operation() {
    }

    protected Operation(LocalDate dateOperation, double amount, Account account) {
        this.dateOperation = dateOperation;
        this.amount = amount;
        this.account = account;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocalDate getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

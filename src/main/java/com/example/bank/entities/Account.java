package com.example.bank.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Account implements Serializable {

    @Id
    private String code;
    private LocalDate dateCreation;
    private double amount;
    //Découvert
    private double overdraft;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CODE_CLI")
    private Client client;

    @OneToMany(mappedBy="account")
    private List<Operation> operations;

    public Account() {
    }

    public Account(String code, LocalDate dateCreation, double amount,double overdraft, Client client) {
        this.code = code;
        this.dateCreation = dateCreation;
        this.amount = amount;
        this.overdraft = overdraft;
        this.client = client;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}

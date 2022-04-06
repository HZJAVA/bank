package com.example.bank.service;

import com.example.bank.entities.Client;

import java.util.Optional;

public interface ClientService {
    public Optional<Client> findById(Long id);
    public boolean delete(Long id);
}

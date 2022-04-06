package com.example.bank.service;

import com.example.bank.entities.Client;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.ClientRepository;
import com.example.bank.repository.OperationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private static Logger logger = LogManager.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Override
    /**
     * Find client by id
     * @param id
     * @return client
     */
    public Optional<Client> findById(Long id) {
        logger.info("Start ClientServiceImpl.findById method");
        Optional<Client> client = clientRepository.findById(id);
        logger.info("End ClientServiceImpl.findById method");
        return client;
    }

    @Override
    /**
     * Delete a client by his Id
     * @param id
     * @return true if all is OK, false if KO
     */
    public boolean delete(Long id) {
        logger.info("Start ClientServiceImpl.delete method");
        Optional<Client> client = clientRepository.findById(id);
        boolean isDeleted = false;
        if(client.isPresent()){
            operationRepository.deleteOperationByAccount(client.get().getAccount().getCode());
            accountRepository.delete(client.get().getAccount());
            try {
                clientRepository.deleteById(id);
                isDeleted =  true;
            } catch (Exception e) {
                isDeleted = false;
            }
        }
        logger.info("End ClientServiceImpl.delete method");
        return  isDeleted;
    }
}

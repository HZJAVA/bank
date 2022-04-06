package com.example.bank.rest;

import com.example.bank.entities.Client;
import com.example.bank.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping(value = "/services")
public class ClientServiceRest {

    private static Logger logger = LogManager.getLogger(ClientServiceRest.class);

    @Autowired
    private ClientService clientService;

    /**
     * Return Client by id
     *
     * @param id
     * @return ResponseEntity<Client>
     */
    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<Client> findById(@PathVariable("id") Long id) {
        logger.info("Start find client by : {}", id);
        Optional<Client> client = clientService.findById(id);
        logger.info("End find client by : {}", id);
        return new ResponseEntity<>(client.get(), HttpStatus.OK);
    }

    /**
     * Delete Client by id
     *
     * @param id
     * @return ResponseEntity
     */
    @DeleteMapping(value = "/clients/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable Long id) {
        logger.info("Start delete client by : {}", id);
        try {
            clientService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        logger.info("End delete client by : {}", id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}

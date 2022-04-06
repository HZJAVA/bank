package com.example.bank;


import com.example.bank.entities.Account;
import com.example.bank.entities.Client;
import com.example.bank.entities.Operation;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.OperationRepository;
import com.example.bank.service.BankServiceImpl;
import com.example.bank.service.ClientService;
import com.example.bank.service.ClientServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import com.example.bank.service.BankService;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @InjectMocks
    private BankServiceImpl bankService;

    @Mock
    private OperationRepository operationRepository;

    @Test
    void testFindAccount() {
        Client client = new Client("clientTest", "address");
        String code = client.getAccount().getCode();

        Account account = bankService.findAccount(code);
        Assert.assertNotNull("Account doesn't exist", String.valueOf(account));
        boolean status = clientService.delete(client.getId());
        Assert.assertTrue(status, "Delete client doesn't work !");
    }

    @Test
    void testDeposit() {
        //given
        Account account = createAccount("1", LocalDate.now(), 500, 0);
        Account account2 = createAccount("2", LocalDate.now(), 1000, 0);

        when(bankService.findAccount("1")).thenReturn(account);
        when(bankService.findAccount("2")).thenReturn(account2);

        //when
        double amount = account.getAmount();
        bankService.deposit(account.getCode(), 500);

        //then
        Assert.assertEquals(amount + 500, account2.getAmount(), 0);
    }

    @Test
    void testWithdrawal() {
        //given
        Client client = new Client(1L, "clientTest", "address");
        Account account = createAccount("1", LocalDate.now(), 500, 0);

        when(bankService.findAccount("1")).thenReturn(account);

        //when
        double amount = account.getAmount();
        bankService.withdrawal(account.getCode(), 500);
        Assert.assertEquals(amount - 500, 0);
    }

    @Test
    void testListOperation() {
        //given
        Client client = new Client(1L, "clientTest", "address");
        Account account = bankService.findAccount(client.getAccount().getCode());
        double amount = account.getAmount();
        bankService.deposit(account.getCode(), 5000);
        bankService.withdrawal(account.getCode(), 500);
        bankService.withdrawal(account.getCode(), 500);
        Account account2 = bankService.findAccount(client.getAccount().getCode());
        Assert.assertEquals(amount + 5000 - 500 - 500, account2.getAmount(), 0);
        List<Operation> list = bankService.listOperation(account.getCode());
        Assert.assertEquals(list.size(), 3);
    }

    public Account createAccount(String code, LocalDate dateCreation, double amount, double overdraft) {
        Account account = new Account();
        account.setCode(code);
        account.setDateCreation(dateCreation);
        account.setAmount(amount);
        account.setOverdraft(overdraft);
        return account;
    }
}

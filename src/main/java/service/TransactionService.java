package service;

import exception.AccountNotEnoughMoney;
import exception.AccountNotFoundException;
import model.Account;
import repository.impl.AccountRepositoryImpl;
import service.impl.AccountServiceImpl;

import java.util.concurrent.locks.ReentrantLock;

import static util.UtilService.getRandomInt;

public class TransactionService implements Runnable {

    private AccountRepositoryImpl accountRepository;
    private final long CONST_SUM = 100L;
    private AccountServiceImpl accountService;

    public TransactionService(AccountRepositoryImpl accountRepository, AccountServiceImpl accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @Override
    public void run() {
        int firstAccountId = getRandomInt(0, accountRepository.getAccountArrayList().size() - 1);//нуль специально чтоб падоло
        int secondAccountId = getRandomInt(firstAccountId + 1, accountRepository.getAccountArrayList().size());
        try {
            Account tmpFirstAccount = accountRepository.getAccount(firstAccountId);
            Account tmpSecondAccount = accountRepository.getAccount(secondAccountId);

            tmpFirstAccount.getReentrantLock().lock();
            tmpSecondAccount.getReentrantLock().lock();
            try {
                long tmpFirstAccountBalance = tmpFirstAccount.getBalance();
                long tmpSecondAccountBalance = tmpSecondAccount.getBalance();
                if (tmpFirstAccountBalance < tmpSecondAccountBalance) {
                    if (accountService.isAccountBalanceEnough(tmpSecondAccount, CONST_SUM)) {
                        tmpSecondAccount.setBalance(tmpSecondAccount.getBalance() - CONST_SUM);
                        tmpFirstAccount.setBalance(tmpFirstAccount.getBalance() + CONST_SUM);
                        System.out.println(tmpFirstAccount + " get from " + tmpSecondAccount + CONST_SUM + "$");
                    } else {
                        throw new AccountNotEnoughMoney("Account not enough money with id ", tmpSecondAccount.getId(), tmpSecondAccount.getBalance(), CONST_SUM);
                    }
                } else {
                    if (accountService.isAccountBalanceEnough(tmpFirstAccount, CONST_SUM)) {
                        tmpSecondAccount.setBalance(tmpSecondAccount.getBalance() + CONST_SUM);
                        tmpFirstAccount.setBalance(tmpFirstAccount.getBalance() - CONST_SUM);
                        System.out.println(tmpFirstAccount + " send " + tmpSecondAccount + CONST_SUM + "$");
                    } else {
                        throw new AccountNotEnoughMoney("Account not enough money with id ",
                                tmpFirstAccount.getId(),
                                tmpFirstAccount.getBalance(),
                                CONST_SUM);
                    }
                }
            } catch (AccountNotEnoughMoney e) {
                System.out.println(e.getMessage() + e.getId() +
                        ", current balance " + e.getCurrentBalance() +
                        ", required " + e.getRequestedSum());
            } finally {
                tmpSecondAccount.getReentrantLock().unlock();
                tmpFirstAccount.getReentrantLock().unlock();
            }
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage() + e.getId());
        }
    }

}

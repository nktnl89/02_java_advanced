package service;

import exception.AccountNotEnoughMoney;
import exception.AccountNotFoundException;
import model.Account;
import org.slf4j.Logger;
import repository.impl.AccountRepositoryImpl;
import service.impl.AccountServiceImpl;

import java.util.concurrent.Callable;

import static service.UtilService.getRandomInt;

public class TransactionServiceCallable implements Callable {
    private AccountRepositoryImpl accountRepository;
    private AccountServiceImpl accountService;

    private final long CONST_SUM = 100L;
    private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger("TransactionService");

    TransactionServiceCallable(AccountRepositoryImpl accountRepository, AccountServiceImpl accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    private void transferSumBetweenAccounts(Account account1, Account account2, long sum) {
        account1.setBalance(account1.getBalance() - sum);
        account2.setBalance(account2.getBalance() + sum);
        LOGGER.debug(account1 + " get from " + account2 + CONST_SUM + "$");
    }

    private void makeTransferBetweenAccounts(Account tmpFirstAccount, Account tmpSecondAccount) throws AccountNotEnoughMoney {
        long tmpFirstAccountBalance = tmpFirstAccount.getBalance();
        long tmpSecondAccountBalance = tmpSecondAccount.getBalance();

        if (tmpFirstAccountBalance < tmpSecondAccountBalance) {
            if (accountService.isAccountBalanceEnough(tmpSecondAccount, CONST_SUM)) {
                transferSumBetweenAccounts(tmpSecondAccount, tmpFirstAccount, CONST_SUM);
            } else {
                throw new AccountNotEnoughMoney("Account not enough money",
                        tmpSecondAccount.getId(),
                        tmpSecondAccount.getBalance(),
                        CONST_SUM);
            }
        } else {
            if (accountService.isAccountBalanceEnough(tmpFirstAccount, CONST_SUM)) {
                transferSumBetweenAccounts(tmpFirstAccount, tmpSecondAccount, CONST_SUM);
            } else {
                throw new AccountNotEnoughMoney("Account not enough money",
                        tmpFirstAccount.getId(),
                        tmpFirstAccount.getBalance(),
                        CONST_SUM);
            }
        }
    }

    @Override
    public Object call() {
        int firstAccountId = getRandomInt(0, accountRepository.getAccountArrayList().size() - 1);//нуль специально чтоб был warning
        int secondAccountId = getRandomInt(firstAccountId + 1, accountRepository.getAccountArrayList().size());
        boolean isOperationCorrect = true;
        try {
            Account tmpFirstAccount = accountRepository.getAccount(firstAccountId);
            Account tmpSecondAccount = accountRepository.getAccount(secondAccountId);

            tmpFirstAccount.getReentrantLock().lock();

            tmpSecondAccount.getReentrantLock().lock();
            try {
                makeTransferBetweenAccounts(tmpFirstAccount, tmpSecondAccount);
            } catch (AccountNotEnoughMoney e) {
                LOGGER.warn(e.getMessage() + " id = {}, current balance = {} requested sum = {}", e.getId(), e.getCurrentBalance(), e.getRequestedSum());//, e);
                isOperationCorrect = false;
            } finally {
                tmpSecondAccount.getReentrantLock().unlock();
                tmpFirstAccount.getReentrantLock().unlock();
            }
        } catch (AccountNotFoundException e) {
            LOGGER.warn(e.getMessage() + " with id = {}", e.getId());//, e);
            isOperationCorrect = false;
        }
        return isOperationCorrect;
    }
}

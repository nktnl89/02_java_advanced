package service.impl;

import model.Account;
import service.AccountServiceInterface;

import java.util.ArrayList;

public class AccountServiceImpl implements AccountServiceInterface {
    @Override
    public long getSummaryBalances(ArrayList<Account> accounts) {
        return accounts.stream().mapToLong(Account::getBalance).sum();
    }

    @Override
    public boolean isAccountBalanceEnough(Account account, long sum) {
        return (account.getBalance() - sum) >= 0;
    }

}

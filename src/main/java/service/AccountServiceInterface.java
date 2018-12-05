package service;

import model.Account;

import java.util.ArrayList;

public interface AccountServiceInterface {
    long getSummaryBalances(ArrayList<Account>accounts);

    boolean isAccountBalanceEnough(Account account, long sum);
}

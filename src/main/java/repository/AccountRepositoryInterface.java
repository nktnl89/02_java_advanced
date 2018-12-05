package repository;

import exception.AccountNotFoundException;
import model.Account;
import model.User;

import java.util.ArrayList;

public interface AccountRepositoryInterface {
    ArrayList<Account> getListAccountsFromFile();

    void writeAccountToFile(Account account);

    Account getAccountFromFileById(int id) throws AccountNotFoundException;

    Account getAccount(int id) throws AccountNotFoundException;
}

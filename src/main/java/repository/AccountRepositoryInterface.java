package repository;

import model.Account;
import model.User;

import java.util.ArrayList;

public interface AccountRepositoryInterface {
    ArrayList<Account> getListAccounts(String catalog);

    void writeAccountToFile(Account account, String catalog);

    Account getAccountByOwner(User user, String catalog);

}

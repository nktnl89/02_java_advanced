package repository.impl;

import model.Account;
import model.User;
import repository.AccountRepositoryInterface;
import util.UtilService;

import java.util.ArrayList;

public class AccountRepositoryImpl implements AccountRepositoryInterface {

    private UtilService serializerUtil = new UtilService();

    public ArrayList<Account> getListAccounts(String dir) {
        return serializerUtil.getListAccounts(dir);
    }

    public void writeAccountToFile(Account account, String dir) {
        serializerUtil.writeAccountToFile(account, dir);
    }

    public void writeAccountListToFiles(ArrayList<Account> accounts, String dir) {
        accounts.forEach(account -> serializerUtil.writeAccountToFile(account, dir));
    }

    public Account getAccountByOwner(User user, String dir) {
        return serializerUtil.getAccountByOwner(user, dir);
    }
}

package repository.impl;

import exception.AccountNotFoundException;
import model.Account;
import repository.AccountRepositoryInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class AccountRepositoryImpl implements AccountRepositoryInterface {
    private String dir;
    private ArrayList<Account> accountArrayList;

    public ArrayList<Account> getAccountArrayList() {
        return accountArrayList;
    }

    public void setAccountArrayList(ArrayList<Account> accountArrayList) {
        this.accountArrayList = accountArrayList;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public AccountRepositoryImpl() {
    }

    public AccountRepositoryImpl(String dir) {
        this.dir = dir;
    }

    public ArrayList<Account> getListAccountsFromFile() {
        ArrayList<Account> accountArrayList = new ArrayList<>();
        File dirFile = new File(dir);
        if (dirFile.exists() && dirFile.isDirectory()) {
            for (File file : dirFile.listFiles()) {
                try (FileInputStream fileInputStream = new FileInputStream(file.getPath())) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    accountArrayList.add((Account) objectInputStream.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        accountArrayList.sort(new Comparator<Account>() {
            public int compare(Account o1, Account o2) {
                if (o1.getId() == o2.getId())
                    return 0;
                return o1.getId() < o2.getId() ? -1 : 1;
            }
        });
        return accountArrayList;
    }

    public void writeAccountToFile(Account account) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(dir + "/" + account.getOwner())) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(account);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAccountListToFiles(ArrayList<Account> accounts) {
        accounts.forEach(this::writeAccountToFile);
    }

    @Override
    public Account getAccountFromFileById(int id) throws AccountNotFoundException {
        Account tmpAccount = null;
        File dirFile = new File(dir);
        if (dirFile.exists() && dirFile.isDirectory()) {
            for (File file : dirFile.listFiles()) {
                try (FileInputStream fileInputStream = new FileInputStream(file.getPath())) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    tmpAccount = (Account) objectInputStream.readObject();
                    if (tmpAccount.getId() == id) {
                        return tmpAccount;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new AccountNotFoundException("Account not found with id ", id);
                }
            }
        }
        return tmpAccount;
    }

    @Override
    public Account getAccount(int id) throws AccountNotFoundException {
        for (Account account : accountArrayList) {
            if (account.getId() == id) {
                return account;
            }
        }
        throw new AccountNotFoundException("Account not found", id);
    }
}

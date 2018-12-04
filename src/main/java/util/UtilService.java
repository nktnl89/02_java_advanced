package util;

import model.Account;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class UtilService {
    public long getRandomLong() {
        return (long) (new Random().nextDouble() * (1500));
    }

    public ArrayList<Account> getListAccounts(String dir) {
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
        return accountArrayList;
    }

    public void writeAccountToFile(Account account, String dir) {

        try (FileOutputStream fileOutputStream = new FileOutputStream(dir + "/" + account.getOwner())) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(account);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Account getAccountByOwner(User user, String dir) {
        File dirFile = new File(dir);
        if (dirFile.exists() && dirFile.isDirectory()) {
            for (File file : dirFile.listFiles()) {
                if (file.getName().equals(user.getName())) {
                    try (FileInputStream fileInputStream = new FileInputStream(file.getPath())) {
                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                        return (Account) objectInputStream.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

}

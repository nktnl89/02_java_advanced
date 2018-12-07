package service;

import model.Account;
import model.User;

import java.util.ArrayList;
import java.util.Random;

class UtilService {
    private static long getRandomLong() {
        return (long) (new Random().nextDouble() * (300));
    }

    static int getRandomInt(int range) {
        return new Random().nextInt() * range;
    }

    static int getRandomInt(int start, int finish) {
        return start + new Random().nextInt(finish - start);
    }

    static ArrayList<Account> initData() {
        ArrayList<Account> accountArrayList = new ArrayList<>();
        accountArrayList.add(new Account(1, new User("LarionovA"), getRandomLong()));
        accountArrayList.add(new Account(2, new User("KorostelevK"), getRandomLong()));
        accountArrayList.add(new Account(3, new User("BuneginP"), getRandomLong()));
        accountArrayList.add(new Account(4, new User("DavlatovU"), getRandomLong()));
        accountArrayList.add(new Account(5, new User("GabdrahmanovM"), getRandomLong()));
        accountArrayList.add(new Account(6, new User("KurbanovR"), getRandomLong()));
        accountArrayList.add(new Account(7, new User("TararuevaU"), getRandomLong()));
        accountArrayList.add(new Account(8, new User("NikitinA"), getRandomLong()));
        accountArrayList.add(new Account(9, new User("MoskovetcL"), getRandomLong()));

        return accountArrayList;
    }
}

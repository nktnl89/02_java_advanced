package service;

import model.Account;
import model.User;
import repository.impl.AccountRepositoryImpl;
import service.impl.AccountServiceImpl;
import util.UtilService;

import java.util.ArrayList;

public class DemoService {

    private AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
    private AccountServiceImpl accountService = new AccountServiceImpl();
    private UtilService utilService = new UtilService();

    public void startDemo() {
        String dir = "src/main/resources";
        initData(dir);
        ArrayList<Account> accountArrayList = accountRepository.getListAccounts(dir);
        accountArrayList.forEach(System.out::println);


        System.out.println("");
        System.out.println("Starting balance: " + accountService.getSummaryBalances(accountArrayList) + "$");
        System.out.println("");
        //!ТРАНЗАКЦИЯ!!!
        //дальше берем Один акаунт
        //потом берем другой аккаунт
        //надо выяснить операцию, если у второго больше чем у первого, то отдаем от второго первому
        //  иначе наоборот
        //потом придумываем сколько ему передадим(рандом от баланса)||или сколько передадим от него(рандом от баланса второго)
        //потом записываем новые балансы в соответствующие файлы
        //...
        //профит!
    }

    private void initData(String dir) {
        ArrayList<Account> accountArrayList = new ArrayList<>();
        accountArrayList.add(new Account(new User("LarionovA"), utilService.getRandomLong()));
        accountArrayList.add(new Account(new User("KorostelevK"), utilService.getRandomLong()));
        accountArrayList.add(new Account(new User("BuneginP"), utilService.getRandomLong()));
        accountArrayList.add(new Account(new User("DavlatovU"), utilService.getRandomLong()));
        accountArrayList.add(new Account(new User("GabdrahmanovM"), utilService.getRandomLong()));
        accountArrayList.add(new Account(new User("KurbanovR"), utilService.getRandomLong()));
        accountArrayList.add(new Account(new User("TararuevaU"), utilService.getRandomLong()));
        accountArrayList.add(new Account(new User("MoskovetcL"), utilService.getRandomLong()));

        accountRepository.writeAccountListToFiles(accountArrayList, dir);
    }


}


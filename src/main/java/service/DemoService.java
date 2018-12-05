package service;

import model.Account;
import model.User;
import repository.impl.AccountRepositoryImpl;
import service.impl.AccountServiceImpl;

import java.util.ArrayList;

import static util.UtilService.*;

public class DemoService {

    private AccountRepositoryImpl accountRepository;
    private AccountServiceImpl accountService;

    public void startDemo() {
        String dir = "src/main/resources";
        accountRepository = new AccountRepositoryImpl();
        accountRepository.setDir(dir);
        accountService = new AccountServiceImpl();

        initData(dir);
        ArrayList<Account> accountArrayList = accountRepository.getListAccountsFromFile();
        accountRepository.setAccountArrayList(accountArrayList);
        accountArrayList.forEach(System.out::println);

        System.out.println("");
        System.out.println("Starting balance: " + accountService.getSummaryBalances(accountArrayList) + "$");
        System.out.println("_____________________________________________________________");
        System.out.println();


        for (int i = 0; i < 10; i++) {
            TransactionService transactionService = new TransactionService(accountRepository, accountService);

            new Thread(transactionService).start();

        }

        accountRepository.writeAccountListToFiles(accountRepository.getAccountArrayList());
        ArrayList<Account> afterTransaction = accountRepository.getListAccountsFromFile();
        afterTransaction.forEach(System.out::println);

        System.out.println("");
        System.out.println("Starting balance: " + accountService.getSummaryBalances(afterTransaction) + "$");
        System.out.println("_____________________________________________________________");
        System.out.println();

    }

    private void initData(String dir) {
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

        accountRepository.writeAccountListToFiles(accountArrayList);
    }


}


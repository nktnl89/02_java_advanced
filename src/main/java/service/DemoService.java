package service;

import model.Account;
import org.slf4j.Logger;
import repository.impl.AccountRepositoryImpl;
import service.impl.AccountServiceImpl;

import java.util.ArrayList;
import java.util.concurrent.*;

import static service.UtilService.*;

public class DemoService {

    private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(service.DemoService.class);//"DemoService");

    public void startDemo() {
        String dir = "src/main/resources/data";
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
        accountRepository.setDir(dir);
        AccountServiceImpl accountService = new AccountServiceImpl();

        accountRepository.setAccountArrayList(initData());

        ArrayList<Account> accountArrayList = accountRepository.getListAccountsFromFile();
        accountRepository.setAccountArrayList(accountArrayList);
        accountArrayList.forEach(account -> LOGGER.info(account.toString()));
        LOGGER.info("start balance: " + accountService.getSummaryBalances(accountArrayList) + "$");

//!!!!!!ЭТО РАБОТАЕТ С РАНАБЛ, НО ДЕЛАЕТ БОЛЬШЕ ОПЕРАЦИЙ ЧЕМ НАДО!!!
//        CountDownLatch countDownLatch = new CountDownLatch(10);
//        ExecutorService executor = Executors.newFixedThreadPool(5);
//        try {
//            while (countDownLatch.getCount() != 0) {
//                executor.submit(new TransactionService(accountRepository, accountService, countDownLatch));
//                System.out.println(countDownLatch.getCount());
//            }
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        ExecutorService executor = Executors.newFixedThreadPool(20);
        int counterCorrectOperations = 0;
        int counterOperationsAtAll = 0;
        while (counterCorrectOperations < 1000) {
            Future result = executor.submit(new TransactionServiceCallable(accountRepository, accountService));
            counterOperationsAtAll++;
            try {
                if ((Boolean) result.get(10, TimeUnit.MILLISECONDS)) {
                    counterCorrectOperations++;
                    LOGGER.debug("Correct operation №{}", counterCorrectOperations);
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        LOGGER.info("All operations - {}", counterOperationsAtAll);
        LOGGER.info("Correct operations - {}", counterCorrectOperations);

        executor.shutdown();
        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        accountRepository.writeAccountListToFiles(accountRepository.getAccountArrayList());
        ArrayList<Account> afterTransaction = accountRepository.getListAccountsFromFile();
        afterTransaction.forEach(account -> LOGGER.info(account.toString()));
        LOGGER.info("Finish balance: " + accountService.getSummaryBalances(afterTransaction) + "$");
    }
}


package exception;

public class AccountNotEnoughMoney extends Exception {
    private int id;
    private long currentBalance;
    private long requestedSum;

    public long getRequestedSum() {
        return requestedSum;
    }

    public int getId() {
        return id;
    }

    public long getCurrentBalance() {
        return currentBalance;
    }

    public AccountNotEnoughMoney(String message, int id, long currentBalance, long requestedSum) {
        super(message);
        this.id = id;
        this.currentBalance = currentBalance;
        this.requestedSum = requestedSum;
    }
}

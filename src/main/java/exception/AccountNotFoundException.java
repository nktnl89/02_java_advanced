package exception;

public class AccountNotFoundException extends Exception {
    private int id;

    public int getId() {
        return id;
    }

    public AccountNotFoundException(String message, int id) {
        super(message);
        this.id = id;
    }
}

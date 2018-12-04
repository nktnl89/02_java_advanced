package model;

import java.io.Serializable;

public class Account implements Serializable {
    private User owner;
    private long balance;

    public Account() {
    }

    @Override
    public String toString() {
        return "Account{" +
                "owner=" + owner +
                ", balance=" + balance +
                '}';
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Account(User owner, Long balance) {
        this.owner = owner;
        this.balance = balance;
    }
}

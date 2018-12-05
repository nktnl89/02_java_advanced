package model;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantLock;

public class Account implements Serializable {
    private int id;
    private User owner;
    private long balance;
    private ReentrantLock reentrantLock = new ReentrantLock();

    public ReentrantLock getReentrantLock() {
        return reentrantLock;
    }

    public void setReentrantLock(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public Account() {
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", owner=" + owner +
                ", balance=" + balance +
                "$}";
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

    public Account(int id, User owner, Long balance) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
    }
}

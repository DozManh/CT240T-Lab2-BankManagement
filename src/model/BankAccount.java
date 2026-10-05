package model;

import exception.InvalidAmountException;
import exception.InsufficientBalanceException;

public abstract class BankAccount {
    private String accountNumber;
    private String holderName;
    private double balance;

    public BankAccount(String accountNumber, String holderName, double balance) throws InvalidAmountException {
        if (balance < 0) {
            throw new InvalidAmountException("Số dư ban đầu không được âm!");
        }
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public double getBalance() { return balance; }
    protected void setBalance(double balance) { this.balance = balance; }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Số tiền nạp phải lớn hơn 0!");
        }
        this.balance += amount;
    }

    public abstract void withdraw(double amount) 
            throws InsufficientBalanceException, InvalidAmountException;

    @Override
    public String toString() {
        return "STK: " + accountNumber + " | Chủ TK: " + holderName + " | Số dư: " + String.format("%,.0f", balance) + " VNĐ";
    }
}
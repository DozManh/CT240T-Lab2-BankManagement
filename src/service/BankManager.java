package service;

import model.BankAccount;
import exception.InvalidAmountException;
import exception.InsufficientBalanceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankManager {
    private Map<String, BankAccount> accountMap;

    public BankManager() {
        this.accountMap = new HashMap<>();
    }

    public void addAccount(BankAccount account) {
        accountMap.put(account.getAccountNumber(), account);
    }

    public BankAccount findAccount(String accountNumber) {
        return accountMap.get(accountNumber);
    }

    public List<BankAccount> getAllAccounts() {
        return new ArrayList<>(accountMap.values());
    }

    public double calculateTotalBalance(List<? extends BankAccount> accounts) {
        double total = 0.0;
        for (BankAccount acc : accounts) {
            total += acc.getBalance();
        }
        return total;
    }

    public void transferMoney(String fromAccNum, String toAccNum, double amount) 
            throws InsufficientBalanceException, InvalidAmountException {
        BankAccount fromAcc = findAccount(fromAccNum);
        BankAccount toAcc = findAccount(toAccNum);

        if (fromAcc == null) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản nguồn: " + fromAccNum);
        }
        if (toAcc == null) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản đích: " + toAccNum);
        }
        if (fromAccNum.equalsIgnoreCase(toAccNum)) {
            throw new IllegalArgumentException("Tài khoản nguồn và đích không được trùng nhau!");
        }

        fromAcc.withdraw(amount);
        toAcc.deposit(amount);
    }
}
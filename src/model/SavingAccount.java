package model;

import exception.InvalidAmountException;
import exception.InsufficientBalanceException;

public class SavingAccount extends BankAccount {
    private double interestRate;
    public static final double MIN_BALANCE = 50000.0;

    public SavingAccount(String accountNumber, String holderName, double balance, double interestRate) 
            throws InvalidAmountException, InsufficientBalanceException {
        super(accountNumber, holderName, balance);
        if (balance < MIN_BALANCE) {
            throw new InsufficientBalanceException("Số dư khởi tạo tài khoản tiết kiệm phải từ " 
                    + String.format("%,.0f", MIN_BALANCE) + " VNĐ trở lên!");
        }
        this.interestRate = interestRate;
    }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Số tiền rút phải lớn hơn 0!");
        }
        if (getBalance() - amount < MIN_BALANCE) {
            throw new InsufficientBalanceException("Rút tiền thất bại! Số dư tối thiểu còn lại phải duy trì từ " 
                    + String.format("%,.0f", MIN_BALANCE) + " VNĐ.");
        }
        setBalance(getBalance() - amount);
    }

    @Override
    public String toString() {
        return super.toString() + " | Lãi suất: " + interestRate + "%/năm (TK Tiết kiệm)";
    }
}
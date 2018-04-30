package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private long DAY_IN_MS = 1000 * 60 * 60 * 24;

    private final int accountType;
    List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (!hasWithdrawalInPrevious10Days())
                    return amount * 0.05;
                return amount * 0.001;
                /*if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;*/
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

    private boolean hasWithdrawalInPrevious10Days() {
        Date tenDaysAgoDate = new Date(System.currentTimeMillis() - (10 * DAY_IN_MS));
        Date nowDate = new Date();
        List<Transaction> transactions = returnTransactions(tenDaysAgoDate, nowDate);
        for (Transaction transaction: transactions) {
            if (transaction.amount < 0) return true;
        }
        return false;
    }

    public List<Transaction> returnTransactions(Date minDate, Date maxDate) {
        ArrayList<Transaction> resultTransactions = new ArrayList<Transaction>();
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionDate().compareTo(minDate) >= 0
                && transaction.getTransactionDate().compareTo(maxDate) < 0) {
                resultTransactions.add(transaction);
            } else if (transaction.getTransactionDate().compareTo(maxDate) >= 0) {
                break;
            }
        }
        return resultTransactions;
    }

}

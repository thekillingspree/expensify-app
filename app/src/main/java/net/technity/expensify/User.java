/*
 * Copyright (c) Ajesh, Alpesh, Arjun
 */

package net.technity.expensify;

public class User {
    String userName, uid;
    double initialBalance, currentBalance, totalExpenses, totalIncome;

    public User(String userName, String uid, double initialBalance) {
        this.userName = userName;
        this.uid = uid;
        this.initialBalance = initialBalance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }
}

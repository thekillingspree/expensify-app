/*
 * Copyright (c) Ajesh, Alpesh, Arjun
 */

package net.technity.expensify;

import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Expense {
    private String eTitle, eNotes;
    private boolean isSpent;
    private double eAmount;
    private long createdAt;

    public Expense() {

    }

    public Expense (String t, String n, double amt, long createdAt,
                    boolean isSpent) {
        this.eTitle = t;
        this.eNotes = n;
        this.eAmount = amt;
        this.createdAt = createdAt;
        this.isSpent = isSpent;
    }

    public boolean isSpent() {
        return isSpent;
    }

    public void setSpent(boolean spent) {
        isSpent = spent;
    }

    public String geteTitle() {
        return eTitle;
    }

    public void seteTitle(String eTitle) {
        this.eTitle = eTitle;
    }

    public String geteNotes() {
        return eNotes;
    }

    public void seteNotes(String eNotes) {
        this.eNotes = eNotes;
    }

    public double geteAmount() {
        return eAmount;
    }

    public void seteAmount(double eAmount) {
        this.eAmount = eAmount;
    }

    public String getStringFromAmount() {
        String spent = this.isSpent ? "-₹" : "+₹";
        return spent + String.format("%.2f", this.eAmount);
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getCreatedAtString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM, yyyy");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(this.createdAt);
        return simpleDateFormat.format(c.getTime());
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}

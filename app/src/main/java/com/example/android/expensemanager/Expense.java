package com.example.android.expensemanager;

import java.io.Serializable;

/**
 * Created by ralph on 09/09/17.
 */

public class Expense implements Serializable{

    private String title;
    private int amount;
    private int id;

    public Expense(String title, int amount) {
        this.title = title;
        this.amount = amount;
    }

    public Expense(String title, int amount, int id) {
        this.title = title;
        this.amount = amount;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

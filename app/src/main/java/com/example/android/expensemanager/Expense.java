package com.example.android.expensemanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by ralph on 09/09/17.
 */

@Entity(tableName = "expenses")
public class Expense implements Serializable{


    @Ignore
    private String title;


    private int amount;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public Expense(){}

    @Ignore
    public Expense(String title, int amount) {
        this.title = title;
        this.amount = amount;
    }

    @Ignore
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

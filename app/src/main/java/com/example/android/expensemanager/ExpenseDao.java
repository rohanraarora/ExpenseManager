package com.example.android.expensemanager;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by ralph on 18/10/17.
 */

@Dao
public interface ExpenseDao {

    @Query("SELECT * FROM expenses")
    List<Expense> getExpenses();

    @Query("SELECT * FROM expenses WHERE id = :expenseId")
    Expense getExpense(long expenseId);


    @Insert()
    long addExpense(Expense expense);

    @Update()
    void updateExpense(Expense expense);

    @Delete()
    void deleteExpense(Expense expense);

    @Insert()
    void addTwoExpenses(List<Expense> expenses);


}

package com.example.android.expensemanager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by ralph on 18/10/17.
 */

@Database(entities = {Expense.class},version = 1)
public abstract class ExpenseDatabase extends RoomDatabase {

    private static ExpenseDatabase INSTANCE;

    public static ExpenseDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ExpenseDatabase.class,"expenseRoomDb")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }


    public abstract ExpenseDao getExpenseDao();


}

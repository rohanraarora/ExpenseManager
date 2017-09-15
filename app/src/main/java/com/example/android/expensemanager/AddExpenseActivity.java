package com.example.android.expensemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddExpenseActivity extends AppCompatActivity {

    public static final int ADD_SUCCESS = 1;

    EditText title;
    EditText amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        title = (EditText)findViewById(R.id.titleEdit);
        amount = (EditText) findViewById(R.id.amountEdit);
    }

    public void add(View view){
        String titleText = title.getText().toString();
        String amountText = amount.getText().toString();
        int amount = Integer.parseInt(amountText);
        Expense expense = new Expense(titleText,amount);
        Intent result = new Intent();
        result.putExtra(Constant.KEY_EXPENSE,expense);
        setResult(ADD_SUCCESS, result);
        finish();
    }
}

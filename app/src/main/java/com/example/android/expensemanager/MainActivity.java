package com.example.android.expensemanager;

import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.jar.Manifest;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.example.android.expensemanager.Constant.KEY_EXPENSE;
import static com.example.android.expensemanager.Constant.KEY_EXPENSE_ID;
import static com.example.android.expensemanager.Constant.KEY_POSITION;
import static com.example.android.expensemanager.Constant.KEY_TITLE;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Expense> expenses;
    CustomAdapter adapter;


    public final static int REQUEST_DETAIL = 1;
    public final static int REQUEST_ADD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        expenses = new ArrayList<>();


//        ExpenseOpenHelper openHelper = ExpenseOpenHelper.getInstance(getApplicationContext());
//        SQLiteDatabase db = openHelper.getReadableDatabase();
//
//        Cursor cursor = db.query(Contract.EXPENSE_TABLE_NAME,null,null,null,null,null,null);
//        db.delete(Contract.EXPENSE_TABLE_NAME,Contract.EXPENSE_ID + " = ?",new String[]{"1"});

//        String[] columns = new String[]{Contract.EXPENSE_TITLE};
//        String[] selectionArgs = new String[]{"100","1000"};
//        db.query(Contract.EXPENSE_TABLE_NAME,columns,Contract.EXPENSE_AMOUNT + " > ? AND "
//                + Contract.EXPENSE_AMOUNT + " < ?",selectionArgs,null,null,
//                Contract.EXPENSE_AMOUNT + " DESC");
//
//        while (cursor.moveToNext()){
//
//            String title = cursor.getString(cursor.getColumnIndex(Contract.EXPENSE_TITLE));
//            int amount = cursor.getInt(cursor.getColumnIndex(Contract.EXPENSE_AMOUNT));
//            int id = cursor.getInt(cursor.getColumnIndex(Contract.EXPENSE_ID));
//            Expense expense = new Expense(title,amount,id);
//            expenses.add(expense);
//
//
//        }
//
//        cursor.close();

        final ExpenseDatabase database = ExpenseDatabase.getInstance(this);
        expenses.addAll(database.getExpenseDao().getExpenses());


        adapter = new CustomAdapter(this, expenses, new CustomAdapter.DeleteButtonClickListener() {
            @Override
            public void onDeleteClicked(int position,View view) {
                database.getExpenseDao().deleteExpense(expenses.get(position));
                expenses.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Expense expense = expenses.get(i);
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);

                intent.putExtra(KEY_EXPENSE,expense);
                intent.putExtra(KEY_POSITION,i);

                startActivityForResult(intent,REQUEST_DETAIL);
            }
        });

    }


    @Override
    protected void onStart() {
//        br = new CustomBroadcastReceiver();
//        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_LOW);
//        registerReceiver(br,intentFilter);

        super.onStart();
//        Log.d("Lifecycle Event","Start");
    }

    @Override
    protected void onStop() {
       //aunregisterReceiver(br);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {

            Intent add = new Intent(this,AddExpenseActivity.class);
            startActivityForResult(add,REQUEST_ADD);



        }else if(id == R.id.remove){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Expense");
            builder.setMessage("Are you sure?");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    expenses.remove(expenses.size() -1);
                    adapter.notifyDataSetChanged();

                }
            });
            builder.setNegativeButton("No", null);

            AlertDialog dialog = builder.create();
            dialog.show();

        }

        if( id == R.id.about){

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            String url = "http://codingninjas.in";
            intent.setData(Uri.parse(url));
            startActivity(intent);

        }else if(id == R.id.feedback){

            Intent feedback = new Intent();
            feedback.setAction(Intent.ACTION_SENDTO);
            feedback.setData(Uri.parse("mailto:rohan@codingninjas.in"));
            feedback.putExtra(Intent.EXTRA_TEXT,"FEEDBACK");
            if(feedback.resolveActivity(getPackageManager()) != null){
                startActivity(feedback);
            }
            else {

            }
        }else  if(id == R.id.share){

            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT,"DOWNLOAD our app");
            Intent chooser = Intent.createChooser(share,"Share App");
            startActivity(chooser);

        }
        else if(id == R.id.call){

            int permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE);
            if(permission == PERMISSION_GRANTED){
                Intent call = new Intent();
                call.setAction(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:9999999"));
                startActivity(call);
            }
            else {
              //  ActivityCompat.requestPermissions();
            }


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_DETAIL){

            if(resultCode == DetailActivity.RESULT_SAVE){


                int position = data.getIntExtra(KEY_POSITION,0);
                String title = data.getStringExtra(KEY_TITLE);

                Expense expense = expenses.get(position);
                expense.setTitle(title);
                adapter.notifyDataSetChanged();
            }
        }else if(requestCode == REQUEST_ADD){
            if(resultCode == AddExpenseActivity.ADD_SUCCESS){
                long id   =  data.getLongExtra(KEY_EXPENSE_ID,-1L);
                if(id > -1){
//                    ExpenseOpenHelper openHelper = ExpenseOpenHelper.getInstance(getApplicationContext());
//                    SQLiteDatabase db = openHelper.getReadableDatabase();
//
//                    Cursor cursor = db.query(Contract.EXPENSE_TABLE_NAME,null,
//                            Contract.EXPENSE_ID + " = ?",new String[]{id + ""}
//                            ,null,null,null);
//
//                    if(cursor.moveToFirst()){
//                        String title = cursor.getString(cursor.getColumnIndex(Contract.EXPENSE_TITLE));
//                        int amount = cursor.getInt(cursor.getColumnIndex(Contract.EXPENSE_AMOUNT));
//                        Expense expense = new Expense(title,amount,(int)id);
//                        expenses.add(expense);


                    ExpenseDatabase database = ExpenseDatabase.getInstance(this);
                    Expense expense = database.getExpenseDao().getExpense(id);
                    expenses.add(expense);
                    adapter.notifyDataSetChanged();

                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

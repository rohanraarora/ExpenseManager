package com.example.android.expensemanager;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ralph on 09/09/17.
 */

public class CustomAdapter extends ArrayAdapter<Expense> {


    Context mContext;
    ArrayList<Expense> mItems;
    DeleteButtonClickListener mDeleteButtonClickListener;

    public CustomAdapter(@NonNull Context context, ArrayList<Expense> expenses,DeleteButtonClickListener deleteButtonClickListener) {
        super(context, 0);

        mContext = context;
        mItems = expenses;
        mDeleteButtonClickListener = deleteButtonClickListener;

    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.detail_row_layout,null);
             viewHolder = new ViewHolder();
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView amount = (TextView) convertView.findViewById(R.id.amount);
            Button button = (Button)convertView.findViewById(R.id.deleteButton);
            viewHolder.button = button;
            viewHolder.title = title;
            viewHolder.amount = amount;

            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder)convertView.getTag();
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                expenses.remove(position);
//                adapter.notifyDataSetChanged();
               mDeleteButtonClickListener.onDeleteClicked(position,view);
            }
        });
        Expense item = mItems.get(position);
        viewHolder.title.setText(item.getTitle());
        viewHolder.amount.setText(item.getAmount() +"");
        return convertView;
    }


    static class ViewHolder {

        TextView title;
        TextView amount;
        Button button;

    }


    static interface DeleteButtonClickListener {

        void onDeleteClicked(int position,View v);

    }
}

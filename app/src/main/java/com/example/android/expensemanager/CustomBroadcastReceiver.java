package com.example.android.expensemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class CustomBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        if(action.equals(Intent.ACTION_BATTERY_LOW)) {
            Log.d("Battery Receiver", "Battery Low");
        }
        else{
            Log.d("Battery Receiver", "Battery Okay");
        }
    }

}

package com.example.jerry.alarmclockyoutube;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("We are in!","Finally");

        String getString = intent.getExtras().getString("extra");

        Log.e("Key: ",getString);

        Long getChoice = intent.getExtras().getLong("Sound Choice");

        Log.e("Sound choice: ", getChoice.toString());

        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

        serviceIntent.putExtra("extra",getString);

        serviceIntent.putExtra("Sound Choice", getChoice );

        context.startService(serviceIntent);
    }

}

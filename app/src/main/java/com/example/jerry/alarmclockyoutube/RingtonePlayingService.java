package com.example.jerry.alarmclockyoutube;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class RingtonePlayingService extends Service {

    MediaPlayer mediaSong;
    boolean isRunning;
    int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {

        Log.e("hello?", "hey");

        String state = intent.getExtras().getString("extra");

        Log.e("Ringstone state: extra is ", state);

        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        if(!this.isRunning && startId == 1) {
            Log.e("There is no music, " , " and you want start");
            mediaSong = MediaPlayer.create(this, R.raw.thebeach);
            mediaSong.start();

            this.isRunning=true;
            this.startId=0;
        }
        else if (this.isRunning && startId == 0) {
            Log.e("There is music, " , " and you want end");

            mediaSong.stop();
            mediaSong.reset();

            this.isRunning=false;
            this.startId=0;

        }
        else if (!this.isRunning && startId == 0) {
            Log.e("There is no music, " , " and you want end");
                this.isRunning=false;
                this.startId=0;
        }
        else if (this.isRunning && startId == 1) {
            Log.e("There is music, " , " and you want start");
            this.startId=1;
            this.isRunning=true;

        }
        else {
            Log.e("How the hell did you get here ", "???");

        }

        return START_NOT_STICKY;

    };

    @Override
    public void onDestroy() {
        Toast.makeText(this, "on destroy called", Toast.LENGTH_SHORT).show();
    }
}

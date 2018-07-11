package com.example.jerry.alarmclockyoutube;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;


import android.icu.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    TimePicker  timePicker;
    TextView updateText;

    Context context;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

        timePicker = (TimePicker) findViewById(R.id.TimePicker);

        updateText = (TextView) findViewById(R.id.AlarmCheck);

        final Calendar calendar = Calendar.getInstance();

        Button startAlarm = (Button) findViewById(R.id.AlarmOn);

       final Intent myIntent = new Intent(this.context,AlarmReciever.class);

        startAlarm.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)

            @Override
            public void onClick(View view) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();


                calendar.set(Calendar.HOUR,timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());



                String hourString = String.valueOf(hour);
                String minuteString = String.valueOf(minute);

                if (minute < 10) {
                    minuteString = "0" + String.valueOf(minute);
                }

                setAlarmText("Alarm On: " + hourString +":"+minuteString);

                myIntent.putExtra("extra", "alarm on");

                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT );

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

        Button endAlarm = (Button) findViewById(R.id.AlarmOff);
        endAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setAlarmText("Alarm Off");

                assert(pendingIntent!=null);

                alarmManager.cancel(pendingIntent);

                myIntent.putExtra("extra", "off");

                sendBroadcast(myIntent);

            }
        });



    }

    private void setAlarmText(String alarm_on) {
        updateText.setText(alarm_on);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

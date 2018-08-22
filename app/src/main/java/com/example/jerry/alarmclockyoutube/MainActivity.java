package com.example.jerry.alarmclockyoutube;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;


import android.icu.util.Calendar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    File media;

    AlarmManager alarmManager;
    TimePicker  timePicker;
    TextView updateText;
    long chooseMusic;

    Context context;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        media = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String currentSong;
        ArrayList<HashMap<String, String >> songsList = new ArrayList<HashMap<String, String>>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(media));
            while(reader.readLine()!=null) {
                currentSong = reader.readLine();
                Log.e("Current song: " ,currentSong);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);
        this.context = this;

        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

        timePicker = (TimePicker) findViewById(R.id.TimePicker);

        updateText = (TextView) findViewById(R.id.AlarmCheck);

        final Calendar calendar = Calendar.getInstance();

        Button startAlarm = (Button) findViewById(R.id.AlarmOn);

       final Intent myIntent = new Intent(MainActivity.this,AlarmReciever.class);

        startAlarm.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.M)

                @Override
                public void onClick(View view) {

                // setting calendar instance with the hour and minute that we picked
                // on the time picker
                calendar.set(java.util.Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(java.util.Calendar.MINUTE, timePicker.getMinute());

                // get the int values of the hour and minute
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                // convert the int values to strings
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                // convert 24-hour time to 12-hour time
                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                }

                if (minute < 10) {
                    //10:7 --> 10:07
                    minute_string = "0" + String.valueOf(minute);
                }

                setAlarmText("Alarm On: " + hour +":"+minute_string);

                Log.e("We have reached:  ", "Alarm On Button");


                myIntent.putExtra("extra", "alarm on");

                myIntent.putExtra("Sound Choice", chooseMusic);

                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT );

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.selector);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.musicarray,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        Button endAlarm = (Button) findViewById(R.id.AlarmOff);
        endAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                setAlarmText("Alarm Off");



                if(pendingIntent!=null) {
                    alarmManager.cancel(pendingIntent);
                }

                myIntent.putExtra("extra", "off");

                myIntent.putExtra("Sound Choice", chooseMusic);

                sendBroadcast(myIntent);

            }
        });



    }

    private void setAlarmText(String alarm_on) {
        updateText.setText(alarm_on);
    }

    class FileExtensionFilter implements FilenameFilter
    {
        public boolean accept(File dir, String name)
        {
            return (name.endsWith(".mp3") || name.endsWith(".MP3")) || name.endsWith(".MP4") || name.endsWith(".mp4");
        }
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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        chooseMusic = l;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

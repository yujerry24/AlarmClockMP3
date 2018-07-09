package com.example.jerry.alarmclockyoutube;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    Calendar calendar;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

        timePicker = (TimePicker) findViewById(R.id.TimePicker);

        updateText = (TextView) findViewById(R.id.AlarmCheck);

        calendar = Calendar.getInstance();

        Button startAlarm = (Button) findViewById(R.id.AlarmOn);
        startAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setAlarmText("Alarm On");
            }
        });

        Button endAlarm = (Button) findViewById(R.id.AlarmOff);
        endAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setAlarmText("Alarm Off");

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

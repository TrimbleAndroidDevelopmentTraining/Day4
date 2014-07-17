package com.trimble.servicesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;


public class LocalServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_service);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.local, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startChronometer() {
        Chronometer chrono = (Chronometer)findViewById(R.id.chronometer);
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
    }

    private void stopChronometer() {
        ((Chronometer)findViewById(R.id.chronometer)).stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopChronometer();
    }

    public void simpleIntentService_Button_onClick(View view) {
        String msg = "HelloService";
        Intent msgIntent = new Intent(this, SimpleIntentService.class);
        msgIntent.putExtra(SimpleIntentService.PARAM_IN_MSG, msg);

        startChronometer();

        startService(msgIntent);
    }

    public void simpleStartedBoundService_Button_onClick(View view) {
        String msg = "HelloService";
        Intent msgIntent = new Intent(this, SimpleStartedBoundService.class);
        msgIntent.putExtra(SimpleStartedBoundService.PARAM_IN_MSG, msg);

        startChronometer();

        startService(msgIntent);
    }
}

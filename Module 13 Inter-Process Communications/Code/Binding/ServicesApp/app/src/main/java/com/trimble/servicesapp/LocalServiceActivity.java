package com.trimble.servicesapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;


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

    public void intentService_Button_onClick(View view) {
        String msg = "HelloService";
        Intent msgIntent = new Intent(this, SimpleIntentService.class);
        msgIntent.putExtra(SimpleIntentService.PARAM_IN_MSG, msg);

        startChronometer();

        startService(msgIntent);
    }

    public void startedService_Button_onClick(View view) {
        String msg = "HelloService";
        Intent msgIntent = new Intent(this, SimpleStartedBoundService.class);
        msgIntent.putExtra(SimpleStartedBoundService.PARAM_IN_MSG, msg);

        startChronometer();

        startService(msgIntent);
    }

    // New Stuff
    private SimpleStartedBoundService service;
    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder binder) {
            SimpleStartedBoundService.SimpleStartedBoundServiceBinder serviceBinder =
                    (SimpleStartedBoundService.SimpleStartedBoundServiceBinder)binder;
            service = serviceBinder.getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            service = null;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent= new Intent(this, SimpleStartedBoundService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopChronometer();

        unbindService(mConnection);
    }

    public void boundService_Button_onClick(View view) {
        if (service != null) {
            String info = service.getTime();
            ((TextView)findViewById(R.id.time_text_view)).setText(info);
        }
    }
}

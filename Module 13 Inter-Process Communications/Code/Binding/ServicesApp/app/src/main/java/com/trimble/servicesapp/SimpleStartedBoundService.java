package com.trimble.servicesapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.format.DateFormat;

public class SimpleStartedBoundService extends Service {

    public static final String PARAM_IN_MSG = "imsg";

    private final IBinder mBinder = new SimpleStartedBoundServiceBinder();
    public class SimpleStartedBoundServiceBinder extends Binder {
        SimpleStartedBoundService getService() {
            return SimpleStartedBoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public String getTime() {
        String results =  DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis()).toString();
        return results;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String msg = intent.getStringExtra(PARAM_IN_MSG);
        SystemClock.sleep(5000); // 5 seconds
        sendResultsUsingActivityIntent(msg);

        stopSelf();

        return START_NOT_STICKY;
    }

    private void sendResultsUsingActivityIntent(String resultMsg) {
        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.putExtra(ResultActivity.PARAM_OUT_MSG, resultMsg);

        // Because we are staring an Activity from outside an existing Activity
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(resultIntent);
    }
}

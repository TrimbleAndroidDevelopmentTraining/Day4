package com.trimble.servicesapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class SimpleStartedBoundService extends Service {

    public static final String PARAM_IN_MSG = "imsg";

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

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

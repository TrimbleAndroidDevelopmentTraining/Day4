package com.trimble.servicesapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.text.format.DateFormat;

public class SimpleIntentService extends IntentService {

    public static final String PARAM_IN_MSG = "imsg";

    public SimpleIntentService() {
        super("SimpleIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String msg = intent.getStringExtra(PARAM_IN_MSG);
        SystemClock.sleep(5000); // 5 seconds
        sendResultsUsingActivityIntent(msg);
    }

    private void sendResultsUsingActivityIntent(String resultMsg) {
        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.putExtra(ResultActivity.PARAM_OUT_MSG, resultMsg);

        // Because we are staring an Activity from outside an existing Activity
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(resultIntent);
    }
}

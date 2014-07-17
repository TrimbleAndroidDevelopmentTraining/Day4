package com.trimble.servicesapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.widget.Toast;

public class SimpleStartedBoundService extends Service {

    public static final String PARAM_IN_MSG = "imsg";
    public static final String EXTRA_MESSENGER = "messenger";
    public static final int MSG_SAY_HELLO = 1;

    private final IBinder mBinder = new SimpleStartedBoundServiceBinder();
    public class SimpleStartedBoundServiceBinder extends Binder {
        SimpleStartedBoundService getService() {
            return SimpleStartedBoundService.this;
        }
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
                    SendMessage();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    final Messenger inMessenger = new Messenger(new IncomingHandler());
    Messenger outMessenger;

    @Override
    public IBinder onBind(Intent intent) {
        outMessenger = intent.getParcelableExtra(EXTRA_MESSENGER);
        return inMessenger.getBinder();
    }

    private void SendMessage() {
        if (outMessenger != null) {
            Message msg = Message.obtain(null, LocalServiceActivity.MSG_SAY_BYE, 0, 0);
            try {
                outMessenger.send(msg);
            } catch (RemoteException e) {
                Toast.makeText(getApplicationContext(), "MessengerService Error!", Toast.LENGTH_SHORT).show();
            }
        }
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

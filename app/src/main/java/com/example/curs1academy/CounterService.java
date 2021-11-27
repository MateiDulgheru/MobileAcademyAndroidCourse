package com.example.curs1academy;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class CounterService extends Service {
    public static final String TAG=CounterService.class.getName();
    public static final String ACTION_COUNT="Orice";
    public CounterService() { }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent==null) return START_NOT_STICKY;

        if(intent.getAction()==ACTION_COUNT){
            startCounterThread();
            return START_REDELIVER_INTENT | START_STICKY;
        }
        return START_NOT_STICKY;
    }

    public void count(){
        for(int i=0; i<5; i++){

            try {
                Thread.sleep(1000);
                Log.d(TAG,"count = "+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LocalBroadcastManager.getInstance(MobileActivityApplication.getAppContext()).sendBroadcast(new Intent(MainActivity.ACTION_TIME_IS_UP));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"serviciu omorat");
    }

    public void startCounterThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                count();
            }
        }).start();
    }
}
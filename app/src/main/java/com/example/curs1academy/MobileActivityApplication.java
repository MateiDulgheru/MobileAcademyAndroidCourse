package com.example.curs1academy;

import android.app.Application;
import android.content.Context;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;


public class MobileActivityApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context  getAppContext() {
        return context;
    }

    public void setUpWorker(){
        Constraints constraints=new Constraints.Builder().
                setRequiredNetworkType(NetworkType.UNMETERED).
                setRequiresCharging(true).build();
        WorkRequest workRequest=new PeriodicWorkRequest.Builder(
                DataDownloadWorker.class,
                1, TimeUnit.HOURS,
                10,TimeUnit.MINUTES).setConstraints(constraints).build();

        WorkManager.getInstance(this).enqueue(workRequest);
    }


}

package com.damian.salonapp.services;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.damian.salonapp.services.networking.DataSocket;

/**
 * Created by damian on 3/6/17.
 */

public class NetworkingService extends IntentService {


    private DataSocket dataSocket;
    private MyBroadcastReceiver mBroadcastReceiver;
    /* the ctor passes a name to the service being run.
    *   used prolly for debugging.
    */
    public NetworkingService(){
        super("SALON_NW_SERVICE");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        /* handle your intent here */
        String data=intent.getDataString();
        intent.setClass(getApplicationContext(),MyBroadcastReceiver.class);
        intent.setAction(Intent.ACTION_SEND);
        System.out.println("doing somethign"+intent.getAction());

        this.dataSocket=new DataSocket();
        this.dataSocket.waitAndProcess();
        System.out.println("will be doing some stuff...hopefully");

    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
        System.out.println("in setIntentRedelivery");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mBroadcastReceiver=new MyBroadcastReceiver();
        getApplicationContext().registerReceiver(mBroadcastReceiver,new IntentFilter(Intent.ACTION_SEND));
        System.out.println("in onCreate Service");

    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        System.out.println("in onStartService");

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        System.out.println("in onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }


    /* closing the ServerSocket here*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("in on destroy");
        this.dataSocket.stopEverything();
        if(mBroadcastReceiver!=null) {
            this.getApplicationContext().unregisterReceiver(mBroadcastReceiver);
            Log.d("NetworkingService","unregistering broadcast receiver");
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("IN ONBIND");
        return super.onBind(intent);
    }
}

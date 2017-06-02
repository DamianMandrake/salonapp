package com.damian.salonapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.damian.salonapp.services.networking.DataSocket;

/**
 * Created by damian on 3/6/17.
 */

public class NetworkingService extends IntentService {


    private DataSocket dataSocket;
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
        System.out.println("doing somethign");

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
        System.out.println("in onCreate Service");

    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        System.out.println("in onStartService");

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        System.out.println("in onStartComman");
        return super.onStartCommand(intent, flags, startId);

    }


    /* closing the ServerSocket here*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("in on destroy");
        this.dataSocket.stopEverything();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("IN ONBIND");
        return super.onBind(intent);
    }
}

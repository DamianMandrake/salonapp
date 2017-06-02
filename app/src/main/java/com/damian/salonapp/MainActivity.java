package com.damian.salonapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.damian.salonapp.services.networking.SetSomeText;
import com.damian.salonapp.services.NetworkingService;

public class MainActivity extends AppCompatActivity implements SetSomeText {

    private TextView ipText;
    private final static int MSUCCESS=4;
    public static String TEXT_VIEW_TXT="Type this ip address into your pc client";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("creating and sending intent");

        ipText=(TextView) findViewById(R.id.ip_text_view);
        com.damian.salonapp.services.networking.DataSocket.ref=this;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_DENIED){
            //starting the service
            this.launchService();
        }else{
            this.requestPerm();
        }


    }


    private void launchService(){
        Intent intent=new Intent(this, NetworkingService.class);
        intent.setData(Uri.parse("random"));//dont need this
        this.startService(intent);


    }
    private void requestPerm(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},MSUCCESS);

    }

    /* Overriding SetSomeText's method*/
    @Override
    public void setSomeText(final String text){
        /* since only ui thread is allowed to access ui elements*/
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.ipText.setText(text);
            }
        });
    }



    /* Overrding onRequestPermissionsResult- callback to the dialog box given to the user once he reacts to it*/

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode){
            case MainActivity.MSUCCESS:
                //if req is denied then the array is empty
                if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                            this.launchService();
                }else{
                    this.setSomeText("Permissions are required for the proper functioning of the app");
                    this.requestPerm();
                }
        }

    }

}

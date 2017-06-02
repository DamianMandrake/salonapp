package com.damian.salonapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.damian.salonapp.services.NetworkingService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("creating and sending intent");
        Intent intent=new Intent(this, NetworkingService.class);
        intent.setData(Uri.parse("random"));
        this.startService(intent);
    }
}

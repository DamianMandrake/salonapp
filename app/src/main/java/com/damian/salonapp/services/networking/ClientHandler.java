package com.damian.salonapp.services.networking;

import android.telephony.SmsManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by damian on 3/6/17.
 */

public class ClientHandler extends Thread{
    private Socket socket;
    public ClientHandler(Socket socket){
        this.socket=socket;
        this.start();
    }

    /* TODO read input data and to send sms to someone*/
    @Override
    public void run(){
        try {
            PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
            String msg=this.sendSms(this.getDataFromSource());
            pw.println(msg);
            Log.d("ClientHandler","reply sent is "+msg);
            pw.close();
            this.socket.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    private JSONObject getDataFromSource(){
        try{

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder stringBuilder=new StringBuilder();
            String temp;
            while((temp=bufferedReader.readLine())!=null)
                stringBuilder.append(temp);
            Log.d("ClientHandler","received data "+stringBuilder.toString());
            bufferedReader.close();
            return new JSONObject(stringBuilder.toString());
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch (JSONException jse){
            jse.printStackTrace();
        }
        return null;
    }

    private String sendSms(JSONObject jsonObject){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            Log.d("ClientHandler","Sending message");
            smsManager.sendTextMessage(jsonObject.get("phone").toString(), null, jsonObject.get("message").toString(), null, null);
            return "sent";
        }catch (JSONException jse){
            jse.printStackTrace();
        }
        return "issue";
    }

}

package com.damian.salonapp.services.networking;

import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import com.damian.salonapp.services.sentto.SentTo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by damian on 3/6/17.
 */

public class ClientHandler extends Thread{
    private Socket socket;
    public static AddToList addToList;
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

            //PendingIntent pendingIntent = PendingIntent.getBroadcast(this,444,new Intent(),PendingIntent.FLAG_UPDATE_CURRENT);
            String msg=jsonObject.get("message").toString(),ph=jsonObject.get("phone").toString();
            ArrayList<String> arrayList=smsManager.divideMessage(msg);





            //destnuum, sc,arraylist of strings,sentIntents,receivedIntents



            smsManager.sendMultipartTextMessage(ph,null,arrayList,null,null);

            //calling add to list

            addToList.addToList(new SentTo(msg,ph,new Date()));


            return "sent";
        }catch (JSONException jse){
            jse.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "issue";
    }

}

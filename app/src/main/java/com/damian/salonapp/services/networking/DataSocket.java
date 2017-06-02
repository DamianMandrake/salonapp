package com.damian.salonapp.services.networking;

import android.util.Log;

import java.io.IOError;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by damian on 3/6/17.
 */

public class DataSocket  {
    private ServerSocket mServerSocket;
    private static int mDefaultPort=4444;
    private static int mDefaultQueSize=10;
    //private Thread thread;
    public DataSocket(){
        try {
            this.mServerSocket = new ServerSocket(DataSocket.mDefaultPort, DataSocket.mDefaultQueSize, InetAddress.getLocalHost());

        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /* Handling all requests from the pcs to send sms to the client here*/

    public void waitAndProcess(){
        try{
            Log.d("DataSocket","waiting for connection");
            Socket client=this.mServerSocket.accept();
            Log.d("DataSocket","someone just conencted");
            ClientHandler clientHandler=new ClientHandler(client);
        }catch (IOException io){
            io.printStackTrace();
        }
    }


    public void stopEverything(){
        try {
            this.mServerSocket.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }






}

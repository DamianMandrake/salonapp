package com.damian.salonapp.services.networking;

import android.app.Activity;
import android.util.Log;

import com.damian.salonapp.MainActivity;

import java.io.IOError;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by damian on 3/6/17.
 */

public class DataSocket  {
    private ServerSocket mServerSocket;
    private static int mDefaultPort=4444;
    private static int mDefaultQueSize=10;

    public static SetSomeText ref=null;
    public DataSocket(){
        try {
            InetAddress inetAddress=this.getIpAdress();
            if(inetAddress==null)
                throw new IOException();
            this.mServerSocket = new ServerSocket(DataSocket.mDefaultPort, DataSocket.mDefaultQueSize, inetAddress);
            ref.setSomeText(MainActivity.TEXT_VIEW_TXT+inetAddress.toString());

        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /* Handling all requests from the pcs to send sms to the client here*/

    public void waitAndProcess(){
        while (true)
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





    /* since InetAddress.getLocalHost() returns the actual localhost
    * Traversing the NetworkInterface enum and obtaining the real ip address if its on
    * a network
    * */
    private InetAddress getIpAdress(){
        try{
        Enumeration<NetworkInterface> networkInterfaceEnumeration=NetworkInterface.getNetworkInterfaces();
            while(networkInterfaceEnumeration.hasMoreElements()){
                NetworkInterface ni=networkInterfaceEnumeration.nextElement();

                Enumeration<InetAddress> inetAddressEnumeration=ni.getInetAddresses();
                while(inetAddressEnumeration.hasMoreElements()){
                    InetAddress ia=inetAddressEnumeration.nextElement();
                    if(!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address)
                        return ia;
                }
            }
        }catch (SocketException se){
            se.printStackTrace();
            Log.d("DataSocket","unable to obtain ip...Check network settings");
        }
        return null;
    }




}

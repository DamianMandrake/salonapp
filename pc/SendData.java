package pc;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
public class SendData{
	private Socket mSocket;
	private static int port=4444;
	public SendData(String dest){
		try{
		this.mSocket=new Socket(dest,port);
		}catch(IOException se){
			se.printStackTrace();
		}
	}


	public void send(){
		new Thread(new Runnable(){

			@Override
			public void run(){
				try{
					BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
					System.out.println("Enter number to be sent to");
					String num=buf.readLine().trim();
					buf.close();
					//System.out.println("Enter msg that has to be sent");//will prolly be constant... idk
					String data="some msg";
					PrintWriter pw=new PrintWriter(SendData.this.mSocket.getOutputStream());
					pw.println("{phone:\""+num+"\",message:\""+data+"\"}");
					pw.close();
					
					buf=new BufferedReader(new InputStreamReader(SendData.this.mSocket.getInputStream()));
					String x;
					while((x=buf.readLine())!=null)
						System.out.println("Phone reply : "+x);

					buf.close();
				}catch (IOException e) {
					e.printStackTrace();
				}


			}

		}).start();


	}

	public static void main(String arp[]){
		new SendData(arp[0]).send();
	}

}
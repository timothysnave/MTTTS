package socketComm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
	private int localCode, remoteCode, port;
	private ServerSocket ss;
	private Socket s;
	private DatagramSocket ds;
	private DataOutputStream out;
	private DataInputStream in;
	private boolean connected;
	public static enum protocol{TCP, UDP};
	private protocol p;
	
	public Server(int port, int localCode, int remoteCode) // TCP by default
	{
		// Set up variables
		this.localCode = localCode;
		this.remoteCode = remoteCode;
		this.port = port;
		connected = false;
		p = protocol.TCP;
		
		try {
			ss = new ServerSocket(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Server(int port, int localCode, int remoteCode, protocol p) // Choose between TCP and UDP
	{
		// Set up variables
		this.localCode = localCode;
		this.remoteCode = remoteCode;
		connected = false;
		this.p = p;
		
		if (p==protocol.TCP)
		{
			try {
				ss = new ServerSocket(port);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void start()
	{
		if (p==protocol.TCP)
			startTCP();
		else if (p==protocol.UDP)
			startUDP();
	}
	
	private void startUDP()
	{
		System.out.println("Server started");
		
		try {
			ds = new DatagramSocket(port);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void startTCP()
	{
		try {
			
			while(!connected){
				System.out.println("Server started");
				s = ss.accept();
				in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
				out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
				if (in.readInt()==remoteCode){
					out.writeInt(localCode);
					out.flush();
					System.out.println("Client contacted");
					s.close();
					in = null;
					out = null;
					if ((s=ss.accept()) != null) // get link address & stuff, check to see if its the right client
												// VERIFY WITH A CODE THE PURPOSE OF THE CONNECTION!!!
					{
						in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
						out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
						System.out.println("Client connected");
						connected = true;
					}
				}
				else{
					s.close();
					in = null;
					out = null;
				}
			}
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void send(int b)
	{
		try {
			out.write(b);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int receive()
	{
		int result;
		try {
			result = (int)in.readFloat();
		} catch (Exception e) {
			result = -1;
			try {
				s.close();
			} catch (IOException e1) {}
			in = null;
			out = null;
			connected = false;
			System.out.println("Connection closed");
		}
		return result;
	}
	
	public boolean isConnected()
	{
		return connected;
	}
	
	
}

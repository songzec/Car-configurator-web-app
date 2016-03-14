package server;

import java.net.*;
import java.util.Properties;

import server.BuildCarModelOptions;

import java.io.*;
/**
 * This class implements server, not client.
 * @author Songze Chen
 *
 */
public class DefaultSocketClient 
    			extends Thread implements SocketClientInterface,
                              				SocketClientConstants {
	private ObjectInputStream  reader;
	private ObjectOutputStream  writer;
	private Socket sock;
	private String strHost;
	private int iPort;


	public DefaultSocketClient(Socket sock) {
		this.sock = sock;
	}

	public void run(){
		if (openConnection()){
			handleSession();
			closeSession();
		}
	}
	
	public boolean openConnection(){
		
		try {
			writer = new ObjectOutputStream(sock.getOutputStream());
			reader = new ObjectInputStream(sock.getInputStream()); 
		} catch (Exception e){
			if (DEBUG) {
				System.err.println("Unable to obtain stream to/from " + strHost);
			}
			return false;
		}
		return true;
	}
	
	public void handleSession(){
		if (DEBUG) {
			System.out.println ("Handling session with "+ strHost + ":" + iPort);
		}
	
		try {
			Object object;
			while((object = reader.readObject()) != null) {
				handleInput(object);
			}
			sendOutput("Conversation end.");
		} catch (IOException e){
			if (DEBUG) {
				System.out.println ("Handling session with "+ strHost + ":" + iPort);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void sendOutput(Object object) throws IOException {
		writer.writeObject(object);
		writer.flush();
	}
	
	public void handleInput(Object object) throws IOException{
		
		BuildCarModelOptions build = new BuildCarModelOptions(); 
		String className = object.getClass().getSimpleName();
		if(className.equals("Properties")) {
			Properties prop = (Properties) object;
			build.buildAutoFromProperties(prop);
			sendOutput("An Automobile instance is successfully created...\n");
		} else if(className.equals("String")) {
			String request = (String) object;
			if(request.equals("configuration")) {
				sendOutput(build.getModelList());
			} else {
				sendOutput(build.getModelByRequest(request));
			}
		}
	}
	
	public void closeSession(){
		try {
			writer = null;
			reader = null;
			sock.close();
		} catch (IOException e) {
			if (DEBUG) {
				System.err.println("Error closing socket to " + strHost);
			}
		}
	}
	
	public void setHost(String strHost){
		this.strHost = strHost;
	}
	
	public void setPort(int iPort){
		this.iPort = iPort;
	}
}

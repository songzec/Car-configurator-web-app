package client;

import java.net.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import model.Automobile;

import java.io.*;
/**
 * This class implements client.
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


	public void run(){
		if (openConnection()){
			handleSession();
			closeSession();
		}
	}
	public boolean openConnection(){

		try {
			sock = new Socket(strHost, iPort);
		} catch(IOException socketError) {
			if (DEBUG)
				System.err.println("Unable to connect to " + strHost);
			return false; 
		}
		try {
			reader = new ObjectInputStream(sock.getInputStream()); 
			writer = new ObjectOutputStream(sock.getOutputStream()); 
		} catch (Exception e) {
			if (DEBUG) 
				System.err.println("Unable to obtain stream to/from " + strHost); 
			return false;
		}
		return true;
	}
	
	public void handleSession(){
		if (DEBUG) {
			System.out.println ("Handling session with "+ strHost + ":" + iPort);
		}
		Scanner scanner = new Scanner(System.in);
		String userInput;
		try {
			while(true) {
				
				System.out.println("Please input a number:");
				System.out.println("1.Upload a properties file");
				System.out.println("2.Configure a car.");
				System.out.println();
				userInput = scanner.nextLine();
				if(userInput == null) {
					sendOutput(null);
					System.out.println((String) reader.readObject());
					break;
				}
				handleInput(userInput);
			}
			scanner.close();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public void sendOutput(Object object) throws IOException {
		writer.writeObject(object);
		writer.flush();
	}
	
	public void handleInput(String userInput) throws IOException, ClassNotFoundException { 
		CarModelOptionsIO cmo = new CarModelOptionsIO(); 
		SelectCarOption sco = new SelectCarOption(); 
		Scanner scanner = new Scanner(System.in);
		
		if(userInput.equals("1")) {
			System.out.println("Please input a file path:");
			String fileName = scanner.nextLine();
			Properties prop = cmo.buildProperties(fileName);
			sendOutput(prop);
			System.out.println((String) reader.readObject());
		} else if(userInput.equals("2")) {
			sendOutput("configuration");
			@SuppressWarnings("unchecked")
			ArrayList<String> modelList = (ArrayList<String>) reader.readObject();
			System.out.println("The following is the model list that you can configure:");
			for(String name : modelList) {
				System.out.println(name);
			}
			System.out.println("Please select one:");
			String modelName = scanner.nextLine();
			sendOutput(modelName);
			
			Automobile auto = (Automobile) reader.readObject();
			sco.selectCarOption(auto);
		} else {
			System.out.println("Please input 1 for uploading or 2 for configuring...");
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

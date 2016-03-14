/**
 * client main class to launch client.
 */
package client;

public class Client {
    public static void main(String[] args){
    	CarModelOptionsIO socketThread = new CarModelOptionsIO();
		socketThread.setHost("localhost");
		socketThread.setPort(4444);
		socketThread.start();
    }
}

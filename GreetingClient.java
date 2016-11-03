import java.net.*;
import java.io.*;
import java.util.Scanner;

public class GreetingClient {
	public static void main(String [] args) {
		try {
			String serverName = args[0]; //get IP address of server from first param
			int port = Integer.parseInt(args[1]); //get port from second param
			String message;
			// String message = args[2]; //get message from the third param

			/* Open a ClientSocket and connect to ServerSocket */
			System.out.println("Connecting to " + serverName + " on port " + port);
			//insert missing line here for creating a new socket for client and binding it to a port - Socket client = new Socket(serverName, port);
			Socket client = new Socket(serverName, port);


			System.out.println("Just connected to " + client.getRemoteSocketAddress());

			Scanner scanner = new Scanner(System.in);//for getting inputs
			while(true) {
				System.out.print("Message: ");
				message = scanner.nextLine();
				System.out.println("-->"+message);
				if(message == "/exit") {
					client.close();
					break;
				}
				
				/* Send data to the ServerSocket */
				OutputStream outToServer = client.getOutputStream();
				DataOutputStream out = new DataOutputStream(outToServer);
				out.writeUTF("Client " + client.getLocalSocketAddress()+" says: " +message);

				/* Receive data from the ServerSocket */
				InputStream inFromServer = client.getInputStream();
				DataInputStream in = new DataInputStream(inFromServer);
				System.out.println("Server says " + in.readUTF());
			}
			 //insert missing line for closing the socket from the client side - client.close()
			System.out.println("goooo");
			}catch(IOException e) {
				//e.printStackTrace();
				System.out.println("Cannot find Server");
			}catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
			}
	}
}

/**
* a) Socket client = new Socket(serverName, port);
* b) client.close();
**/

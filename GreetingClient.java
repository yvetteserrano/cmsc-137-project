import java.net.*;
import java.io.*;
import java.util.Scanner;

public class GreetingClient {
	public static void main(String [] args) {
		try {
			String serverName = args[0]; //get IP address of server from first param
			int port = Integer.parseInt(args[1]); //get port from second param
			String nickname = args[2];
			String message;

			/* Open a ClientSocket and connect to ServerSocket */
			System.out.println("Connecting to " + serverName + " on port " + port);
			//insert missing line here for creating a new socket for client and binding it to a port - Socket client = new Socket(serverName, port);
			Socket client = new Socket(serverName, port);


			System.out.println("Just connected to " + client.getRemoteSocketAddress());

			Scanner scanner = new Scanner(System.in);//for getting inputs
			
			OutputStream outToServer;
			DataOutputStream out;
			InputStream inFromServer;
			DataInputStream in;
			
			while(true) {
				System.out.print("Message: ");
				message = "";
				while(message.equals("")) {
					message = scanner.nextLine();
				}
				//~ System.out.println(scanner.nextLine());
				if(message == "/exit") {
					System.out.println("exit");
					client.close();
					break;
				}
				/* Send data to the ServerSocket */
				outToServer = client.getOutputStream();
				out = new DataOutputStream(outToServer);
				out.writeUTF(client.getLocalSocketAddress()+"`"+nickname+"`"+message);
				/* Receive data from the ServerSocket */
				inFromServer = client.getInputStream();
				in = new DataInputStream(inFromServer);
				System.out.println(in.readUTF());
			}
			 //insert missing line for closing the socket from the client side - client.close()
			}catch(IOException e) {
				//e.printStackTrace();
				System.out.println("Cannot find Server");
			}catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Usage: java GreetingClient <server ip> <port no.> '<nickname>'");
			}
	}
}

/**
* a) Socket client = new Socket(serverName, port);
* b) client.close();
**/

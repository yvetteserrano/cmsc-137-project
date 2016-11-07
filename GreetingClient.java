import java.net.*;
import java.io.*;
import java.util.Scanner;

public class GreetingClient {
	
	public static class MessageSender extends Thread {
		
		Socket clientSocket;
		String nickname;
		
		public MessageSender(Socket clientSocket, String nickname) {
			this.clientSocket = clientSocket;
			this.nickname = nickname;
		}
		
		public void run() {
			
			System.out.println("Sender started");
			
			DataInputStream in;
			DataOutputStream out;
			String message;
			Scanner scanner = new Scanner(System.in);
			try{
				
				while(true) {
					System.out.print("Message: ");
					message = "";
					while(message.equals("")) {
						message = scanner.nextLine();
					}
					//~ System.out.println(scanner.nextLine());
					if(message == "/exit") {
						System.out.println("exit");
						clientSocket.close();
						this.stop();
						break;
					}
					/* Send data to the ServerSocket */
					out = new DataOutputStream(clientSocket.getOutputStream());
					out.writeUTF(clientSocket.getLocalSocketAddress()+"`"+nickname+"`"+message);
				}
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class MessageFetcher extends Thread {
		
		Socket clientSocket;	//client to fetch messages for
		
		public MessageFetcher(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		public void run() {
			
			System.out.println("Fetcher started");
			
			DataInputStream in;
			DataOutputStream out;
			String message;
			Scanner scanner = new Scanner(System.in);
			try{
				
				while(true) {
					/* Receive data from the ServerSocket */
					in = new DataInputStream(clientSocket.getInputStream());
					if(in.available() > 0) {
						System.out.println(in.readUTF());
					}
				}
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
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
			//~ InputStream inFromServer;
			//~ DataInputStream in;
			
			MessageFetcher fetcher = new MessageFetcher(client);
			fetcher.start();
			
			MessageSender sender = new MessageSender(client, nickname);
			sender.start();
			
			
			
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

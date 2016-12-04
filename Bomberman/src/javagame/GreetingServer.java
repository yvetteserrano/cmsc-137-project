package javagame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class GreetingServer extends Thread {
	private ServerSocket serverSocket;
   
	public GreetingServer(int port) throws IOException {
		//insert missing line here for binding a port to a socket

		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(999999);
	}
	
	public class ClientThread extends Thread {
		
		Socket clientSocket;
		ArrayList<Socket> clientSockets;
		
		public ClientThread(Socket clientSocket, ArrayList<Socket> clientSockets) {
			this.clientSocket = clientSocket;
			//~ this.threads = threads;
			this.clientSockets = clientSockets;
		}
		
		public void run(){
			
			System.out.println(this + " running");
			
			String clientMsg;
			String[] tokens;
			DataInputStream in;
			DataOutputStream out;
			
			try{
				while(true) {
					
					//System.out.println("CLIENT SOCKETS>>"+clientSockets);
					
					//~ for(int i=0; i<clientSockets.size(); i+=1) {
						//~ System.out.println(clientSockets.get(i));
					//~ }
					
					/* Read data from the ClientSocket */
					//in = new DataInputStream(clientSocket.getInputStream());
					//System.out.println(">"+in.readUTF());
					
					ArrayList<String> clientAddresses = new ArrayList<String>();
					ArrayList<String> nicknames = new ArrayList<String>();
					ArrayList<String> messages = new ArrayList<String>();
					
					
					for(int i=0; i<clientSockets.size(); i+=1) {
						in = new DataInputStream(clientSockets.get(i).getInputStream());
						
						if(in.available() > 0) {
							clientMsg = in.readUTF().toString();
							tokens = clientMsg.split("\\`");
							clientAddresses.add(tokens[0]);
							nicknames.add(tokens[1]);
							messages.add(tokens[2]);
							System.out.println(tokens[0] + " (" + tokens[1] + ")" + ": " + tokens[2]);
						} else {
							continue;
						}
						
					}
					
					//~ clientMsg = in.readUTF().toString();
					
					//~ tokens = clientMsg.split("\\`");
					// tokens[0] == client address
					// tokens[1] == nickname
					// tokens[2] == message
					
					//~ System.out.println(tokens[0] + " (" + tokens[1] + ")" + ": " + tokens[2]);
					
					for(int i=0; i<clientSockets.size(); i+=1) {
						if(clientSockets.get(i) == clientSocket) {	//skip if self
							// TODO: FIX SENDING TO SELF
							//continue;
						}
						out = new DataOutputStream(clientSockets.get(i).getOutputStream());
						for(int j=0; j<messages.size(); j+=1) {
							out.writeUTF(clientAddresses.get(j) + " (" + nicknames.get(j) + "): " + messages.get(j));
						}
					}
					

					/* Send data to the ClientSocket */
					//~ out.writeUTF("Thank you for connecting to " + clientSocket.getLocalSocketAddress() + "__" + tokens[2]);
					//~ out.writeUTF(tokens[0] + " " + tokens[1] + ": " + tokens[2]);
					//
					boolean foo = false;
					if(foo) {
					//~ if(tokens[2].compareTo("/exit")==0) {
						clientSocket.close();
						System.out.println("Server ended the connection to "+ clientSocket.getRemoteSocketAddress());
					}
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {
      boolean connected = true;
      
      ArrayList<Socket> clientSockets = new ArrayList<Socket>();
      ArrayList<ClientThread> clientThreads = new ArrayList<ClientThread>();
      
      while(connected) {
		try {
			System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");

			/* Start accepting data from the ServerSocket */
			//insert missing line for accepting connection from client here]
			Socket clientSocket = serverSocket.accept();
			
			clientSockets.add(clientSocket);
			
			ClientThread clientThread = new ClientThread(clientSocket, clientSockets);
			clientThread.start();
			
			System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
			
			
			
			
			
			// connected = false;
			}catch(SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			}catch(IOException e) {
				//e.printStackTrace();
				System.out.println("Usage: java GreetingServer <port no.>");
				break;
			}
		} 
	}
//	public static void main(String [] args) {
//		try {
//			int port = Integer.parseInt(args[0]);
//			Thread t = new GreetingServer(port);
//			t.start();
//		}catch(IOException e) {
//			//e.printStackTrace();
//			System.out.println("Usage: java GreetingServer <port no.>");
//		}catch(ArrayIndexOutOfBoundsException e) {
//			System.out.println("Usage: java GreetingServer <port no.> ");
//		}
//	}
}
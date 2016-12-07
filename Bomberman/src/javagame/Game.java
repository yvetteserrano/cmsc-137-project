package javagame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javagame.GreetingServer.ClientThread;
import javagame.GreetingClient.*;

import java.net.*;
import java.io.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public final class Game extends StateBasedGame {
	
	public static final String gameName = "Bomberman";
	public static final int menu = 0;
	public static final int play = 1;
	public static final int instructions = 2;
	
	public Game(String gameName) {
		super(gameName);
		this.addState(new Menu(menu));
		this.addState(new Play(play));
		this.addState(new Instructions(instructions));
	}
	
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		// GameContainer manages game loop, frame rate, behind the scene stuff
		this.getState(menu).init(gameContainer, this);
		this.getState(play).init(gameContainer, this);
		this.getState(instructions).init(gameContainer, this);
		
		//redirect to menu state
		this.enterState(menu);
		
		
	}
	
	public static void main(String[] args) {
		AppGameContainer appGC;
		
		try {
			// create a window that would hold a game
			appGC = new AppGameContainer(new Game(gameName));

			appGC.setDisplayMode(1000, 700, false);
			
//			try {
//				int port = 9999;
//				Thread t = new GreetingServer(port);
//				t.start();
//			}catch(IOException e) {
//				//e.printStackTrace();
//				System.out.println("Usage: java GreetingServer <port no.>");
//			}catch(ArrayIndexOutOfBoundsException e) {
//				System.out.println("Usage: java GreetingServer <port no.> ");
//			}
//			try {
//				GreetingServer server = new GreetingServer(9999);
//				server.start();
//				System.out.println("Server started.");
//			}catch(IOException e) {
//				
//			}
			
			appGC.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void createClient(String serverName, int port, String nickname) {
		try {
//			String serverName = args[0]; //get IP address of server from first param
//			int port = Integer.parseInt(args[1]); //get port from second param
//			String nickname = args[2];
			String message;

			/* Open a ClientSocket and connect to ServerSocket */
			System.out.println("Connecting to " + serverName + " on port " + port);
			//insert missing line here for creating a new socket for client and binding it to a port - Socket client = new Socket(serverName, port);
			Socket client = new Socket(serverName, port);


			System.out.println("Just connected to " + client.getRemoteSocketAddress());

//			Scanner scanner = new Scanner(System.in);//for getting inputs
			
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


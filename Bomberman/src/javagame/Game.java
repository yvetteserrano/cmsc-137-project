package javagame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javagame.GreetingServer.ClientThread;

import java.net.*;
import java.io.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public final class Game extends StateBasedGame {
	
	public static final String gameName = "Bomberman";
	public static final int menu = 0;
	public static final int play = 1;
	
	public Game(String gameName) {
		super(gameName);
		this.addState(new Menu(menu));
		this.addState(new Play(play));
	}
	
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		// GameContainer manages game loop, frame rate, behind the scene stuff
		this.getState(menu).init(gameContainer, this);
		this.getState(play).init(gameContainer, this);
		
		//redirect to menu state
		this.enterState(menu);
	}
	
	public static void main(String[] args) {
		AppGameContainer appGC;
		
		try {
			// create a window that would hold a game
			appGC = new AppGameContainer(new Game(gameName));

			appGC.setDisplayMode(768, 640, false);
			
			try {
//				int port = Integer.parseInt(args[0]);
				int port = 9999;
				Thread t = new GreetingServer(port);
				t.start();
			}catch(IOException e) {
				//e.printStackTrace();
				System.out.println("Usage: java GreetingServer <port no.>");
			}catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Usage: java GreetingServer <port no.> ");
			}
			
			appGC.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
		
	}

}



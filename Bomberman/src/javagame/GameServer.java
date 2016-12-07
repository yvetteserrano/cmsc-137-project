package javagame;

import java.net.*;

public class GameServer implements Runnable {
	private final int clientPort;
	
	public GameServer(int clientPort) {
		this.clientPort = clientPort;
	}
	
	@Override
	public void run() {
//		try(DatagramPacket serverSocket = new DatagramSocket()) {
//			
//			
//		} catch(SocketException e) {
//			e.printStackTrace();
//		} catch(UnknownHostException e) {
//			e.printStackTrace();
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
	}
	
}

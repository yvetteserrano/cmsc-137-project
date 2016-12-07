import java.io.IOException;
import java.net.*;



public class UDPServer {
	
	public static int NUM_OF_PLAYERS = 2;	// 2 players only
	
	public static void main(String[] args) throws IOException {
		
		
		// will be sent to client while at Main State
		// will be used to set player's initial position 
		String[] bmPos = {
			"120,120,1,1",	//player 1
			"660,480,13,19"	//player 2
		};
		
		int numOfPlayers = 0;
		int port = 5555;
		
		DatagramSocket serverSocket = new DatagramSocket(port);
		System.out.println("Datagram Socket started on port " + port);
		
		byte[] sendData = new byte[1024];
		String initialMsg = "Initial Msg";
		
		sendData = initialMsg.getBytes();
			
		//~ DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
		//~ serverSocket.send(sendPacket);
		
		int dataLength = 100;
		byte[] receiveData = new byte[dataLength];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		
		while(true) {
			System.out.println("Waiting for incoming packets");
			serverSocket.receive(receivePacket);
			
			InetAddress IPAddress = receivePacket.getAddress();
			int clientPort = receivePacket.getPort();
			String message = new String(receivePacket.getData());
			
			message = message.trim();
			System.out.println(message + "received.");
			
			String[] tokens = message.split(",");
			System.out.println(">"+tokens[0]+"<");
			if(tokens[0].equals("X") && numOfPlayers < NUM_OF_PLAYERS) {
				System.out.println("true");
				sendData = new byte[1024];
				String responseMessage = bmPos[numOfPlayers];
				numOfPlayers+=1;
				sendData = responseMessage.getBytes();
				
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
				serverSocket.send(sendPacket);
				System.out.println("sent bmPos");
			} else {
				sendData = new byte[1024];
				String responseMessage = "THANKS";
				sendData = responseMessage.getBytes();
				
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
				serverSocket.send(sendPacket);
			}
			break;		// for testing only
			
		}
	}
}

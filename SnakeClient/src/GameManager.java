import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;

import message.ClientMessage;
import message.ServerMessage;
import utilities.Coordinate;
import utilities.CoordinatePresets;
import utilities.ObjectByteConversion;

import java.io.*;

public class GameManager {
	
	protected static PlayField field;
	
	protected final static int gridWidth = 20;
	
	private static SocketChannel socketChannel;
	private static ByteBuffer buffer;

	private static final int SERVER_PORT = 6533;//allow this to be changed later
	
	//receives from server, initialize message to server notifies server of new player
	private static int playerID;
	
	public static void connectToServer(String hostName, int portNumber) {
		try {
			buffer = ByteBuffer.allocate(256);
			
			//change localhost to actual ip address when not testing client and server on same local network
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			
			socketChannel.connect(new InetSocketAddress("localhost", SERVER_PORT));
			
			//busy loop when connecting
			while(!socketChannel.finishConnect());
			
			notifyServerClient();
			System.out.println("connected");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void closeSocket()
	{
		try {
			System.out.println("close client");
			socketChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void notifyServerClient()
	{
		if ((playerID != 0 && socketChannel.isConnected()) || !socketChannel.isConnected()) {
			return;
		}
		try {
			ClientMessage clientMessage = new ClientMessage(playerID, CoordinatePresets.ZERO, true);
			
			byte[] bytes = ObjectByteConversion.toBytes(clientMessage);
			System.out.println("writing x number of bytes: " + bytes.length);
			//write the object
			buffer = ByteBuffer.wrap(bytes);
			socketChannel.write(buffer);
			buffer.clear();
		} catch (IOException e) {
			//unable to write to channel
			e.printStackTrace();
		}
	}
	
	public static void sendDirection(Coordinate direction)
	{
		try {
			ClientMessage clientMessage = new ClientMessage(playerID, direction, false);
			
			byte[] bytes = ObjectByteConversion.toBytes(clientMessage);
			
			//write the object
			buffer = ByteBuffer.wrap(bytes);
			socketChannel.write(buffer);
			buffer.clear();
		} catch (IOException e) {
			//unable to write to channel
			e.printStackTrace();
		}
	}
	
	public static void startListening()
	{
		ByteBuffer buffer = ByteBuffer.allocate(309);//ServerMessage is 309 bytes
		while(true)
		{
			try {
				socketChannel.read(buffer);
				
				ServerMessage message = ((ServerMessage) ObjectByteConversion.toObject(buffer.array()));
				
				if (buffer.position() != 0)
				{
					if (message.initialize)
					{
						System.out.println("initial, my id is : " + message.playerID);
						playerID = message.playerID;
					}
					System.out.println("change at position : (" + message.coord.x + ", " + message.coord.y + ") to color : " + message.color);
				}
				buffer.clear();
			} catch (IOException e) {
				// when reading from channel is invalid
				e.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				// when conversion of bytes to ServerMessage is incorrect
				e.printStackTrace();
				break;
			}
		}
	}
	
	public static void initializeUI()
	{
		field = new PlayField();
		//create grid
		field.createFrame(gridWidth);
		//add input to ui
		field.addKeyListener(new InputEvents());
	}

}

package backend;

import java.io.*;
import java.nio.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.Timer;

import message.ClientMessage;
import message.ServerMessage;
import ui.InputEvents;
import ui.PlayField;
import utilities.Coordinate;
import utilities.ObjectByteConversion;

public final class GameManager {
	/*
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	*/
	private static ServerSocketChannel serverSocketChannel;
	private static Selector selector;
	
	private static ByteBuffer buffer;
	
	private BufferedReader scanner;
	
	private boolean accepted = false;
	
	protected static PlayField field;
	
	private static ArrayList<Food> foodList = new ArrayList<Food>();
	
	private static ArrayList<Player> playerList = new ArrayList<Player>();
	
	//allow these to be changed later through a .ini thats read when server starts?
	private static final int TICKS_INTERVAL = 1000;//delay in ms
	private static final int MAX_PLAYERS = 4;
	protected final static int gridWidth = 40;
	
	private static List<ServerMessage> updateMessages;
	
	public static List<Player> getPlayerList() {
		return playerList;
	}
	
	public static List<Food> getFoodList(){
		return foodList;
	}

	public void gameTicks() {
		int delay = TICKS_INTERVAL;
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//System.out.println("check");
				handleCommands();
				
				try {
					handleServerInput();
					
					sendServerPlayfieldChanges();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (accepted == true) {
					for (Iterator<Player> iterator = playerList.iterator(); iterator.hasNext();) {
						Player p = iterator.next();
						p.move(iterator);
					}
					createFood();
				}
			}
		};
		new Timer(delay, taskPerformer).start();
	}
	
	
	private void handleCommands()
	{
		try {
			if (scanner.ready())
			{
				if (scanner.readLine().equals("exit"))
				{
					closeServer();
					System.exit(0);
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void registerSocketAccept()
	{
		SocketChannel socketChannel;
		try {
			socketChannel = serverSocketChannel.accept();
			accepted = true;
			socketChannel.configureBlocking(false);
			
			if(socketChannel != null){
				Player player = addPlayer();
				socketChannel.register(selector, SelectionKey.OP_READ, player);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleServerInput() throws IOException {
		if (selector.selectNow() == 0) { 
			System.out.println("input size : 0");
			return;
		}
		
		//get read ready keys
		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		
		//get iterator of keys
		Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
		
		//iterate until no more keys
		System.out.println("size : " + selectedKeys.size());
		while(keyIterator.hasNext()) {
		    
			//process keys
		    SelectionKey key = keyIterator.next();

		    if(key.isAcceptable()) {
		        // a connection was accepted.
		    	registerSocketAccept();
		    	System.out.println("connection accepted");

		    } else if (key.isReadable()) {
		        // a channel is ready for reading
		    	System.out.println("read ready");
		    	SocketChannel channel = ((SocketChannel) key.channel());
		    		
		    	//check if connected
		    	if (!channel.isConnected())
		    	{
				    keyIterator.remove();
				    continue;
		    	}
		    	
    			channel.read(buffer);

		    	System.out.println("at position after read : " + buffer.position());
		    	
		    	if (buffer.position() == buffer.limit() || buffer.position() % 157 != 0) {
				    //shouldn't reach here if buffer allocated size is large, if buffer allocated is large (able to buffer 100 client messages in 1 tick) and reaches here then external program may have been used?
		    		
		    		keyIterator.remove();
				    buffer.clear();
		    		continue;
		    	}
		    	
		    	buffer.position(buffer.position() - 157);//157 is size of a client message
		    	try {
			    	ClientMessage message = (ClientMessage) ObjectByteConversion.toObject(buffer.array());//should pass the number of bytes to limit the conversion to, even though it works like this
			    	
			    	if (!message.initialize)
			    	{
			    		System.out.println("result : " + message.coord.x + ", " + message.coord.y);
			    		Player selectedPlayer = ((Player) key.attachment());
			    		
			    		//check playerID in message is same as the one associated to key
			    		if(true)//if (selectedPlayer.getPlayerID() == message.playerID) commented out as client has to receive first update to get it's playerID so it can then put into it's messages for validation
			    		{
			    			selectedPlayer.setDirection(message.coord);
			    		}
			    	}
			    	else
			    	{
			    		System.out.println("player initialize");//such as color the client sent; not yet implemented. 
			    		//player input in next tick. maybe this is where we determine where to spawn player (which is already added) with spawn avoidance
			    	}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("Message from client not as expected");
			    	buffer.clear();
					e.printStackTrace();
				}
		    	buffer.clear();
		    }

		    keyIterator.remove();
		}
	}
	
	private void sendServerPlayfieldChanges() throws IOException
	{
		//flip to write
    	buffer.flip();
		for (int i = 0; i < updateMessages.size(); i++)
		{
			//get all keys
			Set<SelectionKey> selectedKeys = selector.keys();
			//get iterator of keys
			Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

	    	ServerMessage message = updateMessages.get(i);
	    	byte[] bytes = ObjectByteConversion.toBytes(message);
	    	buffer = ByteBuffer.wrap(bytes);
	    	
			while(keyIterator.hasNext()) {
				//process keys
			    SelectionKey key = keyIterator.next();
			    
			    //has attachment relating to which player
			    if (key.attachment() != null)
			    {
			    	Player player = (Player) key.attachment();
			    	
			    	if (!player.clientInitialized) {
			    		//send initial message with playerID, the coordinate is the size of the playfield
			    		sendInitialMessage(player, (SocketChannel) key.channel());
			    	}
			    	
			    	((SocketChannel) key.channel()).write(buffer);
			    	System.out.println("sent size of : " + buffer.position() + " to playerID : " + player.getPlayerID());//in bytes
			    }
			    
			}
		}
	    System.out.println("sent updates");
		//flip back to read
    	buffer.flip();
		updateMessages.clear();
		
		//System.out.println("is dispatch thread : " + java.awt.EventQueue.isDispatchThread());
	}
	
	private static void sendInitialMessage(Player player, SocketChannel channel) throws IOException
	{
    	ServerMessage newMessage = new ServerMessage(player.getPlayerID(), new Coordinate(gridWidth, gridWidth), Color.white, true);
    	byte[] initBytes = ObjectByteConversion.toBytes(newMessage);
    	ByteBuffer initBuffer = ByteBuffer.allocate(309);
    	initBuffer = ByteBuffer.wrap(initBytes);
    	channel.write(initBuffer);
    	
    	player.clientInitialized = true;
    	
    	//if colors are preset as an integer corresponding to a color, i can send whole board in 1 message by x = grid coordinate in wrapped order and y as the preset color.
	}
	
	public static void server(int portNumber) {
		try {
			/*
			serverSocket = new ServerSocket(portNumber);
			clientSocket = serverSocket.accept();
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			*/
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			
			serverSocketChannel.bind(new InetSocketAddress("localhost", portNumber));
			serverSocketChannel.configureBlocking(false);
			
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			updateMessages = new ArrayList<ServerMessage>();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not start server, closing...");
			System.exit(0);
		}
	}
	
	public static void closeServer()
	{
		try {
			System.out.println("server close socket");
			serverSocketChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createFood() {
		Random rand = new Random();
		int x = rand.nextInt(1);
		if (x == 0) {
			Food food = new Food();
			foodList.add(food);
		}
	}
	
	public static void removePlayer(Player player) {
		playerList.remove(player);
	}
	
	private Player addPlayer()
	{
		Random rand = new Random();
		int newID = 0;
		//chance of getting 0 as new id(but very unlikely) - 0 is used as no id set for clients
		while (newID == 0)
		{
			newID = rand.nextInt();
		}
		Player player = new Player(newID);
		
		playerList.add(player);
		
		return player;
	}
	
	public static void notifyUpdate(Coordinate atPoint, Color color)
	{
		ServerMessage message = new ServerMessage(0, atPoint, color, false);
		updateMessages.add(message);
	}
	
	GameManager()
	{
		scanner = new BufferedReader(new InputStreamReader(System.in));
		
		buffer = ByteBuffer.allocate(1570);//size of 10 client messages. Hard coded for now, should determine size of each message later
		
		initializeUI();
		//addPlayer();
		gameTicks();
	}
	
	public static void initializeUI()
	{
		field = new PlayField();
		field.createFrame(gridWidth);
		//field.addKeyListener(new InputEvents());
	}
}

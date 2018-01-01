package backend;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import ui.InputEvents;
import ui.PlayField;
import utilities.Coordinate;

public final class GameManager {
	
	private static ServerSocket serverSocket;
	
	private static Socket clientSocket;
	
	private static ObjectOutputStream out;
	
	private static ObjectInputStream in;
	
	protected static PlayField field;
	
	protected final static int gridWidth = 20;
	
	Player player1;
	Player player2;
	
	private static List<Food> foodList = new ArrayList<Food>();
	
	private static List<Player> playerList = new ArrayList<Player>();
	
	public static List<Player> getPlayerList() {
		return playerList;
	}
	
	public static List<Food> getFoodList(){
		return foodList;
	}

	public void gameTicks() {
		int delay = 500;
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					if (in.read() != 0) {
						Coordinate move = (Coordinate) in.readObject();
						player2.setDirection(move);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				for (Player p : playerList) {
					p.move();
				}
				createFood();
			}
		  };
		  new Timer(delay, taskPerformer).start();
	}
	
	public static void server(int portNumber) {
		try {
			serverSocket = new ServerSocket(portNumber);
			clientSocket = serverSocket.accept();
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
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
	
	GameManager()
	{
		player1 = new Player(this);
		player2 = new Player(this);
		playerList.add(player1);
		playerList.add(player2);
		initializeUI();
		gameTicks();
	}
	
	public static void initializeUI()
	{
		field = new PlayField();
		field.createFrame(gridWidth);
		field.addKeyListener(new InputEvents());
	}
	

}

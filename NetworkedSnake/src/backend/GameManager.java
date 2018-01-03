package backend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import ui.InputEvents;
import ui.PlayField;

public final class GameManager {
	
	protected static PlayField field;
	
	private static GameManager gm;
	
	protected final static int gridWidth = 20;
	
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
		    	  for (Player p : playerList) {
		    		  p.move();
		    	  }
		    	  createFood();
		      }
		  };
		  new Timer(delay, taskPerformer).start();
	}
	
	public void toClient() {
		
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
		Player player1 = new Player(this);
		Player player2 = new Player(this);
		playerList.add(player1);
		playerList.add(player2);
		gameTicks();
	}
	
	public static GameManager getInstance()
	{
		if (gm == null)
		{
			gm = new GameManager();
		}
		return gm;
	}
	
	public void initializeUI()
	{
		field = new PlayField();
		//create grid
		field.createFrame(gridWidth);
		//add input to ui
		field.addKeyListener(new InputEvents());
	}
	

}

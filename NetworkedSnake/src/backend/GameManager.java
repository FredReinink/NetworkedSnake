package backend;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import ui.InputEvents;
import ui.PlayField;

import utilities.Coordinate;

public class GameManager {
	
	protected static PlayField field;
	
	private static GameManager gm;

	//snake coords and length, move to server (non-static) when networked
	//last node of list is head of the snake
	private static List<Coordinate> coordinateList;


	private static Coordinate lastCoordinate;
	//use as a directional vector from last move step
	private static Coordinate lastDirection;
	
	private static String direction;
	
	private static int snakeLength = 10;
	
	protected final static int gridWidth = 20;
	
	private static List<Food> foodList = new ArrayList<Food>();
	
	public static List<Food> getFoodList(){
		return foodList;
	}
	
	public static List<Coordinate> getCoordinateList() {
		return coordinateList;
	}
	
	public static String getDirection() {
		return direction;
	}
	
	public static void setDirection() {
		Random rand = new Random();
		int n = rand.nextInt(4);
		if (n == 0) {
			direction = "UP";
		}
		else if (n == 1) {
			direction = "RIGHT";
		}
		else if (n == 2) {
			direction = "DOWN";
		}
		else if (n == 3) {
			direction = "LEFT";
		}
	}

	public static void setDirection(String newDirection) {
		int d1 = numberDirection(newDirection);
		int d2 = numberDirection(direction);
		if (Math.max(d1,d2)-Math.min(d1,d2) != 2) { //can't reverse directions
			direction = newDirection;
		}else {
			field.showMessage("invalid move");
		}
	}
	
	public void gameTicks() {
		  int delay = 500;
		  ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  move(direction);
		    	  createFood();
		      }
		  };
		  new Timer(delay, taskPerformer).start();
	}
	
	public void createFood() {
		Random rand = new Random();
		int x = rand.nextInt(1);
		if (x == 0) {
			Food food = new Food();
			foodList.add(food);
		}
	}
	
	public void generateSnake() {
		Random rand = new Random();
		int x = rand.nextInt(gridWidth-4) + 2; // 2 square buffer around borders
		int y = rand.nextInt(gridWidth-4) + 2;
		lastCoordinate = new Coordinate(x,y);
	}
	
	GameManager()
	{
		setDirection();
		coordinateList = new LinkedList<Coordinate>();
		generateSnake();
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
	
	public static void move(String direction)
	{
		//y is flipped as top left is coord (0,0) and bottom right is coord (gridWidth,gridWidth)
		
		switch (direction)
		{
			case "UP":
				lastCoordinate.y--;
				field.showMessage("up");
				break;
			case "DOWN":
				lastCoordinate.y++;
				field.showMessage("down");
				break;
			case "RIGHT":
				lastCoordinate.x++;
				field.showMessage("right");
				break;
			case "LEFT":
				lastCoordinate.x--;
				field.showMessage("left");
				break;
		}
		
		if (!gm.validGridIndex())
		{
			field.showMessage("snake is out of bounds");
			lastCoordinate = lastCoordinate.add(lastDirection.negate());
		}
		/**else if (!gm.validMove(lastCoordinate))
		{
			field.showMessage("invalid move");
			lastCoordinate = lastCoordinate.add(lastDirection);
		}*/
		else
		{
			try {
				if (coordinateList.size() > 1)
				{
					lastDirection = lastCoordinate.subtract(coordinateList.get(coordinateList.size() - 1));
					//System.out.println(lastDirection.x + " : " + lastDirection.y);
				}
				coordinateList.add((Coordinate) lastCoordinate.clone());
				field.setColor(lastCoordinate.x, lastCoordinate.y, Color.blue);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (coordinateList.size() > snakeLength)
		{
			Coordinate snakeTailCoord = coordinateList.remove(0);
			field.setColor(snakeTailCoord.x, snakeTailCoord.y, Color.white);
		}
	}
	
	private boolean validGridIndex()
	{
		if (lastCoordinate.y >= gridWidth || lastCoordinate.x >= gridWidth || 
				lastCoordinate.x < 0 || lastCoordinate.y < 0)
		{
			return false;
		}
		return true;
	}
	
	private boolean validMove(Coordinate nextMoveCoord)
	{
		if (coordinateList.size() > 1 && coordinateList.get(coordinateList.size() - 2).equal(nextMoveCoord))
		{
			return false;
		}
		return true;
	}
	
	public static int numberDirection(String aDirection) {
		int n;
		if (aDirection.equals("UP")) {
			n = 0;
		}else if (aDirection.equals("RIGHT")) {
			n = 1;
		}else if (aDirection.equals("DOWN")) {
			n = 2;
		}else if (aDirection.equals("LEFT")) {
			n = 3;
		}else {
			System.out.println("number error");
			field.showMessage("number error");
			n = -1;
		}
		return n;
	}

}

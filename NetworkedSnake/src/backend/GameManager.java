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

public final class GameManager {
	
	protected static PlayField field;
	
	private static GameManager gm;

	//snake coords and length, move to server (non-static) when networked
	//last node of list is head of the snake
	private static List<Coordinate> coordinateList;


	private static Coordinate lastCoordinate;
	//use as a directional vector from last move step
	private static Coordinate lastDirection;
	
	private static Coordinate newDirection;
	
	private static int snakeLength = 10;
	
	protected final static int gridWidth = 20;
	
	private static List<Food> foodList = new ArrayList<Food>();
	
	public static List<Food> getFoodList(){
		return foodList;
	}
	
	public static List<Coordinate> getCoordinateList() {
		return coordinateList;
	}
	
	public static Coordinate getDirection() {
		return newDirection;
	}
	
	public static void setRandomDirection() {
		Random rand = new Random();
		int n = rand.nextInt(4);
		if (n == 0) {
			newDirection = Coordinate.UP;
		}
		else if (n == 1) {
			newDirection = Coordinate.RIGHT;
		}
		else if (n == 2) {
			newDirection = Coordinate.DOWN;
		}
		else if (n == 3) {
			newDirection = Coordinate.LEFT;
		}
	}

	public static void setDirection(Coordinate inputDirection) {
		if (!gm.validMove(inputDirection)) {
			field.showMessage("invalid move");
		}
		else {
			newDirection = inputDirection;
		}
	}
	
	public void gameTicks() {
		  int delay = 500;
		  ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  move(newDirection);
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
		setRandomDirection();
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
	
	public static void move(Coordinate direction)
	{
		//y is flipped as top left is coord (0,0) and bottom right is coord (gridWidth,gridWidth)
		lastCoordinate.add(direction);
		field.showMessage("(" + direction.x + ", " + direction.y + ")");
		
		if (!gm.validGridIndex()) {
			field.showMessage("snake is out of bounds");
			lastCoordinate.add(lastDirection.negate());
			//negate back
			lastDirection.negate();
		}
		else {
			try {
				if (coordinateList.size() > 1)
				{
					lastDirection = direction;
					//System.out.println(lastDirection.x + " : " + lastDirection.y);
				}
				coordinateList.add((Coordinate) lastCoordinate.clone());
				field.setColor(lastCoordinate.x, lastCoordinate.y, Color.blue);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (coordinateList.size() > snakeLength) {
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
		//lastDirection and nextMoveCoord are both directional coordinates (vectors). When the negation of nextMoveCoord is equal to lastDirection that means they're opposite
		if (nextMoveCoord.negate().equal(lastDirection))
		{
			//negate back
			nextMoveCoord.negate();
			return false;
		}
		//negate back
		nextMoveCoord.negate();
		return true;
	}
}

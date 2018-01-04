package backend;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import utilities.Coordinate;
<<<<<<< HEAD
import utilities.CoordinatePresets;

public class Player {

	public boolean clientInitialized = false;
	
	private int playerID;
	
=======

public class Player {

	//snake coords and length
	//last node of list is head of the snake

>>>>>>> master
	private Coordinate lastCoordinate;
	//use as a directional vector from last move step
	private Coordinate lastDirection;
	
	private Coordinate newDirection;
	
	private List<Coordinate> coordinateList;
	
	private int snakeLength = 10;

<<<<<<< HEAD
	public int getPlayerID() {
		return playerID;
	}
	
=======
>>>>>>> master
	public List<Coordinate> getCoordinateList() {
		return coordinateList;
	}
	
	public void addCoordinateList(Coordinate coordinate) {
		coordinateList.add(coordinate);
	}
	
	public Coordinate getDirection() {
		return newDirection;
	}
	
<<<<<<< HEAD
	public Player(int playerID) {
		this.playerID = playerID;
		
=======
	public Player(GameManager gameManager) {
>>>>>>> master
		coordinateList = new LinkedList<Coordinate>();
		generateSnake();
		setRandomDirection();
	}
	
	public void generateSnake() {
		Random rand = new Random();
<<<<<<< HEAD
		int x = rand.nextInt(GameManager.gridWidth-4) + 4; // 2 square buffer around borders
		int y = rand.nextInt(GameManager.gridWidth-4) + 4;
=======
		int x = rand.nextInt(GameManager.gridWidth-4) + 2; // 2 square buffer around borders
		int y = rand.nextInt(GameManager.gridWidth-4) + 2;
>>>>>>> master
		lastCoordinate = new Coordinate(x,y);
	}
	
	public void setRandomDirection() {
		Random rand = new Random();
		int n = rand.nextInt(4);
		if (n == 0) {
<<<<<<< HEAD
			newDirection = CoordinatePresets.UP;
		}
		else if (n == 1) {
			newDirection = CoordinatePresets.RIGHT;
		}
		else if (n == 2) {
			newDirection = CoordinatePresets.DOWN;
		}
		else if (n == 3) {
			newDirection = CoordinatePresets.LEFT;
=======
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
>>>>>>> master
		}
	}

	public void setDirection(Coordinate inputDirection) {
		if (!validMove(inputDirection)) {
			GameManager.field.showMessage("invalid move");
		}
		else {
			newDirection = inputDirection;
		}
	}
	
	public void move()
	{
		//y is flipped as top left is coord (0,0) and bottom right is coord (gridWidth,gridWidth)
		lastCoordinate.add(newDirection);
		GameManager.field.showMessage("(" + newDirection.x + ", " + newDirection.y + ")");
		
		if (!validGridIndex()) {
			GameManager.field.showMessage("snake is out of bounds");
			lastCoordinate.add(lastDirection.negate());
			//negate back
			lastDirection.negate();
		}
		else {
			try {
				if (coordinateList.size() > 1)
				{
					lastDirection = newDirection;
					//System.out.println(lastDirection.x + " : " + lastDirection.y);
				}
				coordinateList.add((Coordinate) lastCoordinate.clone());
<<<<<<< HEAD
				GameManager.field.setColor(lastCoordinate, Color.blue);
=======
				GameManager.field.setColor(lastCoordinate.x, lastCoordinate.y, Color.blue);
>>>>>>> master
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (coordinateList.size() > snakeLength) {
			Coordinate snakeTailCoord = coordinateList.remove(0);
<<<<<<< HEAD
			GameManager.field.setColor(snakeTailCoord, Color.white);
=======
			GameManager.field.setColor(snakeTailCoord.x, snakeTailCoord.y, Color.white);
>>>>>>> master
		}
	}
	
	public boolean validGridIndex()
	{
		if (lastCoordinate.y >= GameManager.gridWidth || lastCoordinate.x >= GameManager.gridWidth || 
				lastCoordinate.x < 0 || lastCoordinate.y < 0)
		{
			return false;
		}
		return true;
	}
	
	public boolean validMove(Coordinate nextMoveCoord)
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

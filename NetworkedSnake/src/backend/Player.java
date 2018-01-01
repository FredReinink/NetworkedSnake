package backend;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import utilities.Coordinate;

public class Player {

	//snake coords and length
	//last node of list is head of the snake

	private Coordinate lastCoordinate;
	//use as a directional vector from last move step
	private Coordinate lastDirection;
	
	private Coordinate newDirection;
	
	private List<Coordinate> coordinateList;
	
	private int snakeLength = 10;

	public List<Coordinate> getCoordinateList() {
		return coordinateList;
	}
	
	public void addCoordinateList(Coordinate coordinate) {
		coordinateList.add(coordinate);
	}
	
	public Coordinate getDirection() {
		return newDirection;
	}
	
	public Player(GameManager gameManager) {
		coordinateList = new LinkedList<Coordinate>();
		generateSnake();
		setRandomDirection();
	}
	
	public void generateSnake() {
		Random rand = new Random();
		int x = rand.nextInt(GameManager.gridWidth-4) + 2; // 2 square buffer around borders
		int y = rand.nextInt(GameManager.gridWidth-4) + 2;
		lastCoordinate = new Coordinate(x,y);
	}
	
	public void setRandomDirection() {
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
				GameManager.field.setColor(lastCoordinate.x, lastCoordinate.y, Color.blue);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (coordinateList.size() > snakeLength) {
			Coordinate snakeTailCoord = coordinateList.remove(0);
			GameManager.field.setColor(snakeTailCoord.x, snakeTailCoord.y, Color.white);
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

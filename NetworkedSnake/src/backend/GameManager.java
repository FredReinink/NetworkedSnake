package backend;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ui.InputEvents;
import ui.PlayField;

import utilities.Coordinate;

public class GameManager {
	
	private static PlayField field;
	
	private static GameManager gm;

	//snake coords and length, move to server (non-static) when networked
	//last node of list is head of the snake
	private static List<Coordinate> coordinateList;
	private static Coordinate lastCoordinate;
	//use as a directional vector from last move step
	private static Coordinate lastDirection;
	
	private static int snakeLength = 10;
	
	private final static int gridWidth = 20;
	
	GameManager()
	{
		coordinateList = new LinkedList<Coordinate>();
		lastCoordinate = new Coordinate(5,5);
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
	
	public static void move(InputEvents.DIRECTION dir)
	{
		//y is flipped as top left is coord (0,0) and bottom right is coord (gridWidth,gridWidth)
		
		switch (dir)
		{
			case UP:
				lastCoordinate.y--;
				field.showMessage("up");
				break;
			case DOWN:
				lastCoordinate.y++;
				field.showMessage("down");
				break;
			case RIGHT:
				lastCoordinate.x++;
				field.showMessage("right");
				break;
			case LEFT:
				lastCoordinate.x--;
				field.showMessage("left");
				break;
		}
		
		if (!gm.validGridIndex())
		{
			field.showMessage("snake is out of bounds");
			lastCoordinate = lastCoordinate.add(lastDirection.negate());
		}
		else if (!gm.validMove(lastCoordinate))
		{
			field.showMessage("invalid move");
			lastCoordinate = lastCoordinate.add(lastDirection);
		}
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
}

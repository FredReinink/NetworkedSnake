package backend;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

import ui.InputEvents;
import ui.PlayField;

public class GameManager {

	private static PlayField field;
	
	private static GameManager gm;
	
	class Coordinate implements Cloneable
	{
		public Coordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		public Coordinate(Coordinate coord)
		{
			this.x = coord.x;
			this.y = coord.y;
		}
		
		public int x;
		public int y;
		
		public Object clone() throws CloneNotSupportedException
		{
			return super.clone();
		}
	}

	//snake coords and length, move to server (non-static) when networked
	private static Queue<Coordinate> coordinates;
	//tail of queue (last coordinate) is head of the snake
	private static Coordinate lastCoordinate;
	private static int snakeLength = 10;
	
	private final static int gridWidth = 20;
	
	GameManager()
	{
		coordinates = new LinkedList<Coordinate>();
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
		field.createGrid(gridWidth);
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
				break;
			case DOWN:
				lastCoordinate.y++;
				break;
			case RIGHT:
				lastCoordinate.x++;
				break;
			case LEFT:
				lastCoordinate.x--;
				break;
		}
		
		if (gm.validGridIndex())
		{
			try {
				coordinates.add((Coordinate) lastCoordinate.clone());
				field.setColor(lastCoordinate.x, lastCoordinate.y, Color.blue);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			//snake hit wall
		}
		
		if (coordinates.size() > snakeLength)
		{
			Coordinate snakeTailCoord = coordinates.remove();
			field.setColor(snakeTailCoord.x, snakeTailCoord.y, Color.white);
		}
	}
	
	private boolean validGridIndex()
	{
		if (lastCoordinate.y > gridWidth || lastCoordinate.x > gridWidth || 
				lastCoordinate.x < 0 || lastCoordinate.y < 0)
		{
			return false;
		}
		return true;
	}
}

package utilities;

import java.io.Serializable;

//2D vector class
public class Coordinate implements Cloneable, Serializable
{
	public int x;
	public int y;
	
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
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean equal(Coordinate coord)
	{
		try {
		if (x == coord.x && y == coord.y)
		{
			return true;
		}
		} catch (NullPointerException e) {
			return true;
		}
		return false;
	}
	
	public Coordinate subtract(Coordinate coord)
	{
		x -= coord.x;
		y -= coord.y;
		return this;
	}
	
	public Coordinate add(Coordinate coord)
	{
		x += coord.x;
		y += coord.y;
		return this;
	}
	
	public Coordinate negate()
	{
		x = -x;
		y = -y;
		return this;
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
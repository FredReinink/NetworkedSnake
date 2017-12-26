package utilities;

//2D vector class
public class Coordinate implements Cloneable
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
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean equal(Coordinate coord)
	{
		if (x == coord.x && y == coord.y)
		{
			return true;
		}
		return false;
	}
	
	public Coordinate subtract(Coordinate coord)
	{
		return new Coordinate(x - coord.x, y - coord.y);
	}
	
	public Coordinate add(Coordinate coord)
	{
		return new Coordinate(x + coord.x, y + coord.y);
	}
	
	public Coordinate negate()
	{
		return new Coordinate(-x, -y);
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
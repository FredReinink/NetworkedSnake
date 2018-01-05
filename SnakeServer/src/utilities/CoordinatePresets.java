package utilities;

public class CoordinatePresets
{
	//on a grid where (0,0) is top left and (max, max) is bottom left. WARNING: variables in Coordinate mutable
	public static final Coordinate UP = new Coordinate(0, -1);
	public static final Coordinate DOWN = new Coordinate(0, 1);
	public static final Coordinate RIGHT = new Coordinate(1, 0);
	public static final Coordinate LEFT = new Coordinate(-1, 0);
	public static final Coordinate ZERO = new Coordinate(0, 0);
}
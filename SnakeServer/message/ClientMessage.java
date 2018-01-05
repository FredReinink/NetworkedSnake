package message;

import java.io.Serializable;
import utilities.Coordinate;

/*
 * This is used for sending the directional input from client to server.
 * Also used for customizing snake (such as color)
 */
public class ClientMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5814286244794088136L;

	public boolean initialize;
	
	public int playerID;
	
	public Coordinate coord;

	public ClientMessage(int playerID, Coordinate coord, boolean initialize)
	{
		this.initialize = initialize;
		this.playerID = playerID;
		this.coord = coord;
	}
}

package message;

import java.awt.Color;
import java.io.Serializable;

import utilities.Coordinate;

/*
 * Used by server to send changes on coordinates in the playfield.
 * Initialize = true when it's the first message to the client. The coordinate is the playfield size, the client is given their playerID and color is nothing
 */
public class ServerMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6798139663250116775L;

	public boolean initialize;
	
	//the playerID this message is to
	public int playerID;
	
	public Coordinate coord;
	
	public Color color;
	
	public ServerMessage(int playerID, Coordinate coord, Color color, boolean initialize)
	{
		this.playerID = playerID;
		this.coord = coord;
		this.color = color;
		this.initialize = initialize;
	}
}

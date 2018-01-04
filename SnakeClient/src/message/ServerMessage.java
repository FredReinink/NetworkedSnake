package message;

import java.awt.Color;
import java.io.Serializable;

import utilities.Coordinate;

/*
 * Used by server to send changes on coordinates in the playfield.
 * NOTE: since servers can vary in grid width, allow server to initialize client to the same parameters as server
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

package backend;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ui.PlayField;
import utilities.Coordinate;

public class Food {
	
	private int x;
	private int y;
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}


	public Food() {
		Random rand = new Random();
		List<Player> playerList = GameManager.getPlayerList();
		List<Integer> coordinateXList = new ArrayList<Integer>();
		List<Integer> coordinateYList= new ArrayList<Integer>();
		for (Player p : playerList) {
			List<Coordinate> coordinateList = p.getCoordinateList();
			for (Coordinate c : coordinateList) {
				coordinateXList.add(c.getX());
				coordinateYList.add(c.getY());
			}
		}
		boolean valid = false;
		while (!valid){
			x = rand.nextInt(GameManager.gridWidth);
			y = rand.nextInt(GameManager.gridWidth);
			if (coordinateXList.contains(x) && coordinateYList.contains(y)) {
			}else {
				valid = true;
			}
		}
		GameManager.field.setColor(x,y,PlayField.FOOD_COLOR);
	}

}

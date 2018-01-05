package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import backend.GameManager;
import backend.Player;
import utilities.CoordinatePresets;

public class InputEvents implements KeyListener{
	
	//Hardcoded player, player 0 in list is the server player. Fix with player IDs ?
	Player player = GameManager.getPlayerList().get(0);

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_W:
			player.setDirection(CoordinatePresets.UP);
			break;
		case KeyEvent.VK_S:
			player.setDirection(CoordinatePresets.DOWN);
			break;
		case KeyEvent.VK_A:
			player.setDirection(CoordinatePresets.LEFT);
			break;
		case KeyEvent.VK_D:
			player.setDirection(CoordinatePresets.RIGHT);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}

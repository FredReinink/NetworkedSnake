package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import backend.GameManager;
import utilities.Coordinate;

public class InputEvents implements KeyListener{

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_W:
			GameManager.setDirection(Coordinate.UP);
			break;
		case KeyEvent.VK_S:
			GameManager.setDirection(Coordinate.DOWN);
			break;
		case KeyEvent.VK_A:
			GameManager.setDirection(Coordinate.LEFT);
			break;
		case KeyEvent.VK_D:
			GameManager.setDirection(Coordinate.RIGHT);
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

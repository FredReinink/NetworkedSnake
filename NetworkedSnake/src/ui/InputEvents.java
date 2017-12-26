package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import backend.GameManager;

public class InputEvents implements KeyListener{

	public enum DIRECTION
	{
		UP,
		DOWN,
		RIGHT,
		LEFT
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_W:
			GameManager.setDirection("UP");
			break;
		case KeyEvent.VK_S:
			GameManager.setDirection("DOWN");
			break;
		case KeyEvent.VK_A:
			GameManager.setDirection("LEFT");
			break;
		case KeyEvent.VK_D:
			GameManager.setDirection("RIGHT");
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


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import utilities.Coordinate;

public class InputEvents implements KeyListener{
	
	ObjectOutputStream out = GameManager.getOut();

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_W:
				out.writeObject(Coordinate.UP);
				break;
			case KeyEvent.VK_S:
				out.writeObject(Coordinate.DOWN);
				break;
			case KeyEvent.VK_A:
				out.writeObject(Coordinate.LEFT);
				break;
			case KeyEvent.VK_D:
				out.writeObject(Coordinate.RIGHT);
				break;
			}
			out.writeInt(0);
		}catch (IOException f){} {
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

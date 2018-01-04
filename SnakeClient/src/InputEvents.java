
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
<<<<<<< HEAD
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.channels.SocketChannel;

import utilities.Coordinate;
import utilities.CoordinatePresets;

public class InputEvents implements KeyListener{
	
	@Override
	public void keyPressed(KeyEvent e) {
		//try {
			System.out.println("client input");
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_W:
				//out.writeObject(Coordinate.UP);
				GameManager.sendDirection(CoordinatePresets.UP);
				break;
			case KeyEvent.VK_S:
				//out.writeObject(Coordinate.DOWN);
				GameManager.sendDirection(CoordinatePresets.DOWN);
				break;
			case KeyEvent.VK_A:
				//out.writeObject(Coordinate.LEFT);
				GameManager.sendDirection(CoordinatePresets.LEFT);
				break;
			case KeyEvent.VK_D:
				//out.writeObject(Coordinate.RIGHT);
				GameManager.sendDirection(CoordinatePresets.RIGHT);
				break;
			}
			//out.writeInt(0);
		//}catch (IOException f){} {
		//}
=======

public class InputEvents implements KeyListener{

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_W:
	//		player.setDirection(Coordinate.UP);
			break;
		case KeyEvent.VK_S:
		//	player.setDirection(Coordinate.DOWN);
			break;
		case KeyEvent.VK_A:
		//	player.setDirection(Coordinate.LEFT);
			break;
		case KeyEvent.VK_D:
	//		player.setDirection(Coordinate.RIGHT);
			break;
		}
>>>>>>> master
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

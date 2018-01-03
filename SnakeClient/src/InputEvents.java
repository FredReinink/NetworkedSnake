
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;

public class GameManager {
	
	protected static PlayField field;
	
	protected final static int gridWidth = 20;

	private static Socket socket;
	
	private static ObjectInputStream in;
	
	private static ObjectOutputStream out;
	
	public static ObjectOutputStream getOut() {
		return out;
	}
	
	public static Socket getSocket() {
		return socket;
	}
	
	public GameManager() {
		initializeUI();
	}
	
	public static void fromServer(String hostName, int portNumber) {
		try {
			socket = new Socket(hostName, portNumber);
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(GameManager.getSocket().getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void initializeUI()
	{
		field = new PlayField();
		//create grid
		field.createFrame(gridWidth);
		//add input to ui
		field.addKeyListener(new InputEvents());
	}

}

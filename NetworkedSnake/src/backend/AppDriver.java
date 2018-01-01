package backend;

public class AppDriver {

	public static void main (String args[])
	{
		int portNumber = 6533;
		GameManager.server(portNumber);
		GameManager gm = new GameManager();
	}
}

package backend;

public class AppDriver {

	public static void main (String args[])
	{
		System.out.println("server");
		int portNumber = 6533;
		GameManager.server(portNumber);
		GameManager gm = new GameManager();
	}
}

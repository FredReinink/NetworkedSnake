package backend;

public class AppDriver {

	private static final int SERVER_PORT = 6533;//allow this to be changed later
	public static void main (String args[])
	{
		System.out.println("server");
		
		GameManager.server(SERVER_PORT);
		GameManager gm = new GameManager();
	}
}


public class ClientAppDriver {

	public static void main (String args[])
	{
		int portNumber = 6533;
		String hostName = "localhost";
		GameManager.fromServer(hostName, portNumber);
		GameManager gm = new GameManager();
	}

}


public class GameManager {
	
	protected static PlayField field;
	
	protected final static int gridWidth = 20;

	public GameManager() {
		initializeUI();
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

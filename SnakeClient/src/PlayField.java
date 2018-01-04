import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
<<<<<<< HEAD
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
=======
>>>>>>> master
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

<<<<<<< HEAD
import utilities.Coordinate;

public class PlayField extends JFrame{

	//calls when window changes
	private WindowListener winListener = new WindowListener(){

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			GameManager.closeSocket();
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
=======
public class PlayField extends JFrame{

>>>>>>> master
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel gridComponents[][];
	private JPanel messagePanel;
	
	private Dimension scrnSize;
	
	private final float WINDOW_BOUND_PERCENTAGE = 0.7f;
	
	/**
	 * Default constructor
	 */
	public PlayField()
	{
		setTitle("Very snek");
		
		scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setBounds((int)(scrnSize.width * (1-WINDOW_BOUND_PERCENTAGE) * 0.5f), (int)(scrnSize.height * (1-WINDOW_BOUND_PERCENTAGE) * 0.5f), 
					(int)(scrnSize.width * WINDOW_BOUND_PERCENTAGE), (int)(scrnSize.height * WINDOW_BOUND_PERCENTAGE));		
		
		setLayout(new FlowLayout());
		
<<<<<<< HEAD
		//only 1 jframe will be used
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
=======
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
>>>>>>> master
	}
	
	public void showMessage(String message)
	{
		messagePanel.removeAll();

		JTextArea messageLabel = new JTextArea(message);
		messageLabel.setEditable(false);
		messageLabel.setLineWrap(true);
		messageLabel.setOpaque(false);
		messagePanel.add(messageLabel);
		
		display();
	}
	
	public void createFrame(int gridWidth)
	{
		createGrid(gridWidth);
		createMessagePanel();
		
<<<<<<< HEAD
		addWindowListener(winListener);
		
=======
>>>>>>> master
		display();
	}
	
	private void createGrid(int gridWidth)
	{
		gridComponents = new JPanel[gridWidth][gridWidth];
		
		JPanel gridPanel = new JPanel();
		
		gridPanel.setLayout(new GridBagLayout());
		//constraints for grid
		GridBagConstraints constraints = new GridBagConstraints();
		//grid padding
		constraints.insets = new Insets(1,1,1,1);
		
		gridPanel.setBackground(Color.white);
		gridPanel.setBorder(BorderFactory.createLineBorder(Color.white));
		
		Dimension componentDimension = new Dimension(getHeight()/gridWidth, getHeight()/gridWidth);
		
		//add and set jpanels of the grid
		for (int x = 0; x < gridWidth; x++)
		{
			for (int y = 0; y < gridWidth; y++)
			{
				constraints.gridx = x;
				constraints.gridy = y;
				
				JPanel componentPanel = new JPanel();
				componentPanel.setPreferredSize(componentDimension);
				componentPanel.setBackground(Color.white);
				gridPanel.add(componentPanel, constraints);
				
				gridComponents[x][y] = componentPanel;
			}
		}
		
		add(gridPanel);
	}
	
	private void createMessagePanel()
	{
		messagePanel = new JPanel();
		messagePanel.setLayout(new BorderLayout());
		messagePanel.setPreferredSize(new Dimension(100, 100));
		
		add(messagePanel);
	}

	
	public void setColor(int x, int y, Color color)
	{
		gridComponents[x][y].setBackground(color);
		display();
	}
	
<<<<<<< HEAD
	public void setColor(Coordinate coord, Color color)
	{
		gridComponents[coord.x][coord.y].setBackground(color);
		display();
	}
	
=======
>>>>>>> master
	public Color getColor(int x, int y)
	{
		return gridComponents[x][y].getBackground();
	}
	
	public void clear()
	{
		getContentPane().removeAll();
		
		display();
	}
	
	private void display()
	{
		pack();
		setVisible(true);
	}
	
	//add component listener to recreate jpanels upon resize
}

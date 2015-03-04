package layout;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;


public class SmileyPlacing extends JFrame
{
	private static final String TITLE="Pick Starting Positions";
	private static final int WIDTH=500;
	private static final int HEIGHT=500;
	
	private static Container content;
	private static JButton[] cells;
	private CellButtonHandler[] cellHandlers;
	
	private static int[][] grid;
	
	private static int goalPos;
	
	private static List<Integer> smiles = new ArrayList<Integer>();
	
	public SmileyPlacing()
	{
		// initialize the box
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// get the content pane
		content=getContentPane();
		// set background colour
		content.setBackground(Color.GRAY.darker());
		// set layout
		content.setLayout(new GridLayout(8,7));

		// create cells
		cells=new JButton[49];
		for(int i=0; i<49; i++)
		{
			cells[i]=new JButton("b"+i);
			cells[i].setName(""+i);
			//cellHandlers[i]=new CellButtonHandler();
			//cells[i].addActionListener(cellHandlers[i]);
		}
								
		//Add elements to the grid content pane
		for(int i=0; i<49; i++)
		{
			content.add(cells[i]);
		}
		
		
		// sets handlers for where each of the smileys can be, array has their positions
		Integer[] smileyHandlers = {0,1,2,4,5,6,7,9,10,12,13,14,16,17,19,20,21,24,26,27,28,29,31,32,33,34,35,36,40,41,42,43,44,45,46,47,48};
		int size = smileyHandlers.length;
		cellHandlers=new CellButtonHandler[size];
		for(int i=0; i<smileyHandlers.length; i++)
		{
			int curr = smileyHandlers[i];
			cellHandlers[i]=new CellButtonHandler();
			cells[curr].addActionListener(cellHandlers[i]);
		}
		
		JButton done = new JButton();
		done.addActionListener(new CellButtonHandler());
		done.setName("done");
		done.setText("Done");
		content.add(done);
		
		init();
	}
	
	public List<Integer> getSmiles(){
		return smiles;
	}
	
	/*public void setSmiles(ArrayList<Integer> smiles){
		smiles.add(4);
	}*/
	
	public void init()
	{
		// Initialize text in buttons
		for(int i=0; i<49; i++)
		{
			cells[i].setText(""+i);
		}	
		setVisible(true);
		
		// initialize all cells to clear (clears cells after button is pressed)
		for(int v=0; v<49; v++){
			JButton b = (JButton) cells[v];
			b.setBackground(Color.white);
			b.setBorderPainted(true);
			b.setOpaque(false);
		}
		
		// initialize the colour of the cells that are obstacles
				// array of the list of obstacles
				Integer[] obstacles = {8,15,22,23,30,37,38,39,3,11,18,25};
				for(int j=0; j<obstacles.length; j++){
					int cell = obstacles[j];
					// reference the button
					JButton btn = (JButton) cells[cell];
					// change the button style
					btn.setBorderPainted(false);
					btn.setOpaque(true);
					// using black for obstacles
				    btn.setBackground(Color.black);
				    // change text
				    btn.setForeground(Color.white);
				}
				
				// initialize the colour of the goal position, using green to indicate
				JButton goal = (JButton) cells[24];
				goal.setBorderPainted(false);
				goal.setOpaque(true);
				goal.setBackground(Color.green.darker());
	}
	
	public static void start()
	{
		//Create grid
		SmileyPlacing gui=new SmileyPlacing();	
	}
	
	private class CellButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// get what button was pressed
			JButton pressed=(JButton)(e.getSource());
			String button = pressed.getName();
			
			if(button.equals("done")){
				System.out.println("Finished clicking");
				//System.exit(0);
				GridSearch obj = new GridSearch();
				obj.start(smiles);
			}
			else{
				Integer smilePosition = Integer.parseInt(button);
				int mrsmile = Integer.parseInt(button);
				
				//smiles.add(smilePosition);
				//smiles.add(3);
				System.out.println("" + smilePosition);
				/*JButton btn = (JButton) cells[mrsmile];
				// change the button style
				btn.setBorderPainted(false);
				btn.setOpaque(true);
				// using black for obstacles
			    btn.setBackground(Color.yellow);*/
				
				if(!smiles.contains(smilePosition)){
					smiles.add(smilePosition);
					JButton btn = (JButton) cells[mrsmile];
					// change the button style
					btn.setBorderPainted(false);
					btn.setOpaque(true);
					// using black for obstacles
				    btn.setBackground(Color.yellow);
				}
				else{
					smiles.remove(smilePosition);
					JButton b = (JButton) cells[smilePosition];
					b.setBackground(Color.white);
					b.setBorderPainted(true);
					b.setOpaque(false);
				}
			}
			
		}
		
	}
}

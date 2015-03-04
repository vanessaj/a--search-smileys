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


public class SmileyPlacingUneven extends JFrame
{
	private static final String TITLE="Pick Starting Positions";
	private static final int WIDTH=600;
	private static final int HEIGHT=600;
	
	private static Container content;
	private static JButton[] cells;
	private CellButtonHandler[] cellHandlers;
	
	private static int[][] grid;
	
	private static int goalPos;
	
	private static List<Integer> smiles = new ArrayList<Integer>();
	
	public SmileyPlacingUneven()
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
		content.setLayout(new GridLayout(10,9));

		// create cells
		cells=new JButton[81];
		for(int i=0; i<81; i++)
		{
			cells[i]=new JButton("b"+i);
			cells[i].setName(""+i);
		}
								
		//Add elements to the grid content pane
		for(int i=0; i<81; i++)
		{
			content.add(cells[i]);
		}
		
		
		// sets handlers for where each of the smileys can be, array has their positions
		Integer[] smileyHandlers = {0,1,2,4,5,6,7,8,9,11,12,14,16,17,18,20,21,23,24,27,30,32,33,36,37,39,40,41,42,45,46,50,51,54,55,56,57,58,60,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80};
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
		
	public void init()
	{
		// Initialize text in buttons
		for(int i=0; i<81; i++)
		{
			cells[i].setText(""+i);
		}	
		setVisible(true);
		
		// initialize all cells to clear (clears cells after button is pressed)
		for(int v=0; v<81; v++){
			JButton b = (JButton) cells[v];
			b.setBackground(Color.white);
			b.setBorderPainted(true);
			b.setOpaque(false);
		}
		
		// initialize the colour of the cells that are obstacles
				// array of the list of obstacles
				Integer[] obstacles = {3,10,13,19,22,28,29,31,38,47,48,49,59};
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
				JButton goal = (JButton) cells[30];
				goal.setBorderPainted(false);
				goal.setOpaque(true);
				goal.setBackground(Color.green.darker());
				
				// gray out cells not part of grid
				Integer[] space = {25,26,34,35,43,44,52,53,61,62};
				for(int m=0; m<space.length; m++){
					JButton b = (JButton) cells[space[m]];
					b.setBackground(Color.gray.darker());
					b.setBorderPainted(false);
					b.setForeground(Color.gray.darker());
					b.setOpaque(true);
				}
	}
	
	public static void start()
	{
		//Create grid
		SmileyPlacingUneven gui=new SmileyPlacingUneven();	
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
				UnevenMaze hello = new UnevenMaze();
				hello.start(smiles);
			}
			else{
				Integer smilePosition = Integer.parseInt(button);
				int mrsmile = Integer.parseInt(button);
				

				System.out.println("" + smilePosition);
				
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

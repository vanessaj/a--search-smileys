package layout;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;

public class UnevenMaze extends JFrame
{
	private static final String TITLE="A* Algorithm - Uneven Maze";
	private static final int WIDTH=600;
	private static final int HEIGHT=600;
	
	private static Container content;
	private static JButton[] cells;
	private CellButtonHandler[] cellHandlers;
	
	private static int[][] grid;
	
	private static int goalPos;
	private static List<Integer> smiles = new ArrayList<Integer>();
	private static List<Integer> smilesChecked = new ArrayList<Integer>();
	
	private static int winner;
	private static Integer winPath;
	
	public UnevenMaze()
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
		content.setLayout(new GridLayout(9,9));

		// create cells
		cells=new JButton[81];
		for(int i=0; i<81; i++)
		{
			cells[i]=new JButton("b"+i);
			cells[i].setName(""+i);
		}
		
		// create handlers
		int numSmiles = smiles.size();
		cellHandlers=new CellButtonHandler[numSmiles];
		// sets handlers for where each of the smileys is, array has their positions
		ArrayList<Integer> smileyHandlers = (ArrayList<Integer>) smiles;
		for(int i=0; i<numSmiles; i++)
		{
			int curr = smileyHandlers.get(i);
			cellHandlers[i]=new CellButtonHandler();
			cells[curr].addActionListener(cellHandlers[i]);
		}
						
		//Add elements to the grid content pane
		for(int i=0; i<81; i++)
		{
			content.add(cells[i]);
		}
		
		init();
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
				
				// initalize the starting position of all the smiley faces, using yellow
				for(int j=0; j<smiles.size(); j++){
					int cell = smiles.get(j);
					// reference the button
					JButton btn = (JButton) cells[cell];
					// change the button style
					btn.setBorderPainted(false);
					btn.setOpaque(true);
					// using black for obstacles
				    btn.setBackground(Color.yellow);
				}
				
				// initialize the colour of the goal position, using green to indicate
				JButton goal = (JButton) cells[30];
				goal.setBorderPainted(false);
				goal.setOpaque(true);
				goal.setBackground(Color.green.darker());
				
				Integer[] space = {25,26,34,35,43,44,52,53,61,62};
				for(int m=0; m<space.length; m++){
					JButton b = (JButton) cells[space[m]];
					b.setBackground(Color.gray.darker());
					b.setBorderPainted(false);
					b.setForeground(Color.gray.darker());
					b.setOpaque(true);
				}
	}
	
	public static void start(List<Integer> smilePlacement)
	{
		//Create grid
		smiles = smilePlacement;
		smilesChecked = smilePlacement;
		
		winner = 0;
		winPath = Integer.MAX_VALUE;
		UnevenMaze gui=new UnevenMaze();	
	}
	
	private class CellButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// get what button was pressed
			JButton pressed=(JButton)(e.getSource());
			init();
			// get text of button
			//String text=pressed.getText();
			
			// get id of button from name
			String button = pressed.getName();
			//System.out.println("Pressed button " + button);
			int start = Integer.parseInt(button);
			Stack<Integer> path = startMoving(start);
			int pathSize = path.size();
			if(smilesChecked.contains(start)){
				Integer s = (Integer) start;
				smilesChecked.remove(s);
			}
			if(pathSize < winPath){
				winPath = pathSize;
				winner = start;
			}
			System.out.println("");
			System.out.println("START = " + start);
			System.out.println("The path size = " + path.size());
			System.out.print("The path = ");
			while(!path.isEmpty()){
				System.out.print(path.peek() + " ");
				int node = path.peek();
				
				JButton goal = (JButton) cells[node];
				goal.setBorderPainted(false);
				goal.setOpaque(true);
				goal.setBackground(Color.blue);
				
				path.pop();
			}
			System.out.println("");
			
			if(smilesChecked.isEmpty()){
				System.out.println("");
				System.out.println("*** WINNER CALCULATED !!!");
				System.out.println("*** Starting at " + winner);
				System.out.println("*** Path size " + winPath);
			}
			else{
				System.out.print("TRY CHECKING : ");
				for(int k=0; k<smilesChecked.size(); k++){
					System.out.print(smilesChecked.get(k) + ", ");
				}
				System.out.println("");
			}
			
		}
		
	}
	
	// IMPLEMENTATION OF A* SEARCH ALGORITHM
	public static Stack<Integer> startMoving(int startPos){
		//System.out.println("Starting at " + startPos);
		
		// INITIALIZE VALUES
		goalPos = 30;
		// initialize the set of nodes already evaluated, so it is empty
		ArrayList<Integer> closedset = new ArrayList<Integer>();
		// init the set of nodes to be evaluated, begin at starting position 
		ArrayList<Integer> openset = new ArrayList<Integer>();
		openset.add(startPos);
		
		// init the map of nodes navigated for the path to the goal, init as empty, says where we came from
		//int[][] nodeMap = new int[7][7];
		ArrayList<Integer> nodeMap = new ArrayList<Integer>();
		for(int cycle=0; cycle<81; cycle++){
			nodeMap.add(cycle, 0);
		}
		
		// initialize matrix for positions
		// 0 for empty positions
		grid = new int[9][9];
		// 1 for obstacles' positions
		// array for obstacles
		int[] obstacles = {3,10,13,19,22,28,29,31,38,47,48,49,59};
		for(int i=0; i<obstacles.length; i++){
			// check their row and column for the grid
			int coors[] = positionLookup(obstacles[i]);
			// add them to the matrix
			grid[coors[0]][coors[1]] = 1;
		}
		// 2 for the goal
		grid[3][3] = 2;
		//System.out.println(Arrays.deepToString(grid));
		
		// SCORES
		// matrix for g score
		int[][] gscore = new int[9][9];
			// set gscore for start, 0 so already initialized
		// call function for heuristic
		int[] point = positionLookup(startPos);
		int score = heuristicCalc(point);
		//System.out.println("Score = " + score);
		int[][] fscore = new int[9][9];
		// set fscore for start
		fscore[point[0]][point[1]] = gscore[point[0]][point[1]] + score;
		//System.out.println("Score = " + fscore[point[0]][point[1]]);
		
		// ALGORITHM
		
		while(!openset.isEmpty()){
			//current is the node in openset with the lowest fscore
			int fscore_val = Integer.MAX_VALUE;
			int current = 0;
			int currentRow = 0;
			int currentCol = 0;
			for(int i=0; i<openset.size(); i++){
				int currNode = openset.get(i);
				// lookup fscore
				int[] pos = positionLookup(currNode);
				int val = fscore[pos[0]][pos[1]];
				// check if min
				if(val < fscore_val){
					fscore_val = val;
					current = currNode;
					currentRow = pos[0];
					currentCol = pos[1];
				}	
			}
			//System.out.println("Current node = " + current);
			
			// if current = goal
            	//return reconstruct_path(came_from, goal)
			if(current == goalPos){
				return reconstructPath(nodeMap, goalPos, startPos);
				//return q;
			}
			
			//remove current from openset
			Integer curr = current;
			openset.remove(curr);
			//add current to closedset
			closedset.add(current);
			
			// get node neighbours
			ArrayList<Integer> neighbs = getNeighbours(current);
			
			//for each neighbor in neighbor_nodes(current)
			for(int k=0; k<neighbs.size(); k++){
				int neighbour = neighbs.get(k);
				// get neighbour row and col
				int neighbCoors[] = positionLookup(neighbour);
				int neighR = neighbCoors[0]; int neighC = neighbCoors[1];
				
				// if neighbour in closed set
				if(closedset.contains(neighbour)){
					continue;
				}
				//tentative_g_score := g_score[current] + dist_between(current,neighbour)
				int tent_gscore = gscore[currentRow][currentCol] + distance(current, neighbour);
				
				//if neighbor not in openset or tentative gscore < gscore[neighbor] 
				if(!openset.contains(neighbour) || (tent_gscore < gscore[neighR][neighC])){
					// update where you came from if not contained, or if score is better
					nodeMap.set(neighbour, current);
					// g_score[neighbor] := tentative_g_score
					gscore[neighR][neighC] = tent_gscore;
					// declare array for neighbour row and column to use with heuristicCalc
					int[] neighbourCoordinates = {neighR, neighC};
					// update fscore[neighbor] = gscore[neighbor] + heuristic_calculation(neighbour)
					fscore[neighR][neighC] = gscore[neighR][neighC] + heuristicCalc(neighbourCoordinates);
					if(!openset.contains(neighbour)){
						openset.add(neighbour);
					}
					
				}
			}
			
		}
		
		return null;
	}
	
	// finding the recorded path
	public static Stack<Integer> reconstructPath(ArrayList<Integer> nodeMap, int curr, int start){
		// trace back through the node map from the goal to the current
		// finds the path and adds to total_path
		//Queue<Integer> total_path = new LinkedList<Integer>();
		Stack<Integer> total_path = new Stack<Integer>();
		total_path.add(goalPos);
		total_path.add(nodeMap.get(curr));
		
		int pointer = nodeMap.get(curr);

		while(pointer != start){
			total_path.add(nodeMap.get(pointer));
			// update pointer
			pointer = nodeMap.get(pointer);
		}
		
		return total_path;
	}
	
	public static ArrayList<Integer> getNeighbours(int cell){
		ArrayList<Integer> neighbs = new ArrayList<Integer>();
		
		int[] cellRC = positionLookup(cell);
		int row = cellRC[0];
		int col = cellRC[1];
		
		// check left
		if(col>0){
			// find neighbour position
			int rowN = row;
			int colN = col-1;
			// find one number reference to cell
			int pos = rowN*9 + colN;
			// add to arraylist of neighbours
			neighbs.add(pos);
		}
		// check above
		if(row>0){
			// find neighbour position
			int rowN = row-1;
			int colN = col;
			// find one number reference to cell
			int pos = rowN*9 + colN;
			// add to arraylist of neighbours
			neighbs.add(pos);	
		}
		// check right
		if(col<8){
			// find neighbour position
			int rowN = row;
			int colN = col+1;
			// find one number reference to cell
			int pos = rowN*9 + colN;
			// add to arraylist of neighbours
			neighbs.add(pos);
		}
		// check below
		if(row<8){
			// find neighbour position
			int rowN = row+1;
			int colN = col;
			// find one number reference to cell
			int pos = rowN*9 + colN;
			// add to arraylist of neighbours
			neighbs.add(pos);
		}
		
		// remove obstacles and inexistant nodes from neighbour list, not possible to travel to them
		int[] obstacles = {25,26,34,35,43,44,52,53,61,62,3,10,13,19,22,28,29,31,38,47,48,49,59};
		for(int ob =0; ob<obstacles.length; ob++){
			Integer neighbour = obstacles[ob];
			neighbs.remove(neighbour);
		}
		
		return neighbs;
	}
	
	public static int distance(int current, int neighbour){
		int currentRC[] = positionLookup(current);
		int neighbRC[] = positionLookup(neighbour);
		// get row and col coordinates for current node and neigbour node
		int currR = currentRC[0]; int currC = currentRC[1];
		int nR = neighbRC[0]; int nC = neighbRC[1];
		// find the difference
		int rowDifference = Math.abs(nR - currR);
		int colDifference = Math.abs(nC - currC);

		int score = rowDifference + colDifference;
		return score;
	}
	
	public static int heuristicCalc(int[] cell){
		// go from current Position to goal
		// we know goal is at 3,3 (row 3, column 3) in the grid
		int rowGoal = 3;
		int colGoal = 3;
		
		// get the row and col from the parameter
		int row = cell[0];
		int col = cell[1];
		
		// testing
		//System.out.println("Row = " + row + " Col = " + col);
		
		// find the difference
		int rowDifference = rowGoal - row;
		int colDifference = colGoal - col;
		
		// var for number of obstacles
		int obstacles = 0;
		
		// FIND ROW, LOOK FOR OBSTACLES
		// if positive, current position is above goal
		// if 0, current position is on same row (skips while loop)
		// if negative, current position is below goal
		while(row != rowGoal){
			if(grid[row][col] == 1){
				obstacles++;
			}
			// move the pointer to the same row as the goal
			// down if above goal
			if(rowDifference>0){
				row++;
			}
			// up if below goal
			else{
				row--;
			}
			//System.out.println("Row = " + row + " Col = " + col + " Obstacles = " + obstacles);
		}
		
		// FIND COLUMN, LOOK FOR OBSTACLES
		// if positive, current position is to the left
		// if 0, col is same as col goal is in (skips while loop)
		// if negative, current position is to the right
		while(col != colGoal){
			if(grid[row][col] == 1){
				obstacles++;
			}
			// move the pointer to the same col as the goal
			// move right if to the left of the goal
			if(colDifference>0){
				col++;
			}
			// move left if to the right of the goal
			else{
				col--;
			}
			//System.out.println("Row = " + row + " Col = " + col + " Obstacles " + obstacles);
		}
		
		// sum up the vertical path, the horizontal path and the number of obstacles in the way
		int score = Math.abs(rowDifference) + Math.abs(colDifference) + obstacles;
		
		return score;
	}
	
	// function that takes the number of the square and returns its row and column
	public static int[] positionLookup(int pos){
		// calculate row; divide by rows after using mod to account for columns
		int row = (pos - (pos%9))/9;
		// calculate column; use mod, 7 columns
		int col = pos % 9;
		int[] matrixPos = {row,col};
		return matrixPos;
	}
	

}
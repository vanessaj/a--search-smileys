package layout;

import java.util.ArrayList;
import java.util.Scanner;

public class Start {
	
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		System.out.println("Hello!");
		System.out.println("Press 1 to chose the normal maze; press 2 for the uneven maze");
		String maze = scan.next();
		int pick = Integer.parseInt(maze);
		if(pick == 1){
			System.out.println("");
			
			SmileyPlacing smile = new SmileyPlacing();
			smile.start();			
		}
		else if(pick == 2){
			// start 2nd maze
			
			//UnevenMaze obj = new UnevenMaze();
			//obj.start();
			
			SmileyPlacingUneven unevenSmile = new SmileyPlacingUneven();
			unevenSmile.start();
		}
		else{
			//System.out.println("Invalid input!");
		}
		
	}
}

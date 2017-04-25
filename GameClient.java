 

import java.util.*;

/**
 * Client class for Minesweeper that a user runs to play the game.
 * Also takes input from the user.
 * 
 * @author Anuja Gawande 
 * @version Feb. 18,2017
 */
public class GameClient
{
    public static void main (String[]args){
        try{
        System.out.println("Text-Based MINESWEEPER! (v1.3)");
        System.out.println("______________________________");
             
        Scanner input = new Scanner(System.in); 
        System.out.println("How many rows(4-10)?");
        int rows = input.nextInt();
        
        System.out.println("How many columns(4-10)?");
        int columns = input.nextInt();
        
        System.out.println("How many mines(1 - 12)?");
        int mines = input.nextInt();
        
        GameBoard g = new GameBoard(rows, columns, mines);
           
        System.out.println("Select the first tile to uncover!"); 
        System.out.println("Enter the tile's row (0-"+g.getRows()+" and column (0-"+g.getColumns()+") :");
        int rowValue = input.nextInt();
        int colValue = input.nextInt();
        
        g.markOrUncoverTile('U', rowValue, colValue);
        
        g.distribute(mines, rowValue, colValue);
        g.markOrUncoverTile('U', rowValue, colValue);// Calling again since the mines were added after uncovering the first tile. And also the adjacent value needs to be updated
        g.toString();
        
        while(!g.checkIfGameIsOver()){
        	System.out.println("(M)ark or (U)ncover a title?");
        	char c =input.next().charAt(0);
        	
        	System.out.println("Enter the tile's row (0-"+g.getRows()+" and column (0-"+g.getColumns()+") :");
        	rowValue = input.nextInt();
        	colValue = input.nextInt();
        	
        	g.markOrUncoverTile(c, rowValue, colValue);
        	g.toString();
        }
     }catch(Exception e){
        	
        }
    }
}

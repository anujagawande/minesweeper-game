
/**
 * Maintains the internal state of the game
 * 
 * @author Anuja Gawande 
 * @version Feb. 22, 2017
 */
public class GameBoard
{
    protected int rows;
	protected int columns;
    protected int totalNumberOfTiles = 0;
    protected int maxMines = 0;
    protected int totalMinesMarked = 0;
    protected boolean minesCreated = false;
    
	protected Tile[][] objects;//2D array of tile objects

    public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		if(rows>10 || rows<4){
			System.out.println("The maximum grid size should be 10 by 10 tiles. The minimum grid size should be 4 by 4 tiles.");
			System.exit(0);
		}else{
			this.rows = rows;
		}
	}
	public boolean isMinesCreated() {
		return minesCreated;
	}

	public void setMinesCreated(boolean minesCreated) {
		this.minesCreated = minesCreated;
	}
	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		if(columns>10 || columns<4){
			System.out.println("The maximum grid size should be 10 by 10 tiles. The minimum grid size should be 4 by 4 tiles.");
			System.exit(0);
		}else{
			this.columns = columns;
		}
	}

	public int getTotalNumberOfTiles() {
		return totalNumberOfTiles;
	}

	public void setTotalNumberOfTiles(int totalNumberOfTiles) {
		this.totalNumberOfTiles = totalNumberOfTiles;
	}
	
	public int getTotalMinesMarked() {
		return totalMinesMarked;
	}

	public void setTotalMinesMarked(int totalMinesMarked) {
		this.totalMinesMarked = totalMinesMarked;
	}
	
    public int getmaxMines(){

        return maxMines;
    }

    public void setmaxMines(int maxMines){
        int maxMinesAllowed = (int) ((50.0/100.0) * totalNumberOfTiles);//total # of mines te be distributed, 50% of total number of tiles
        //The maximum number of mines should be 50% of the total number of tiles.
        if(maxMines > maxMinesAllowed){
        	System.out.println("The maximum number of mines should be 50% of the total number of tiles! so, Please try again!");
        	System.exit(0);
        }else{
        	this.maxMines = maxMines;
        }
    }

    public GameBoard(int rows, int columns, int totalMines){//?
    	
    	this.setRows(rows);
    	this.setColumns(columns);
    	this.totalNumberOfTiles = rows*columns;
    	this.setmaxMines(totalMines);
    	objects = new Tile[rows][columns];
    	
        for(int i = 0;i<rows; i++){
            for(int j = 0; j<columns; j++){
                objects[i][j] = new Tile();
            }
        }
    }

    public void distribute(int mines, int firstTileRowValue, int firstTileColValue){//distributes specified number of mines throughout 2D array of tiles:?
    	int x = 0;
    	int y = this.getRows() - 1;
    	
    	int a = 0;
    	int b = this.getColumns() - 1;
        do{
            int randomRowsPosition = (int)Math.floor(Math.random()* ((y-x)+1)+x) ;
            int randomColumnPosition = (int)Math.floor(Math.random()* ((b-a)+1)+a);

            if(!(randomRowsPosition==firstTileRowValue && randomColumnPosition==firstTileColValue)){ // condition to avoid adding mines in the already uncovered first position
            	if(objects[randomRowsPosition][randomColumnPosition].isMineTile()== false){//if the position doesnt contain mine already...
                    objects[randomRowsPosition][randomColumnPosition].setMineTile(true);
                    mines--;
                }
            }
            

        }
        while(mines<= maxMines && mines>0);{
        }
        setMinesCreated(true);
    }
    
    public void printTiles(){
    	
    	for (int i=0; i<=this.getRows()-1;i++){
			for(int j=0;j<=this.getColumns()-1;j++){
				System.out.print(objects[i][j] +"\t"+" |"); //
			}
			System.out.println("");
		}
    	
    }
    
    /*
     * This method is to find the count of adjacent Mines
     */
    public int adjacentMines(int x, int y){

        int adjacentMines = 0;
        for(int i = -1; i <= 1; i++) {
            if((x + i < 0) || (x + i >= rows)) {
                continue;
            }
            for(int j = -1; j <= 1; j++) {
                if((y + j < 0) || (y + j >= columns)) {
                    continue;
                }
                if(objects[x + i][y + j].isMineTile()) {
                    adjacentMines++;
                }
            }
        }
        //System.out.println(" The adjacent Mines are: "+adjacentMines);
        
        return adjacentMines;
    }
    
    
    /*
     * This method is to auto uncover the adjacent Mines
     */
    public void autoUncoveringAdjacentTiles(int x, int y) throws Exception{

        for(int i = -1; i <= 1; i++) {
            if((x + i < 0) || (x + i >= rows)) {
                continue;
            }
            for(int j = -1; j <= 1; j++) {
                if((y + j < 0) || (y + j >= columns)) {
                    continue;
                }
                Tile t = objects[x+i][y+j];
                int adjMinesCount = adjacentMines(x+i, y+j);
        		t.setAdjacentMine(adjMinesCount);
                t.setUncoveredTile(true);
                }
            }
        }
        
    
    
    /*
     * This method is to mark or uncover a tile
     */
    public String markOrUncoverTile(char markOrUncover, int row, int col) throws Exception{//?
        
    	// When you collect input for which tile to mark/uncover, be sure that the input refers to a valid position on the grid.
    	if((row > (this.getRows()-1)) || (col > (this.getColumns()-1))){
    		System.out.println("Invalid position on the grid! Please enter a valid one");
    		return "";
    	}
    	
        Tile tile = objects[row][col];
        if(markOrUncover =='M'){
        	//A tile can be marked only if it’s still covered – trying to mark an already uncovered tile should do nothing.
        	if(!tile.isUncoveredTile()){
        		//A tile can be marked and unmarked as many times as desired, as long as it remains covered. However, once a tile is uncovered it cannot be covered again.
        		if(!tile.isMarkedTile()){
        			setTotalMinesMarked(getTotalMinesMarked() + 1);
        		}else{
        			setTotalMinesMarked(getTotalMinesMarked() - 1);
        		}
        		tile.setMarkedTile(!tile.isMarkedTile());
        	}
        }else if(markOrUncover =='U'){
        	if(tile.isMineTile()){
        		System.out.println("BOOM!! its a MINE!! GAME OVER. BETTER LUCK NEXT TIME!");
        		displayTheResult();
        		throw new Exception("Clicked on Mine!");
        	}else{
        		//A tile can be uncovered only if it’s not marked – trying to uncover a marked tile should do nothing.
        		if(!tile.isMarkedTile()){
        			tile.setUncoveredTile(true);	
            		int adjMinesCount = adjacentMines(row, col);
            		tile.setAdjacentMine(adjMinesCount);
            		if(adjMinesCount==0 && isMinesCreated()){
            			autoUncoveringAdjacentTiles(row, col);
            		}
        		}else{
        			System.out.println("A tile can be uncovered only if it’s not marked");
        		}
        	}
        }else{
        	System.out.println(" Invalid option!");
        }
        return "";
    }
    
    public void displayTheResult(){
    	
    	//this method is to Display the correct mine positions after a game loss.It will also indicate the tiles that the player marked incorrectly (i.e., tiles that are marked but do not actually contain mines).
    	
        String display = "";
        System.out.print("  ");

        for(int k = 0;k<columns; k++){
            System.out.print("  " +  k + " ");
        }
        System.out.println();

        System.out.print("  ");

        for(int k = 0;k<columns; k++){
            System.out.print("----");
        }
        System.out.print("-");
        System.out.println();

        for(int i =0; i<rows; i++){
            System.out.print(i + " ");
            for(int j= 0; j<columns; j++){
            	
            	String symbol = "";
            	if(objects[i][j].isMineTile()){
            		symbol = "*";
            	}else if(objects[i][j].isMarkedTile() && !(objects[i][j].isMineTile())){
            		symbol = "X";
            	}
                System.out.print("|  " + symbol + " ");

            }
            System.out.print("|");
            System.out.println("  ");
            System.out.print("  ");

            for(int k = 0;k<columns; k++){
                System.out.print("+---");
            }

            System.out.print("+");
            System.out.println();
            }
        
    }
    
    
    
    public boolean checkIfGameIsOver(){
    	
    	boolean gameOver = false;
    	
    	// 1. all tiles with mines are marked, and all tiles without mines are uncovered (win)
    	boolean allTilesWithMinesMarked = true;
    	boolean allTilesUncoveredWithoutMines = true;
    	
    	for(int i = 0;i<rows; i++){
            for(int j = 0; j<columns; j++){
               Tile t = objects[i][j];
               if(t.isMineTile()){
            	   if(!t.isMarkedTile()){
            		   allTilesWithMinesMarked = false;
                	   break;   
            	   }
               }
               
               if(!t.isMineTile()){
            	   if(!t.isUncoveredTile()){
            		   allTilesUncoveredWithoutMines = false;
                	   break;
            	   }
               }
            }
        }
    	
    	if(allTilesWithMinesMarked && allTilesUncoveredWithoutMines){
    		System.out.println(" Congratulations! You WON the GAME!!");
    		gameOver = true;
    		return gameOver;
    	}
    	
    	// 2. any tile with a mine is uncovered(loss)
    	for(int i = 0;i<rows; i++){
            for(int j = 0; j<columns; j++){
            	 Tile t = objects[i][j];
            	if(t.isMineTile() && t.isUncoveredTile()){
            		System.out.println(" Bad Luck! BETTER LUCK NEXT TIME!!");
            		gameOver = true;
            		return gameOver;
            	}
            }
    	}
    	
    	return gameOver;
    }
    

        public String toString(){//toString method to display the board: works!
        String display = "";
        System.out.print("  ");

        for(int k = 0;k<columns; k++){
            System.out.print("  " +  k + " ");
        }
        System.out.println();

        System.out.print("  ");

        for(int k = 0;k<columns; k++){
            System.out.print("----");
        }
        System.out.print("-");
        System.out.println();

        for(int i =0; i<rows; i++){
            System.out.print(i + " ");
            for(int j= 0; j<columns; j++){
                System.out.print("|  " + objects[i][j].toString() + " ");

            }
            System.out.print("|");
            System.out.println("  ");
            System.out.print("  ");

            for(int k = 0;k<columns; k++){
                System.out.print("+---");
            }

            System.out.print("+");
            System.out.println();
            

        }
        System.out.println("Mines : " +getmaxMines() +" Marked : "+getTotalMinesMarked());
        return display;
    }

}

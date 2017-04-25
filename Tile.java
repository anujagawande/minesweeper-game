 

/**
 * Represents and controls info about single tile on the Minesweeper game board
 * 
 * @author Anuja Gawande 
 * @version Feb. 18, 2017
 */
public class Tile
{
    protected boolean uncoveredTile;
    protected boolean markedTile;
    protected boolean mineTile;
    protected int adjacentMine;
    
    public boolean isUncoveredTile() {
		return uncoveredTile;
	}
	public void setUncoveredTile(boolean uncoveredTiles) {
		this.uncoveredTile = uncoveredTiles;
	}
	public boolean isMarkedTile() {
		return markedTile;
	}
	public void setMarkedTile(boolean markedTiles) {
		this.markedTile = markedTiles;
	}
	public boolean isMineTile() {
		return mineTile;
	}
	public void setMineTile(boolean mineTiles) {
		this.mineTile = mineTiles;
	}
	public int getAdjacentMine() {
		return adjacentMine;
	}
	public void setAdjacentMine(int adjacentMines) {
		this.adjacentMine = adjacentMines;
	}
	public Tile(){//constructor

		setUncoveredTile(false);
		setMarkedTile(false);
		setMineTile(false);
		setAdjacentMine(0);

    }
    public String toString(){//toString method to display the tile, dependng on its state:works!
        String r = "";

        if(uncoveredTile ==false){
            r += "";
        }
        else{
         if(mineTile == true)//MINES are represented by +
         r += "+";
         else if(!markedTile)
         r += adjacentMine;
        }

        if(markedTile == true){//MARKED TILES are represented by *
            r += "*";
        }

        return r;
    }
}

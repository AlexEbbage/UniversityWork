package assignment2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

//Written by Alex Ebbage (1504283) on 12/12/16.
public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1177541238261104002L;
	
	//Reference to the application instance.
	private Main game;
	
	//Tetrimo array and the game grid.
	private Tetrimo[][] tiles;
	
	//Border size around grid.
	private static final int BORDER = 5;
	
	//Width and height of the grid; including hidden rows.
	private static final int BOARD_WIDTH = 10;
	private static final int BOARD_HEIGHT = 20;
	private static final int HIDDEN_ROWS = 2;
	
	//Size of the grid tiles in pixels.
	private static final int TILE_SIZE = 32;
	
	//Bevel size of Tetrimo tiles.
	private static final int BEVEL = 8;
	
	//Width and height of the whole panel.
	private static final int PANEL_WIDTH = HIDDEN_ROWS + BOARD_WIDTH * TILE_SIZE + 2 * BORDER;
	private static final int PANEL_HEIGHT = BOARD_HEIGHT * TILE_SIZE + 2 * BORDER;
	
	//Center of the panel to keep text center aligned.
	private static final int CENTER = (BOARD_WIDTH * TILE_SIZE) / 2;
	
	//Colours for different parts.
	private static final Color LINE_COLOR = new Color(40, 40, 40);
	private static final Color FONT_COLOR = new Color(40, 40, 40);
	private static final Color NAME_COLOR = new Color(230, 0, 0);
	private static final Color BACKGROUND_COLOR =  new Color(255, 255, 255);
	
	//Fonts for different parts.
	private static final Font FONT_LARGE = new Font("Eras Demi ITC", Font.PLAIN, 36);
	private static final Font FONT_MEDIUM = new Font("Eras Demi ITC", Font.PLAIN, 28);
	private static final Font FONT_SMALL = new Font("Eras Demi ITC", Font.PLAIN, 22);
	
	
	//Constructor for the game panel.
	public GamePanel(Main game){
		this.game = game;
		this.tiles = new Tetrimo[BOARD_WIDTH][BOARD_HEIGHT + HIDDEN_ROWS];
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBackground(BACKGROUND_COLOR);
	}
	
	
	//Adds the Tetrimo to the grid when it collides.
	public void addTetrimoToGrid(Tetrimo tetrimo, int x, int y){
		for(int i = 0; i < tetrimo.getSize(); i++){
			for(int j = 0; j < tetrimo.getSize(); j++){
				if(Tetrimo.isTetrimoTile(tetrimo.getCurrentShape(), i, j)){
					setTileValue(tetrimo, i+x, j+y);
				}
			}
		}
		tetrimo.setCurrentShape(tetrimo.getSpawnShape());
	}
	
	
	//Checks to see if any lines need clearing and changes score based on linesCleared.
	public void checkLines(){
		int completedLines = 0;
		for(int y = 0; y < BOARD_HEIGHT+HIDDEN_ROWS; y++){
			if(checkLine(y)){
				completedLines++;
			}
		}
		
		game.setScore(game.getScore() + completedLines * 10);
		game.setLines(game.getLines() + completedLines);
		game.setLevel(1 + (game.getLines()/3));
		
		if(game.getSpeedTemp() < 3){
			game.setSpeedTemp(game.getSpeedTemp() + completedLines*0.25);
			game.setSpeed(game.getSpeedTemp());
		}
	}
	
	
	//Checks if a line needs to be cleard.
	private boolean checkLine(int y){
		for(int x = 0; x < BOARD_WIDTH; x++){
			if(!isTileOccupied(x,y)){
				return false;
			}
		}
		clearLine(y);
		return true;
	}
	
	
	//Clears the given line by shifting all rows above it down 1.
	private void clearLine(int y){
		for(int row = y-1; row >= 0; row--){
			for(int col = 0; col < BOARD_WIDTH; col++){
				setTileValue(getTileValue(col, row), col, row+1);
			}
		}
	}
	
	
	//Checks if the given position of the Tetrimo is valid.
	public boolean isValidPosition(Tetrimo tetrimo, int x, int y){
		//Checks that the Tetrimo is within the grid bounds.
		for(int i = 0; i < tetrimo.getSize(); i++){
			for(int j = 0; j < tetrimo.getSize(); j++){
				if(tetrimo.getCurrentShape()[i][j]){
					if((x+i < 0) || (x+i >= BOARD_WIDTH) || (y+j < 0) || (y+j >= BOARD_HEIGHT+HIDDEN_ROWS)){
						return false;
					}
					else{
						if(isTileOccupied(x+i, y+j)){
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	
	//Rotates a Tetrimo in the chosen direction, if valid sets new position, else reverts to previous position.
	public void rotate(Tetrimo tetrimo, int rotate){
		int xChanged = game.getCurrentX();
		int yChanged = game.getCurrentY();

		//Copies shape to a temporary array.
		boolean[][] tempShape = new boolean[tetrimo.getSize()][tetrimo.getSize()];
		for(int i = 0; i < tetrimo.getSize(); i++){
			for(int j = 0; j < tetrimo.getSize(); j++){
				tempShape[i][j] = tetrimo.getCurrentShape()[i][j];
			}
		}
		
		int xTemp = game.getCurrentX();
		int yTemp = game.getCurrentY();

		//Rotates the matrix clockwise or anti-clockwise.
		if(rotate == 0){
			rotateLeft(tetrimo);
		}
		else{
			rotateRight(tetrimo);
		}
		
		//Checks rotated Tetrimo is within the grid bounds, else moves it inside.
		int leftSpace = leftSpace(tetrimo.getCurrentShape());
		int rightSpace = rightSpace(tetrimo.getCurrentShape());
		if( game.getCurrentX() < -leftSpace){
			xChanged -=  game.getCurrentX() - leftSpace;
		}
		
		else if( game.getCurrentX() + tetrimo.getSize() - rightSpace >= BOARD_WIDTH){
			xChanged -= ( game.getCurrentX() + tetrimo.getSize() - rightSpace) - BOARD_WIDTH + 1;
		}
		
		int topSpace = topSpace(tetrimo.getCurrentShape());
		int bottomSpace = bottomSpace(tetrimo.getCurrentShape());
		if(game.getCurrentY() < -topSpace){
			yChanged -= game.getCurrentY() - topSpace;
		}
		
		else if(game.getCurrentY() + tetrimo.getSize() - bottomSpace >= BOARD_HEIGHT+HIDDEN_ROWS){
			yChanged -= (game.getCurrentY() + tetrimo.getSize() - bottomSpace) - (BOARD_HEIGHT+HIDDEN_ROWS) + 1;
		}
		
		//Check if the moved Tetrimo is overlapping any pieces, if it is, it reverts to start position.
		if(isValidPosition(tetrimo, xChanged, yChanged)){
			game.setCurrentX(xChanged);
			game.setCurrentY(yChanged);
		}
		else{
			game.getCurrentTetrimo().setCurrentShape(tempShape);
			game.setCurrentX(xTemp);
			game.setCurrentY(yTemp);
		}
	}
	
	
	//Finds the space between the left side of the array and the shape.
	public static int leftSpace(boolean[][] shape) {
		for(int x = 0; x < shape[0].length; x++) {
			for(int y = 0; y < shape.length; y++) {
				if(Tetrimo.isTetrimoTile(shape, x, y)) {
					return x;
				}
			}
		}
		return 0;
	}

	
	//Finds the space between the right side of the array and the shape.
	public int rightSpace(boolean[][] shape) {
		for(int x = shape[0].length - 1; x >= 0; x--) {
			for(int y = 0; y < shape.length; y++) {
				if(Tetrimo.isTetrimoTile(shape, x, y)) {
					return shape.length - x;
				}
			}
		}
		return 0;
	}

	
	//Finds the space between the top side of the array and the shape.
	public static int topSpace(boolean[][] shape) {
		for(int y = 0; y < shape[0].length; y++) {
			for(int x = 0; x < shape.length; x++) {
				if(Tetrimo.isTetrimoTile(shape, x, y)) {
					return y;
				}
			}
		}
		return 0;
	}

	
	//Finds the space between the bottom side of the array and the shape.
	public int bottomSpace(boolean[][] shape) {
		for(int y = shape[0].length - 1; y >= 0; y--) {
			for(int x = 0; x < shape.length; x++) {
				if(Tetrimo.isTetrimoTile(shape, x, y)) {
					return shape.length - y;
				}
			}
		}
		return 0;
	}
	
	
	//Rotates the Tetrimo clockwise.
	private void rotateRight(Tetrimo tetrimo){
		boolean[][] shape = tetrimo.getCurrentShape();
		shape = transpose(shape);
		shape = swapRows(shape);
		tetrimo.setCurrentShape(shape);
	}
	
	
	//Rotates the Tetrimo anti-clockwise.
	private void rotateLeft(Tetrimo tetrimo){
		boolean[][] shape = tetrimo.getCurrentShape();
		shape = swapRows(shape);
		shape = transpose(shape);
		tetrimo.setCurrentShape(shape);
	}
	
	
	//Flips the rows of the given Matrix.
	private boolean[][] swapRows(boolean[][] shape){
		boolean[][] temp = new boolean[shape[0].length][shape.length];
	    for (int  i = 0; i < shape.length; i++) {
	        temp[i] = shape[shape.length - i - 1];
	    }
		return temp;
	}
	
	
	//Transposes the given matrix.
	private boolean[][] transpose(boolean[][] shape){
		boolean[][] temp = new boolean[shape[0].length][shape.length];
		for(int x = 0; x < shape.length; x++){
			for(int y = 0; y < shape.length; y++){
				temp[y][x] = shape[x][y];
			}
		}
		return temp;
	}
	
	
	//Clears the grid of Tetrimo values.
	public void resetGrid(){
		for(int i = 0; i < BOARD_WIDTH; i++ ){
			for(int j = 0; j < BOARD_HEIGHT + HIDDEN_ROWS; j++){
				tiles[i][j] = null;
			}
		}
	}
	
	
	//Renders the game panel.
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);	
		Graphics2D g2 = (Graphics2D) g;
		
		//Sets anti-aliasing
		g2.setRenderingHints(Main.getRendererantialiasing());
		g2.setRenderingHints(Main.getRenderertextantialiasing());
		
		g.translate(5, 5);
		
		//If is a new game (first run), draw my name, id and how to start.
		if(game.isNewGame() ) {
			g.setColor(NAME_COLOR);
			g.setFont(FONT_LARGE);
			String text = "ALEX EBBAGE";
			g.drawString(text, CENTER - g.getFontMetrics().stringWidth(text) / 2, 150);
			g.setFont(FONT_MEDIUM);
			text = "1504283";
			g.drawString(text, CENTER - g.getFontMetrics().stringWidth(text) / 2, 180);
			g.setColor(FONT_COLOR);
			g.setFont(FONT_SMALL);
			text = "Press ENTER to Play";
			g.drawString(text, CENTER - g.getFontMetrics().stringWidth(text) / 2, 320);
		}
		//If game is over, show Game over message, score and play again instructions.
		else if(game.isGameOver()){
			g.setColor(FONT_COLOR);
			g.setFont(FONT_LARGE);
			String text = "GAME OVER";
			g.drawString(text, CENTER - g.getFontMetrics().stringWidth(text) / 2, 150);
			g.setFont(FONT_MEDIUM);
			text = "You scored "+game.getScore();
			g.drawString(text, CENTER - g.getFontMetrics().stringWidth(text) / 2, 180);
			g.setFont(FONT_SMALL);
			text = "Press ENTER to Play Again";
			g.drawString(text, CENTER - g.getFontMetrics().stringWidth(text) / 2, 320);
		}
		//Else game is in play, so draw grid, fallen Tetrimo, falling Tetrimo and border.
		else {
			//Draws grid and fallen Tetrimos.
			for(int x = 0; x < BOARD_WIDTH; x++) {
				for(int y = 0; y < HIDDEN_ROWS + BOARD_HEIGHT; y++) {
					Tetrimo tile = tiles[x][y];
					if(tile == null) {
						if(y >= 2){
							g.setColor(BACKGROUND_COLOR);
							g.fillRect(x*TILE_SIZE, (y-2)*TILE_SIZE, TILE_SIZE, TILE_SIZE);
							g.setColor(Color.DARK_GRAY);
							g.drawRect(x* TILE_SIZE, (y-2)* TILE_SIZE, TILE_SIZE, TILE_SIZE);
						}
					}
					else
					{
						drawTile(g, tile, x*TILE_SIZE, (y-HIDDEN_ROWS)*TILE_SIZE);
					}
				}
			}
			
			//Draws falling Tetrimo over grid.
			Tetrimo tetrimo = game.getCurrentTetrimo();
			int positionX = game.getCurrentX();
			int positionY = game.getCurrentY();
			if(tetrimo != null){
				for(int x = 0; x < tetrimo.getSize(); x++){
					for(int y = 0; y < tetrimo.getSize(); y++){
						if(positionY+y >= 2 && Tetrimo.isTetrimoTile(tetrimo.getCurrentShape(), x, y)){
							drawTile(g, tetrimo, (positionX+x)*TILE_SIZE, (positionY+y-HIDDEN_ROWS)*TILE_SIZE);
						}
					}
				}
				//Draws hologram Tetrimo.
				int hologramX = positionX;
				int hologramY = positionY;
				while(isValidPosition(tetrimo, hologramX, hologramY)){
					hologramY++;
				}
				hologramY--;
				for(int x = 0; x < tetrimo.getSize(); x++){
					for(int y = 0; y < tetrimo.getSize(); y++){
						if(Tetrimo.isTetrimoTile(tetrimo.getCurrentShape(), x, y)){
							drawHologramTile(g, tetrimo, (hologramX+x)*TILE_SIZE, (hologramY+y-HIDDEN_ROWS)*TILE_SIZE);
						}
					}
				}
				
			}
		}
		
		g.setColor(LINE_COLOR);
		g.drawRect(0, 0, TILE_SIZE * BOARD_WIDTH, TILE_SIZE * BOARD_HEIGHT);
	}


	//Draws the tile with main color, then draws highlights and shadows line by line, then draws an outline for the tile.
	private void drawTile(Graphics g, Tetrimo tetrimo, int x, int y) {
		g.setColor(tetrimo.getMainColor());
		g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
		
		for(int i = 0; i < BEVEL; i++) {	
			g.setColor(tetrimo.getMainColor().brighter());
			g.drawLine(x+i, y+i, x+TILE_SIZE-i-1, y+i);
			g.drawLine(x+i, y+i+1, x+i, y+TILE_SIZE-i-1);
			
			g.setColor(tetrimo.getMainColor().darker());
			g.drawLine(x+BEVEL-i, y+TILE_SIZE-BEVEL+i, x+TILE_SIZE-BEVEL+i, y+TILE_SIZE-BEVEL+i);
			g.drawLine(x+TILE_SIZE-BEVEL+i, y+BEVEL-i, x+TILE_SIZE-BEVEL+i, y+TILE_SIZE-BEVEL+i-1);
		}	
		
		g.setColor(LINE_COLOR);
		g.drawRect(x, y, TILE_SIZE, TILE_SIZE);
	}
	
	
	//Draws the hologram Tetrimo at the bottom of the drop.
	private void drawHologramTile(Graphics g, Tetrimo tetrimo, int x, int y) {
		//Gets transparent versions of the Tetrimo color.
		Color mainColor = tetrimo.getMainColor();
		mainColor = new Color(mainColor.getRed(), mainColor.getGreen(), mainColor.getGreen(), 100);
		Color highlightColor = tetrimo.getMainColor().brighter();
		highlightColor = new Color(highlightColor.getRed(), highlightColor.getGreen(), highlightColor.getGreen(), 100);
		Color shadowColor = tetrimo.getMainColor().darker();
		shadowColor = new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getGreen(), 100);
		
		//Fills square with main color, then draws highlights and shadows line by line, then draws an outline for the tile.
		g.setColor(mainColor);
		g.fillRect(x+BEVEL, y+BEVEL, TILE_SIZE - 2*BEVEL, TILE_SIZE - 2*BEVEL);
		
		for(int i = 0; i < BEVEL; i++) {	
			g.setColor(highlightColor);
			g.drawLine(x+i, y+i, x+TILE_SIZE-i-1, y+i);
			g.drawLine(x+i, y+i+1, x+i, y+TILE_SIZE-i-1);
			
			g.setColor(shadowColor);
			g.drawLine(x+BEVEL-i, y+TILE_SIZE-BEVEL+i, x+TILE_SIZE-BEVEL+i, y+TILE_SIZE-BEVEL+i);
			g.drawLine(x+TILE_SIZE-BEVEL+i, y+BEVEL-i, x+TILE_SIZE-BEVEL+i, y+TILE_SIZE-BEVEL+i-1);
		}
		
		g.setColor(LINE_COLOR);
		g.drawRect(x, y, TILE_SIZE, TILE_SIZE);		
	}
	
	
	//Returns the value at the grid coords.
	private Tetrimo getTileValue(int x, int y){
		Tetrimo value = tiles[x][y];
		return value;
	}
	
	
	//Sets the tile at the grid coords.
	private void setTileValue(Tetrimo i, int x, int y){
		tiles[x][y] = i;
	}
	
	
	//Returns true or false depending on whether or not the tile at the coords are occupied.
	private boolean isTileOccupied(int x, int y){
		return tiles[x][y] != null;
	}
}

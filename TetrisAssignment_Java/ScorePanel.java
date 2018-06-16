package assignment2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

//Written by Alex Ebbage (1504283) on 12/12/16.
public class ScorePanel extends JPanel{
	private static final long serialVersionUID = 7529309026327100808L;
	
	//Reference to the application instance.
	private Main game;
	
	//Size of the grid tiles in pixels.
	private static final int TILE_SIZE = 16;
	
	//Bevel size of Tetrimo tiles.
	private static final int BEVEL = 4;
	
	//Gap size between components.
	private static final int GAP = 5;
	
	//Width of the boxes in the UI.
	private static final int BOX_WIDTH = TILE_SIZE * 5;
	
	//Width and height of the whole panel.
	private static final int PANEL_WIDTH = BOX_WIDTH + 2*GAP;
	private static final int PANEL_HEIGHT = 640;
	
	//Center of the panel for center-alignment.
	private static final int CENTER = PANEL_WIDTH / 2;
	
	//Height of text and boxes.
	private static final int TEXT_HEIGHT = 15;
	private static final int BOX_HEIGHT = 30;
	
	//Y positions for each string and box.
	private static final int NEXT_BOX_Y = TEXT_HEIGHT + GAP;
	private static final int NEXT_STRING_Y = GAP + TEXT_HEIGHT;
	private static final int SCORE_STRING_Y = NEXT_BOX_Y + 3*BOX_WIDTH + 4*GAP + TEXT_HEIGHT + 30;
	private static final int SCORE_BOX_Y = SCORE_STRING_Y + GAP;
	private static final int LEVEL_STRING_Y = SCORE_BOX_Y + BOX_HEIGHT + TEXT_HEIGHT + 30;
	private static final int LEVEL_BOX_Y = LEVEL_STRING_Y + GAP;
	private static final int LINES_STRING_Y = LEVEL_BOX_Y + BOX_HEIGHT + TEXT_HEIGHT + 30;
	private static final int LINES_BOX_Y = LINES_STRING_Y + GAP;
	
	//Colors for different parts.
	private static final Color LINE_COLOR = new Color(40, 40, 40);
	private static final Color RED_COLOR = new Color(240, 0 ,0);
	private static final Color FONT_COLOR = new Color(40, 40, 40);
	private static final Color BACKGROUND_COLOR =new Color(255, 255, 255);
	
	//Font for text in UI.
	private static final Font FONT_MEDIUM = new Font("Eras Demi ITC", Font.PLAIN, 20);
	
	
	//Constructor for score panel.
	public ScorePanel(Main game){
		this.game = game;
		setPreferredSize(new Dimension(PANEL_WIDTH+GAP, PANEL_HEIGHT));
		setBackground(BACKGROUND_COLOR);
	}

	
	//Renders the score panel.
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//Sets anti-aliasing
		g2.setRenderingHints(Main.getRendererantialiasing());
		g2.setRenderingHints(Main.getRenderertextantialiasing());
		
		//Draws the next tetrimo boxes and tetrimos inside.
		g.setColor(FONT_COLOR);
		g.setFont(FONT_MEDIUM);
		String text = "NEXT";
		g.drawString(text, CENTER - g.getFontMetrics().stringWidth(text) / 2, NEXT_STRING_Y);
		g.setColor(RED_COLOR);
		g.drawRect((CENTER - (BOX_WIDTH / 2)), NEXT_BOX_Y + GAP, BOX_WIDTH, BOX_WIDTH);
		g.drawRect((CENTER - (BOX_WIDTH / 2)) + GAP, NEXT_BOX_Y + 2*GAP, BOX_WIDTH-2*GAP, BOX_WIDTH-2*GAP);
		g.setColor(LINE_COLOR);
		g.drawRect((CENTER - (BOX_WIDTH / 2)), NEXT_BOX_Y + 2 * GAP + BOX_WIDTH, BOX_WIDTH, BOX_WIDTH);
		g.drawRect((CENTER - (BOX_WIDTH / 2)), NEXT_BOX_Y + 3 * GAP + 2 * BOX_WIDTH, BOX_WIDTH, BOX_WIDTH);
		Tetrimo nextTetrimo = game.getNextTetrimo();
		Tetrimo secondTetrimo = game.getSecondTetrimo();
		Tetrimo thirdPiece = game.getThirdTetrimo();
		if(!game.isGameOver() && (game.getNextTetrimo() != null) && (game.getSecondTetrimo() != null) && (game.getThirdTetrimo() != null)) {
			drawPreviewPiece(g, nextTetrimo, 0);
			drawPreviewPiece(g, secondTetrimo, 1);
			drawPreviewPiece(g, thirdPiece, 2);
		}
		
		//Score, level and lines boxes and strings.
		g.setColor(FONT_COLOR);
		text = "SCORE";
		g.drawString(text, CENTER - g.getFontMetrics().stringWidth(text) / 2, SCORE_STRING_Y);
		g.drawRect(CENTER - (BOX_WIDTH / 2), SCORE_BOX_Y, BOX_WIDTH, BOX_HEIGHT);
		text = "" + game.getScore();
		g.drawString(text, CENTER - g.getFontMetrics().stringWidth(text) / 2, SCORE_BOX_Y + TEXT_HEIGHT + 8);
		
		text = "LEVEL";
		g.drawString(text, CENTER - g.getFontMetrics().stringWidth(text) / 2, LEVEL_STRING_Y);
		g.drawRect(CENTER - (BOX_WIDTH / 2), LEVEL_BOX_Y, BOX_WIDTH, BOX_HEIGHT);
		text = "" + game.getLevel();
		g.drawString(text, CENTER - g.getFontMetrics().stringWidth(text) / 2, LEVEL_BOX_Y + TEXT_HEIGHT + 8);
		
		text = "LINES";
		g.drawString(text, CENTER - g.getFontMetrics().stringWidth(text) / 2, LINES_STRING_Y);
		g.drawRect(CENTER - (BOX_WIDTH / 2), LINES_BOX_Y, BOX_WIDTH, BOX_HEIGHT);
		text = "" + game.getLines();
		g.drawString(text, CENTER - g.getFontMetrics().stringWidth(text) / 2, LINES_BOX_Y + TEXT_HEIGHT + 8);
	}

	
	//Draws the next Tetrimo tetrimo inside a box.
	private void drawPreviewPiece(Graphics g, Tetrimo tetrimo, int position){
		int ySpace = GamePanel.topSpace(tetrimo.getSpawnShape());
		int yPosition = NEXT_BOX_Y + (GAP*(position+1)+(BOX_WIDTH*position)+(BOX_WIDTH / 2)) - (tetrimo.getHeight() * TILE_SIZE/2)+ ((-ySpace) * TILE_SIZE);
		int xSpace = GamePanel.leftSpace(tetrimo.getSpawnShape());
		int xPosition = (CENTER - (tetrimo.getSize() * TILE_SIZE/2)) + ((-xSpace)*TILE_SIZE);

		for(int x = 0; x < tetrimo.getSize(); x++) {
			for(int y = 0; y < tetrimo.getSize(); y++) {
				if(Tetrimo.isTetrimoTile(tetrimo.getSpawnShape(), x, y)){
					drawTile(tetrimo, xPosition + (x * TILE_SIZE) , yPosition + (y * TILE_SIZE) , g);
				}
			}
		}
	}

	
	//Draws a Tetrimo tile with a highlight and shadow bevel effect.
	private void drawTile(Tetrimo tetrimo, int x, int y, Graphics g) {
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
}
package assignment2;

import java.awt.BorderLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

//Written by Alex Ebbage (1504283) on 12/12/16.
public class Main extends JFrame implements ActionListener{

	private static final long serialVersionUID = 8489441444146256589L;
	
	//Anti-aliasing for rendering .
	private static final RenderingHints RENDERER_TEXT_AA = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	private static final RenderingHints RENDERER_AA = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	
	//Panel instance with Tetris game on.
	private GamePanel gamePanel;
	
	//Panel instance with game score on.
	private ScorePanel scorePanel;
	
	//Timer for keeping regular updates.
	private Timer timer;
	
	//17ms delay to keep game at ~60FPS.
	private static final int DELAY = 17;
	
	//Default time for tetrimo to fall a tile (1000ms).
	private int dropTimer = 1000;
	
	//Keeps position of time left before next drop.
	private double dropTimerPos = dropTimer;
	
	//Default speed multiplier for falling.
	private double speed = 1;
	
	//Temporary speed value so value not lost whilst speeding up and down.
	private double speedTemp = speed;
	
	//Max speed value for when making Tetrimo fall rapidly.
	private static final int MAX_SPEED = 30;
	
	//Boolean to show whether game is new or not.
	private boolean isNewGame;
	
	//Boolean to show whether game is new or not.
	private boolean isGameOver;
	
	//Current position of the falling Tetrimo.
	private int currentX = 5;
	private int currentY = 0;
	
	//Current level player is on.
	private int level;
	
	//Current score the player has.
	private int score;
	
	//Current amount of lines player has cleared.
	private int lines;
	
	//The current Tetrimo which is falling.
	private Tetrimo currentTetrimo;
	
	//The next 3 Tetrimos that are going to fall.
	private Tetrimo nextTetrimo;
	private Tetrimo secondTetrimo;
	private Tetrimo thirdTetrimo;
	
	//Random used to select the next random Tetrimo.
	private Random random;
	
	
	
	//The main method to run the application, creates an instance of the application.
	public static void main(String[] args){
		Main game = new Main();
	}
	
	
	//Constructor for the application.
	private Main(){
		//Settings for the window.
		super("TETRIS by Alex Ebbage 1504283");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		//Adds game and score panel onto window.
		this.gamePanel = new GamePanel(this);
		this.scorePanel = new ScorePanel(this);		
		add(gamePanel, BorderLayout.CENTER);
		add(scorePanel, BorderLayout.EAST);
		pack();
		
		//Instantiates random, the timer, starts the timer and initiates some values for the game.
		this.random = new Random();
		timer = new Timer(DELAY, this);
		timer.start();
		this.setNextTetrimo(Tetrimo.values()[random.nextInt(7)]);
		this.setNewGame(true);
		spawnPiece();
			
		//Mouse listener allowing the user to input mouse commands.
		addMouseListener(new MouseAdapter(){		
			 @Override
			    public void mouseClicked(MouseEvent e) {
					switch(e.getButton()){
					
						case MouseEvent.BUTTON1:
							if(gamePanel.isValidPosition(getCurrentTetrimo(), getCurrentX()-1, getCurrentY())){
								setCurrentX(getCurrentX() - 1);
							}
							break;
							
						case MouseEvent.BUTTON3:
							if(gamePanel.isValidPosition(getCurrentTetrimo(), getCurrentX()+1, getCurrentY())){
								setCurrentX(getCurrentX() + 1);
							}
							break;
							
				    	case MouseEvent.BUTTON2:
							gamePanel.rotate(getCurrentTetrimo(), 1);
							break;
						}
			    }
		});
		
		//Key listener allowing the user to input keyboard commands.
		addKeyListener(new KeyAdapter(){			
			@Override
			public void keyPressed(KeyEvent e){
				switch(e.getKeyCode()){
				
				//Move falling Tetrimo left if move is valid.
				case KeyEvent.VK_A:
					if(gamePanel.isValidPosition(getCurrentTetrimo(), getCurrentX()-1, getCurrentY())){
						setCurrentX(getCurrentX() - 1);
					}
					break;
					
				case KeyEvent.VK_LEFT:
					if(gamePanel.isValidPosition(getCurrentTetrimo(), getCurrentX()-1, getCurrentY())){
						setCurrentX(getCurrentX() - 1);
					}
					break;
				
				//Move falling Tetrimo right if move is valid.
				case KeyEvent.VK_D:
					if(gamePanel.isValidPosition(getCurrentTetrimo(), getCurrentX()+1, getCurrentY())){
						setCurrentX(getCurrentX() + 1);
					}
					break;
					
				case KeyEvent.VK_RIGHT:
					if(gamePanel.isValidPosition(getCurrentTetrimo(), getCurrentX()+1, getCurrentY())){
						setCurrentX(getCurrentX() + 1);
					}
					break;
				
				//Rotates Tetrimo anti-clockwise.
				case KeyEvent.VK_Q:
					gamePanel.rotate(getCurrentTetrimo(), 0);
					break;
				
				//Rotates Tetrimo clockwise.
				case KeyEvent.VK_E:
					gamePanel.rotate(getCurrentTetrimo(), 1);
					break;
					
				case KeyEvent.VK_UP:
					gamePanel.rotate(getCurrentTetrimo(), 0);
					break;
				
				//Sets speed to max to drop pieces quickly.
				case KeyEvent.VK_S:
					setSpeed(MAX_SPEED);
					break;
					
				case KeyEvent.VK_DOWN:
					setSpeed(MAX_SPEED);
					break;
				
				//Starts and restarts game.
				case KeyEvent.VK_ENTER:
					if(isGameOver() || isNewGame()) {
						resetGame();
					}
					break;
				
				}
			}

			//When down or s key is released, reverts the speed to a normal value instead of max speed.
			@Override
			public void keyReleased(KeyEvent e){
				switch(e.getKeyCode()){
				
				case KeyEvent.VK_S:
					setSpeed(getSpeedTemp());
					break;
					
				case KeyEvent.VK_DOWN:
					setSpeed(getSpeedTemp());
					break;
				}
			}
			
		});
	}

	
	//The game loop, called every time there is an action, every 17ms because of the timer.
	//If the game is in progress, it is updated then rendered.
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!(isGameOver() || isNewGame())){
			updateGame();	
		}
	}
	
	
	//Updates the game then renders it.
	//Deducts the delay*speed from the dropTimer, so when it's below 0, the Tetrimo either falls if
	//the fall is a valid move, or it had collided, in which case the Tetrimo is added to the grid,
	//lines are checked for completion and new Tetrimo is spawned at the top. Then the dropTimer is
	//reset.
	private void updateGame(){
		dropTimerPos -= DELAY*getSpeed();
		if(dropTimerPos < 0){
			if(gamePanel.isValidPosition(getCurrentTetrimo(), getCurrentX(), getCurrentY()+1)){
				setCurrentY(getCurrentY() + 1);
			}
			else{
				if(getSpeed() != MAX_SPEED){
					setSpeedTemp(getSpeed());
				}
				setSpeed(getSpeedTemp());
				gamePanel.addTetrimoToGrid(getCurrentTetrimo(), getCurrentX(), getCurrentY());
				gamePanel.checkLines();
				spawnPiece();
			}
			dropTimerPos = dropTimer;
		}
		gamePanel.repaint();
		scorePanel.repaint();
	}
	
	
	//Spawns a new Tetrimo.
	//Shifts all the pieces up the chain, then find a new random third Tetrimo. Also, sets current
	//position to the top of the grid. If a new tetrimo can't be added, then the game is over.
	private void spawnPiece(){
		if(this.getNextTetrimo() == null){
			this.setNextTetrimo(Tetrimo.values()[random.nextInt(7)]);
			this.setSecondTetrimo(random(this.getNextTetrimo()));
			this.setNextTetrimo(random(this.getSecondTetrimo()));
		}
		this.setCurrentTetrimo(this.getNextTetrimo());
		this.setNextTetrimo(this.getSecondTetrimo());
		this.setSecondTetrimo(this.getThirdTetrimo());
		this.setThirdTetrimo(random(this.getSecondTetrimo()));
		this.setCurrentX(getCurrentTetrimo().getSpawnX());
		this.setCurrentY(getCurrentTetrimo().getSpawnY());
		if(!gamePanel.isValidPosition(getCurrentTetrimo(), getCurrentX(), getCurrentY())){
			this.setGameOver(true);
			timer.stop();
		}
	}
	
	
	//Finds a random Tetrimo.
	//Reduces the chance of the same Tetrimo being selected in a row.
	private Tetrimo random(Tetrimo previous){
		Tetrimo lastPiece = previous;
		Tetrimo nextTetrimo = Tetrimo.values()[random.nextInt(7)];	
		for(int i = 0; i < 2; i++){
			if(nextTetrimo.equals(lastPiece)){
				nextTetrimo = Tetrimo.values()[random.nextInt(7)];
			}
		}
		return nextTetrimo;
	}
	
	
	//Resets the game.
	//Called when the player restarts after a game over, basically sets score to 0 and
	//restarts the timer.
	private void resetGame(){
		this.setNewGame(false);
		this.setGameOver(false);	
		this.setScore(0);
		this.setLevel(1);
		this.setLines(0);
		this.setSpeed(1);
		this.setSpeedTemp(1);
		gamePanel.resetGrid();
		spawnPiece();
		timer.start();
	}


	//Returns the score.
	public int getScore() {
		return score;
	}


	//Sets the score.
	public void setScore(int score) {
		this.score = score;
	}


	//Returns the lines cleared.
	public int getLines() {
		return lines;
	}


	//Sets the lines cleared.
	public void setLines(int lines) {
		this.lines = lines;
	}


	//Gets the level.
	public int getLevel() {
		return level;
	}


	//Sets the level.
	public void setLevel(int level) {
		this.level = level;
	}


	//Gets the temporary speed value.
	public double getSpeedTemp() {
		return speedTemp;
	}


	//Sets the temporary speed value.
	public void setSpeedTemp(double speedTemp) {
		this.speedTemp = speedTemp;
	}


	//Gets the speed value.
	public double getSpeed() {
		return speed;
	}


	//Sets the speed value.
	public void setSpeed(double speed) {
		this.speed = speed;
	}


	//Gets the falling Tetrimo's X position.
	public int getCurrentX() {
		return currentX;
	}


	//Sets the falling Tetrimo's X position.
	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}


	//Gets the falling Tetrimo's Y position.
	public int getCurrentY() {
		return currentY;
	}


	//Sets the falling Tetrimo's Y position.
	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}


	//Gets the falling Tetrimo.
	public Tetrimo getCurrentTetrimo() {
		return currentTetrimo;
	}


	//Sets the falling Tetrimo.
	public void setCurrentTetrimo(Tetrimo currentTetrimo) {
		this.currentTetrimo = currentTetrimo;
	}


	//Gets the renderer hints for AA.
	public static RenderingHints getRendererantialiasing() {
		return RENDERER_AA;
	}


	//Gets the renderer hints for text AA.
	public static RenderingHints getRenderertextantialiasing() {
		return RENDERER_TEXT_AA;
	}


	//Returns isNewGame value.
	public boolean isNewGame() {
		return isNewGame;
	}


	//Sets isNewGame value.
	public void setNewGame(boolean isNewGame) {
		this.isNewGame = isNewGame;
	}


	//Gets isGameOver value.
	public boolean isGameOver() {
		return isGameOver;
	}


	//Sets isGameOver value.
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}


	//Gets the next Tetrimo to fall.
	public Tetrimo getNextTetrimo() {
		return nextTetrimo;
	}


	//Sets the next Tetrimo to fall.
	public void setNextTetrimo(Tetrimo nextTetrimo) {
		this.nextTetrimo = nextTetrimo;
	}


	//Gets the second next Tetrimo to fall.
	public Tetrimo getSecondTetrimo() {
		return secondTetrimo;
	}

	
	//Sets the second next Tetrimo to fall.
	public void setSecondTetrimo(Tetrimo secondTetrimo) {
		this.secondTetrimo = secondTetrimo;
	}

	
	//Gets the third next Tetrimo to fall.
	public Tetrimo getThirdTetrimo() {
		return thirdTetrimo;
	}


	//Sets the third next Tetrimo to fall.
	public void setThirdTetrimo(Tetrimo thirdTetrimo) {
		this.thirdTetrimo = thirdTetrimo;
	}
}

package assignment2;

import java.awt.Color;

//Written by Alex Ebbage (1504283) on 12/12/16.
//Tetrimo enum used because of finite variety of Tetrimo pieces.
public enum Tetrimo{
	I(new Color(0,229,240), 4, 1, 
		new boolean[][] {
			{false, true, false, false},
			{false, true, false, false},
			{false, true, false, false},
			{false, true, false, false}},
			
		new boolean[][] {
			{false, true, false, false},
			{false, true, false, false},
			{false, true, false, false},
			{false, true, false, false}
	}),
	
	J(new Color(30,110,255), 3, 2,
		new boolean[][] {
			{false, false, true},
			{false, false, true},
			{false, true, true}},
			
		new boolean[][] {
			{false, false, true},
			{false, false, true},
			{false, true, true}
	}),
	
	L(new Color(255,150,0), 3, 2,
		new boolean[][] {
			{false, true, true},
			{false, false, true},
			{false, false, true}},
			
		new boolean[][] {
			{false, true, true},
			{false, false, true},
			{false, false, true}
	}),
	
	O(new Color(240,240,0), 2, 2,
		new boolean[][] {
			{true, true},
			{true, true}},
			
		new boolean[][] {
			{true, true},
			{true, true}
	}),
	
	S(new Color(25,205,0), 3, 2,
		new boolean[][] {
			{false, true, false},
			{false, true, true},
			{false, false, true}},
			
		new boolean[][] {
			{false, true, false},
			{false, true, true},
			{false, false, true}
	}),
	
	Z(new Color(220,0,0), 3, 2,
		new boolean[][] {
			{false, false, true},
			{false, true, true},
			{false, true, false}},
			
		new boolean[][] {
			{false, false, true},
			{false, true, true},
			{false, true, false}
	}),
	
	T(new Color(217,0,240), 3, 2,
		new boolean[][] {
			{false, false, true},
			{false, true, true},
			{false, false, true}},
			
		new boolean[][] {
			{false, false, true},
			{false, true, true},
			{false, false, true}
	});

	//Color of the Tetrimo.
	private final Color mainColor;
	
	//Spawn position of the Tetrimo.
	private final int spawnX;
	private final int spawnY;
	
	//Size of the Tetrimo array.
	private final int size;
	
	//Height for centering in preview.
	private final int height;
	
	//Spawn shape for the Tetrimo.
	private final boolean[][] spawnShape;
	
	//Current shape of the Tetrimo.
	private boolean[][] currentShape;
	
	
	//Constructor for the Tetrimos
	private Tetrimo(Color color, int size, int height, boolean[][] spawnShape, boolean[][] currentShape){
		this.mainColor = color;
		this.size = size;
		this.height = height;
		this.spawnShape = spawnShape;
		this.currentShape = currentShape;	
		this.spawnX = 5 - (size + 1) / 2;
		this.spawnY = GamePanel.topSpace(spawnShape);
	}
	
	
	//Returns true if given part of shape is solid.
	public static boolean isTetrimoTile(boolean[][] shape, int x, int y){
		return shape[x][y];
	}
	
	
	//Gets the current shape of the Tetrimo.
	public boolean[][] getCurrentShape() {
		return currentShape;
	}

	
	//Sets the current shape of the Tetrimo.
	public void setCurrentShape(boolean[][] currentShape) {
		this.currentShape = currentShape;
	}

	
	//Gets the main color of the Tetrimo.
	public Color getMainColor() {
		return mainColor;
	}

	
	//Gets the size of the Tetrimo.
	public int getSize() {
		return size;
	}

	
	//Gets the height of the Tetrimo.
	public int getHeight() {
		return height;
	}
	
	
	//Gets the spawn shape of the Tetrimo.
	public boolean[][] getSpawnShape() {
		return spawnShape;
	}
	
	
	//Gets the x value of the Tetrimo spawn.
	public int getSpawnX() {
		return spawnX;
	}

	
	//Gets the y value of the Tetrimo spawn.	
	public int getSpawnY() {
		return spawnY;
	}

}

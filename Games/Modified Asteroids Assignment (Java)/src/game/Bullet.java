package game;

import java.awt.Color;
import java.awt.Graphics2D;
import utilities.Vector2D;

public class Bullet extends GameObject{
	public static final Color COLOR = new Color(255, 208, 50);
	Vector2D zoneSize;
	
	
	public Bullet(Vector2D position, Vector2D velocity, Vector2D zoneSize) {
		super(position, velocity, 2, 1);
		this.zoneSize = zoneSize;
	}

	@Override
	public void update(){
		position.addScaled(velocity, Constants.DT);
		
		if((position.x < 0) || (position.x > zoneSize.x) ||(position.y < 0) || (position.y > zoneSize.y)) isAlive = false;

	}
	

	@Override
	public void hit(GameObject other){
		isAlive = false;
	}
	

	@Override
	public void draw(Graphics2D g) {
		g.setColor(COLOR);
		g.fillOval((int)(position.x - radius), (int)(position.y - radius), (int)(2 * radius), (int)(2 * radius));
	}
}

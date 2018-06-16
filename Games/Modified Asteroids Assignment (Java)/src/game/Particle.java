package game;

import java.awt.Color;
import java.awt.Graphics2D;
import utilities.Vector2D;

public class Particle extends GameObject{

	private Color color;
	private int r, g, b, life;
	private double a, size;
	private Vector2D lastPosition;
	
	
	public Particle(Vector2D p, Vector2D v, Color c, int s){
		super(p, v, 1, 0.01);
		this.lastPosition = new Vector2D(p);
        this.r = c.getRed();
        this.g = c.getGreen();
        this.b = c.getBlue();
		this.color = c;
		this.size = s;
		this.a = 255;
		this.life = 0;
	}

	//Particle fades as it's life goes on.
	public void update(){
    	if(life < 100){
    		life++;
    		a -= life * 0.06;
   		
    		if(a < 0){
    			isAlive = false;
    			a = 0;
    			life = 100;
    		}
    		else{
        		color = new Color(r, g, b, (int)a);
        		lastPosition = new Vector2D(position);
        		position = new Vector2D(position.addScaled(velocity, Constants.DT));
    		}   		
    	}
    	else{
    		isAlive = false;
    	}
	}
	
	
	public void draw(Graphics2D g){
		g.setColor(color);
		g.drawLine((int)lastPosition.x, (int)lastPosition.y, (int)position.x, (int)position.y);
	}
	

	//Creates a particle explosion using a variety of parameters.
	public static void explosion(Vector2D position, double radius, double size, int count, int colorType, Game game){
		Vector2D startPosition = new Vector2D(position);  
		Color color1 = new Color(255,255,255);
		Color color2 = new Color(255,255,255);			
		color1 = new Color(255,135,0);			
		color2 = new Color(191, 133, 107);
		Vector2D startVelocity;
		
		for(int i = 0; i < count; i++){
			startVelocity = new Vector2D(Math.random() - 0.5, Math.random() - 0.5);
			startVelocity.normalise();	
			startVelocity.mult(size);
			
			
			double speedModifier = (100 - Math.random()*80);
			speedModifier /= 100;
			startVelocity = startVelocity.mult(speedModifier*size);
			
			int choice = (int)(Math.random());
			if(choice == 0){
				game.spawningObjects.add(new Particle(startPosition, startVelocity, color1, (int)size));
			}else{
				game.spawningObjects.add(new Particle(startPosition, startVelocity, color2, (int)size));
			}
		}
	}


	public void hit(GameObject other) {
	}
	
	
}

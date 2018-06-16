package game;

import java.awt.Graphics2D;
import utilities.Vector2D;

public abstract class GameObject {

	public Vector2D position, velocity;
	public double radius, volume, density, mass;
	public boolean isAlive;
	public Game game;
	public boolean physicsOn;

	
	public GameObject(Vector2D p, Vector2D v, double r, double density){
		this.game = game;
		isAlive = true;
		this.position = new Vector2D(p);
		this.velocity = new Vector2D(v);
		this.radius = r;
		this.density = density;
		this.volume = Math.PI * radius * radius;
		this.mass = volume * density;
		this.physicsOn = false;
	}
	
	
	public abstract void hit(GameObject other);

	
	public void update(){
		position.addScaled(velocity, Constants.DT);

		if(game != null){
			if(position.x > game.currentZone.size.x) {
				position.x = game.currentZone.size.x;
				velocity.x *= -1;
			}
			else if(position.x < 0){
				position.x = 0;
				velocity.x *= -1;
			}
			if(position.y > game.currentZone.size.y) {
				position.y = game.currentZone.size.y;
				velocity.y *= -1;
			}
			else if(position.y < 0){
				position.y = 0;
				velocity.y *= -1;
			}
		}
	}

	
	public boolean overlap(GameObject other){
		double distance = this.position.dist(other.position);
		double radiusSum = this.radius + other.radius;
		return (radiusSum > distance);
	}
	
	
	public void collisionHandling(GameObject other){
		if((this.overlap(other))){
			this.hit(other);
			other.hit(this);
			if(this.physicsOn && other.physicsOn){
				physicsCollisionHandling(other);
			}
		}
	}

	public void physicsCollisionHandling(GameObject other){
		//Pushes the overlapping objects apart to get rid of sticking issue.
		Vector2D delta = new Vector2D(position).subtract(other.position);
		double length = delta.mag();
		Vector2D mtd = new Vector2D(delta.mult(((radius + other.radius) - length) / length));
		position.addScaled(mtd, 0.5);
		other.position.subtractScaled(mtd, 0.5);

		Vector2D normal = new Vector2D(mtd).normalise();

		//Checks if objects are moving apart.
		Vector2D relativeVelocity = new Vector2D(velocity).subtract(other.velocity);
		double impactSpeed = relativeVelocity.dot(normal);
		if(impactSpeed > 0) return;

		//Calculates new velocity vectors for both objects.
		double cor = 0.8;
		double massA = 1 / mass;
		double massB = 1 / other.mass;

		double impulse = -(cor * impactSpeed) / (massA + massB);
		Vector2D impulseVector = new Vector2D(normal).mult(impulse);
		velocity.addScaled(impulseVector, massA);
		other.velocity.subtractScaled(impulseVector, massB);
	}

	
	public abstract void draw(Graphics2D g);

}

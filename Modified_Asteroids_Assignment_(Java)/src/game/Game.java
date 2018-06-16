package game;

import java.util.ArrayList;
import java.util.Random;

import utilities.JEasyFrame;
import utilities.Vector2D;

public class Game{
	Keys ctrl;
	Ship ship;
	Camera camera;
	GUI gui;
	Zone currentZone;
	World world;
	Vector2D viewSize, playerPosition;
	SoundManager soundManager;
	public ArrayList<Bullet> bullets;
	public ArrayList<Asteroid> asteroids;
	public ArrayList<LootCrate> lootCrates;
	public ArrayList<Particle> particles;
	public ArrayList<Ship> players;
	public ArrayList<GameObject> spawningObjects;
	static boolean isGameOver = false;

	
	public Game() {
		spawningObjects = new ArrayList<GameObject>();
		lootCrates = new ArrayList<LootCrate>();
		particles = new ArrayList<Particle>();
		asteroids = new ArrayList<Asteroid>();
		bullets = new ArrayList<Bullet>();
		players = new ArrayList<Ship>();

		viewSize = new Vector2D(1920, 1080);
		world = new World(this);
		currentZone = world.getHub();

		playerPosition = new Vector2D(currentZone.size).mult(0.5);
		ctrl = new Keys(); 
		ship = new Ship(this, playerPosition, ctrl);
		players.add(ship);

		camera = new Camera(playerPosition, viewSize, currentZone.size);
		gui = new GUI(this);
		soundManager = new SoundManager();

		//makeRandomObjcets();
    }


    //FOR TESTING PURPOSES ONLY
	public void makeRandomObjcets(){
		Random r = new Random();
		for(int i = 0; i < 5; i++){
			LootCrate lootCrate = new LootCrate(new Vector2D((currentZone.size.x / 2) -100 - i*50, (currentZone.size.y / 2) -  i*10), (int)(Math.random()*Loot.values().length));
			lootCrates.add(lootCrate);
		}
		// asteroids.add(Asteroid.makeRandomAsteroid(this));
	}

	
	public static void main(String[] args) throws Exception {
		Game game = new Game();
		View view = new View(game);
		new JEasyFrame(view, "Dangerous Elite").addKeyListener(game.ctrl);

		//Run the game
		while (!isGameOver) {
			game.update();
			view.repaint();
			Thread.sleep(Constants.DELAY);
		}
	}
	
	
	public void updateObjects(){
		//Update ships
		for (int i = 0; i < players.size(); i++) {
			Ship ship = players.get(i);
			if(ship.isAlive){
				ship.update();
			}
			else{
				Particle.explosion(ship.position, (int)ship.radius, 20, 100, 1, this);
                soundManager.bangM();
                ship.lives--;
				if(ship.lives > 0) ship.respawn();
				camera = new Camera(playerPosition, viewSize, currentZone.size);

			}
		}
		
		//Update asteroids
		for (int i = 0; i < asteroids.size(); i++) {
			Asteroid asteroid = asteroids.get(i);
			if(asteroid.isAlive){
				asteroid.update();
			}
			else{
				Particle.explosion(asteroid.position, (int)asteroid.radius, asteroid.size * 6, asteroid.size * asteroid.size * 15, 1, this);
                switch (asteroid.size) {
                    case 1:
                    case 2:
                        soundManager.bangS();
                        break;
                    case 3:
                        soundManager.bangM();
                        break;
                    case 4:
                        soundManager.bangL();
                        break;
                }
                asteroids.remove(i);
			}
		}

		//Update bullets
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			if(bullet.isAlive){
				bullet.update();
			}
			else{
				Particle.explosion(bullet.position, (int)bullet.radius, 10, 5, 1, this);
				bullets.remove(i);
			}
		}

		//Update particles
		for (int i = 0; i < particles.size(); i++) {
			Particle particle = particles.get(i);
			if(particle.isAlive){
				particle.update();
			}
			else{
				particles.remove(i);
			}
		}

		//Update loot
		for (int i = 0; i < lootCrates.size(); i++) {
			LootCrate lootItem = lootCrates.get(i);
			if(lootItem.isAlive){
				lootItem.update();
			}
			else{
				lootCrates.remove(i);
			}
		}
	}
	

	public void checkCollisions(){
		//Ship collisions
		for(Ship ship: players){
			for(Asteroid asteroid: asteroids)
				ship.collisionHandling(asteroid);
			for(LootCrate lootCrate : lootCrates)
				ship.collisionHandling(lootCrate);
		}
		
		//Bullet collisions
		for(Bullet bullet: bullets){
			for(Asteroid asteroid: asteroids)
				bullet.collisionHandling(asteroid);
		}

		//Asteroid collisions
		for(int i = 0; i < asteroids.size(); i++){
			for(int j = i+1; j < asteroids.size(); j++)
				asteroids.get(i).collisionHandling(asteroids.get(j));
		}
	}
	
	//boolean test = true;
	public void update() {
		synchronized (Game.class) {
			if (spawningObjects.size() > 0) {
				for (int i = 0; i < spawningObjects.size(); i++) {
					GameObject o = spawningObjects.get(i);
					if (o instanceof Asteroid){
						asteroids.add((Asteroid) o);
						//System.out.println("SPAWN ASTEROID");
					}
					if (o instanceof Bullet){
						bullets.add((Bullet) o);
						//System.out.println("SPAWN BULLET");
					}
					if (o instanceof LootCrate){
						lootCrates.add((LootCrate) o);
						//System.out.println("SPAWN LOOTCRATE");d
					}
					if (o instanceof Particle){
						particles.add((Particle) o);
						//System.out.println("SPAWN PARTICLE");
					}

				}
				spawningObjects = new ArrayList<GameObject>();
			}

			updateObjects();
			checkCollisions();
			camera.update(ship.position);
		}

			if (asteroids.size() < 250) for (int i = 0; i < 50; i++) asteroids.add(new Asteroid(this));

	}
}
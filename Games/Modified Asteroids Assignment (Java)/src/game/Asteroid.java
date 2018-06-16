package game;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Asteroid extends GameObject {

    public static final double MAX_SPEED = 60;
    public static final Color COLOR = new Color(191, 133, 107);
    public static final Color COLORB = new Color(127, 85, 66);
    private int[] XP, YP;
    Loot contents;
    double life;
    int size;
    Vector2D direction;
    double spin;


    //Creates new asteroid after breaking it apart.
    public Asteroid(Game game, Vector2D position, Vector2D velocity, int size) {
        super(position, velocity, 10, 1);
        this.physicsOn = true;
        this.game = game;

        this.direction = new Vector2D(Math.random() - 0.5, Math.random() - 0.5).normalise();
        this.spin = ((Math.PI * Math.random()) - (Math.PI / 2)) * 0.1;

        int numberOfPoints = (int) (7 + (Math.random() * this.size * 7));
        int MIN_RADIUS = size * 8;
        int MAX_RADIUS = size * 12;
        int[] xPoints = new int[numberOfPoints];
        int[] yPoints = new int[numberOfPoints];
        double newRadius = 0;
        for (int i = 0; i < numberOfPoints; i++) {
            double theta = Math.PI * 2 * (i + Math.random()) / numberOfPoints;
            double radius = MIN_RADIUS + Math.random() * (MAX_RADIUS - MIN_RADIUS);
            Vector2D polar = new Vector2D(Vector2D.polar(theta, radius));
            newRadius += polar.mag();
            xPoints[i] = (int) polar.x;
            yPoints[i] = (int) polar.y;
        }
        newRadius /= numberOfPoints;
        this.radius = newRadius;
        this.XP = xPoints;
        this.YP = yPoints;

        life = mass;

        int contentsSelecter = (int) (Math.random() * 100);
        if (contentsSelecter < (50 * (radius - 8) / 48)) {
            contents = Loot.values()[(int) (Math.random() * 6)];
        } else contents = null;
    }


    //Creates original asteroids
    public Asteroid(Game game) {
        super(new Vector2D(), new Vector2D(), 10, 1);
        this.physicsOn = true;
        this.game = game;
        this.size = (int) (1 + Math.random() * 3);
        this.position = new Vector2D(Math.random() * game.currentZone.size.x, Math.random() * game.currentZone.size.y);
        this.velocity = new Vector2D(Math.random() - 0.5, Math.random() - 0.5).normalise();
        this.velocity.mult(Math.random() * MAX_SPEED / this.size + Math.random());

        this.direction = new Vector2D(Math.random() - 0.5, Math.random() - 0.5).normalise();
        this.spin = ((Math.PI * Math.random()) - (Math.PI / 2)) * 0.1;

        int numberOfPoints = (int) (7 + (Math.random() * this.size * 7));
        int MIN_RADIUS = this.size * 8;
        int MAX_RADIUS = this.size * 12;
        int[] xPoints = new int[numberOfPoints];
        int[] yPoints = new int[numberOfPoints];
        double newRadius = 0;
        for (int i = 0; i < numberOfPoints; i++) {
            double theta = Math.PI * 2 * (i + Math.random()) / numberOfPoints;
            double radius = MIN_RADIUS + Math.random() * (MAX_RADIUS - MIN_RADIUS);
            Vector2D polar = new Vector2D(Vector2D.polar(theta, radius));
            newRadius += polar.mag();
            xPoints[i] = (int) polar.x;
            yPoints[i] = (int) polar.y;
        }
        newRadius /= numberOfPoints;
        this.radius = newRadius;
        this.XP = xPoints;
        this.YP = yPoints;

        life = mass;

        int contentsSelecter = (int) (Math.random() * 100);
        if (contentsSelecter < (100 * (radius - 8) / 48)) {
            contents = Loot.values()[(int) (Math.random() * 6)];
        } else contents = null;
    }


    @Override
    public void update() {
        super.update();
        this.direction.rotate(this.spin * Constants.DT);
    }


    //Breaks an asteroid into new asteroids.
    public void breakAsteroid() {
        Vector2D newPosition = new Vector2D();
        Vector2D newVelocity = new Vector2D();
        int newSize = size - 1;
        for (int i = 0; i < size; i++) {
        int[] xPoints = new int[size];
        int[] yPoints = new int[size];
        for (int j = 0; j < size; j++) {
            double theta = Math.PI * 2 * j / size;
            double radius = newSize * 10;
            Vector2D polar = new Vector2D(Vector2D.polar(theta, radius));
            xPoints[j] = (int) polar.x;
            yPoints[j] = (int) polar.y;
        }

            newPosition = new Vector2D(position.x + xPoints[i], position.y + yPoints[i]);
            newVelocity = new Vector2D(velocity).addScaled(new Vector2D(xPoints[i], yPoints[i]), 3).normalise();
            newVelocity.mult((Math.random() * MAX_SPEED / (newSize) + Math.random()));
            game.spawningObjects.add(new Asteroid(game, newPosition, newVelocity, newSize));
        }
    }


    @Override
    public void hit(GameObject other) {
        if (other instanceof Asteroid){
            double collisionDamage = new Vector2D(velocity).subtract(other.velocity).mag() / 100;
            life -= collisionDamage;
        }
        if (other instanceof Ship) {
            double collisionDamage = new Vector2D(velocity).subtract(other.velocity).mag() / 100;
            life -= collisionDamage;
        }
        if (other instanceof Bullet) {
            life -= 30;
        }

        if (life < 0) {
            life = 0;
            if (contents != null) {
                game.spawningObjects.add(new LootCrate(position, contents.id));
            }
            if (size > 1) {
                breakAsteroid();
            }
            isAlive = false;
        }
    }


    @Override
    public void draw(Graphics2D g) {
        g.translate(position.x, position.y);

        AffineTransform at = g.getTransform();
        double rot = (direction.angle() + Math.PI / 2);
        g.rotate(rot);

        g.setColor(COLOR);
        g.fillPolygon(XP, YP, XP.length);
        g.setColor(COLORB);
        g.drawPolygon(XP, YP, XP.length);

        g.rotate(-rot);
        g.setTransform(at);

        g.translate(-position.x, -position.y);
    }
}
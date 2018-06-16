package game;

import utilities.Vector2D;

import java.awt.*;

/**
 * Created by Alex on 18-Mar-17.
 */
public class Weapon {
    private Ship owner;
    private Hardpoint hardpoint;
    private Vector2D direction, position;
    private int range, initialVelocity, barrelLength;
    private int fireTimerMax, fireTimer;

    public static final int BULLET_SPEED = 600;


    public Weapon(Hardpoint hardpoint, int range, int initialVelocity, Ship owner){
        this.hardpoint = hardpoint;
        this.position = new Vector2D(hardpoint.position);
        this.direction = new Vector2D(0, -1);
        this.range = range;
        this.initialVelocity = initialVelocity;
        this.owner = owner;
        this.barrelLength= 2;
        this.fireTimerMax = 200;
        this.fireTimer = fireTimerMax;
    }


    //Decreases the fire timer if it's above 0.
    public void update(){
        this.direction = new Vector2D(owner.direction);
        this.position = new Vector2D(hardpoint.position);
        if(fireTimer > 0) fireTimer -= Constants.DELAY;
    }


    //Fires when the timer is off cooldown.
    public void fire(){
        if (fireTimer <= 0) {
            fireTimer = fireTimerMax;
            makeBullet();
        }
    }


    public void draw(Graphics g){
        g.setColor(new Color(31, 31, 31));
        g.drawOval(-1, -1, 2, 2);
        g.drawLine(0, 0, 0,-barrelLength);
    }


    //Creates a bullet and adds it to the spawningObjects arrayList.
    public void makeBullet() {
        Bullet bullet = new Bullet(new Vector2D(hardpoint.position), new Vector2D(owner.velocity), owner.game.currentZone.size);
        bullet.position.add(new Vector2D(this.direction).mult(barrelLength*owner.SCALE + bullet.radius));
        bullet.velocity.addScaled(this.direction, BULLET_SPEED);
        owner.game.spawningObjects.add(bullet);
    }
}

package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;

import utilities.Vector2D;

public class Ship extends GameObject {

    private static final double STEER_RATE = 1 * Math.PI;
    private static final double ACCELERATION = 3;
    private static final double STRAFE_ACCELERATION = 5;
    private static final double THRUST_DRAG = 0.92;
    private static final double STRAFE_DRAG = 0.92;
    private static final double VELOCITY_DRAG = 0.9;
    public static final int MAX_SPEED = 60;
    private static final int STRAFE_SPEED = 20;
    private Vector2D thrust, leftStrafe, rightStrafe, reverse;
    private double currentSpeed;
    public Vector2D direction;
    public double targetSpeed;
    public int MAX_SHIELD, MAX_HULL;
    public double lastHit,hull, shield, SHIELD_RECHARGE_SPEED, SHIELD_DELAY;
    private boolean shieldDelay, shieldRecharging;
    public HashMap<String, Integer> inventory;
    private Controller ctrl;
    public int lives;

    // Everything needed for drawing
    public static final Color COLOR = new Color(114, 1, 1);
    public static final Color COLOR_THRUST = new Color(216, 2, 2);
    private int[] YP = {4, 7, 6, 1, -7, -5, -5, -7, 1, 6, 7};
    private int[] XP = {0, 9, 10, 10, 2, 2, -2, -2, -10, -10, -9};
    public int SCALE = 3;
    private final int HARPOINTS = 2;
    private ArrayList<Hardpoint> hardpoints = new ArrayList<Hardpoint>(HARPOINTS);


    public Ship(Game game, Vector2D position, Controller ctrl) {
        super(new Vector2D(position), new Vector2D(), 4, 0.6);
        this.lives = 0;
        this.radius *= SCALE;
        this.game = game;
        this.ctrl = ctrl;
        direction = new Vector2D(0, -1);
        thrust = new Vector2D();
        leftStrafe = new Vector2D();
        rightStrafe = new Vector2D();
        reverse = new Vector2D();
        targetSpeed = 0;
        currentSpeed = targetSpeed;

        MAX_SHIELD = 100;
        shield = MAX_SHIELD;
        MAX_HULL = 50;
        hull = MAX_HULL;
        lastHit = 0;
        SHIELD_RECHARGE_SPEED = (Constants.DT * 50);
        SHIELD_DELAY = 4;
        shieldDelay = false;
        shieldRecharging = false;

        Hardpoint leftHardpoint = new Hardpoint(this, new Vector2D(-3, -5), 1);
        Hardpoint rightHardpoint = new Hardpoint(this, new Vector2D(3, -5), 1);
        leftHardpoint.changeWeapon(new Weapon(leftHardpoint, 50, 5, this));
        rightHardpoint.changeWeapon(new Weapon(rightHardpoint, 50, 5, this));
        hardpoints.add(leftHardpoint);
        hardpoints.add(rightHardpoint);
        inventory = new HashMap<String, Integer>();
        physicsOn = true;

    }


    @Override
    public void update() {
        super.update();
        Action action = ctrl.action();

        //Calculate direction by rotating based on action.turn, steer_rate and DT.
        direction = direction.rotate(action.turn * STEER_RATE * Constants.DT);

        //Try to reach the targetSpeed
        targetSpeed += action.thrust * 1;
        if (targetSpeed < 0) targetSpeed = 0;
        if (targetSpeed > MAX_SPEED) targetSpeed = MAX_SPEED;
        if (action.stop) targetSpeed = 0;
        currentSpeed = thrust.mag();

        //THRUST
        if(currentSpeed < targetSpeed){
            thrust.addScaled(direction, ACCELERATION);
            if(thrust.mag() > MAX_SPEED)
                thrust.normalise().mult(MAX_SPEED);
        }
        else
            thrust.mult(THRUST_DRAG);


        //STRAFE
        if(action.strafe == -1) {
            rightStrafe.mult(STRAFE_DRAG);
            leftStrafe.addScaled(new Vector2D(direction).rotate(-Math.PI / 2), STRAFE_ACCELERATION);
            if (leftStrafe.mag() > STRAFE_SPEED)
                leftStrafe.normalise().mult(STRAFE_SPEED);
        }
        else if(action.strafe == 1){
            leftStrafe.mult(STRAFE_DRAG);
            rightStrafe.addScaled(new Vector2D(direction).rotate(Math.PI / 2), STRAFE_ACCELERATION);
            if (rightStrafe.mag() > STRAFE_SPEED)
                rightStrafe.normalise().mult(STRAFE_SPEED);
        }
        else {
            leftStrafe.mult(STRAFE_DRAG);
            rightStrafe.mult(STRAFE_DRAG);
        }

        //TOTAL VELOCITY
        velocity.mult(VELOCITY_DRAG);
        velocity.add(thrust);
        velocity.add(leftStrafe);
        velocity.add(rightStrafe);

        //SPEED CONTROL
        if(targetSpeed > 0)
            game.soundManager.startThrust();
        else
            game.soundManager.stopThrust();


        //UPDATE WEAPONS
        for(Hardpoint h : hardpoints) {
            h.update();
            if (h.weapon != null) {
                h.weapon.update();
                if(action.shoot) {
                    h.weapon.fire();
                }
            }
        }

        //UPDATE SHIELDS
        if(shieldDelay){
            lastHit += Constants.DT;
            if(lastHit >= SHIELD_DELAY){
                shieldDelay = false;
                shieldRecharging = true;
            }
        }
        if(shieldRecharging){
            shield += SHIELD_RECHARGE_SPEED;
            if(shield > MAX_SHIELD){
                shield = MAX_SHIELD;
                shieldRecharging = false;
            }
        }

    }


    public void respawn(){
        isAlive = true;
        shield = MAX_SHIELD;
        hull = MAX_HULL;
        targetSpeed = 0;
        currentSpeed = targetSpeed;
        position = new Vector2D(game.currentZone.size.x / 2, game.currentZone.size.y / 2);
    }


    @Override
    public void hit(GameObject other) {
        //Reduce shielding and hull if an asteroid.
        if (other instanceof Asteroid){
            shieldRecharging = false;
            shieldDelay = true;
            lastHit = 0;
            double collisionDamage = new Vector2D(velocity).subtract(other.velocity).mag() / 10;
            targetSpeed = 0;
            if (shield > 0) {
                shield -= collisionDamage;
                if (shield < 0) shield = 0;
            } else {
                hull -= collisionDamage;
                if (hull < 0) {
                    hull = 0;
                    isAlive = false;
                }
            }
        }
        //Add loot to inventory
        if(other instanceof LootCrate){
            Loot o = ((LootCrate) other).contents;
            if(inventory.get(o.name) != null)
                inventory.put(o.name, inventory.get(o.name) + 1);
            else
                inventory.put(o.name, 1);
        }
    }


    @Override
    public void draw(Graphics2D g) {
        Action action = ctrl.action();
        g.translate(position.x, position.y);
        AffineTransform at = g.getTransform();

        double rot = (direction.angle() + Math.PI / 2);
        g.rotate(rot);

        g.scale(SCALE, SCALE);

        for(Hardpoint h : hardpoints){
            h.draw(g);
        }

        if (action.thrust != 0 || action.strafe != 0) g.setColor(COLOR_THRUST);
        else g.setColor(COLOR);
        g.fillPolygon(XP, YP, XP.length);


        g.setColor(Color.cyan);
        g.drawOval((int)(-radius), (int)(-radius), (int)(2 * radius), (int)(2 * radius));
        g.setTransform(at);

        g.translate(-position.x, -position.y);
    }
}
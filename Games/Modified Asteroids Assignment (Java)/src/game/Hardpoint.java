package game;

import utilities.Vector2D;
import java.awt.*;

public class Hardpoint {
    Vector2D basePosition, position;
    Weapon weapon;
    Ship owner;
    int size;
    double width;

    public Hardpoint(){
        position = new Vector2D();
        size = 1;
        weapon = null;
        width = 0.5;
    }


    public Hardpoint(Ship owner, Vector2D basePosition, int size){
        this.owner = owner;
        this.basePosition = new Vector2D(basePosition);
        this.position = new Vector2D(owner.position).add(new Vector2D(basePosition).mult(owner.SCALE).rotate(owner.direction.angle() + Math.PI / 2));
        this.size = size;
        this.weapon = null;
        this.width = size ;
    }


    public void update(){
        this.position = new Vector2D(owner.position).add(new Vector2D(basePosition).mult(owner.SCALE).rotate(owner.direction.angle() + Math.PI / 2));
    }


    public void changeWeapon(Weapon weapon){
        this.weapon = weapon;
    }


    public void draw(Graphics g){
        Vector2D translate = new Vector2D(basePosition);
        g.translate((int)(translate.x), (int)(translate.y));

        if(weapon != null) weapon.draw(g);

        g.translate((int)(-translate.x), (int)(-translate.y));
    }
}

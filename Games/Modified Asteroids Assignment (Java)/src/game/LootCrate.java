package game;

import utilities.Vector2D;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class LootCrate extends GameObject {

    Loot contents;
    Color color;
    Vector2D direction, size;
    double spin;


    public LootCrate(Vector2D position, int id) {
        super(position, new Vector2D(), 30, 0.4);
        this.velocity = new Vector2D(Math.random() - 0.5, Math.random() - 0.5).normalise();
        this.velocity.mult(Math.random() * 10 + Math.random());
        this.size = new Vector2D(20, 20);

        //Generates random spin for the canister.
        this.direction = new Vector2D(Math.random() - 0.5, Math.random() - 0.5).normalise();
        this.spin = ((Math.PI * Math.random()) - (Math.PI / 2)) * 0.1;

        //Generates random contents for the canister.
        contents = Loot.values()[id];
        this.color = contents.getItemColor();
    }


    @Override
    public void hit(GameObject other) {
        //Crate vanishes because player picked it up.
        if(other instanceof Ship){
            isAlive = false;
        }
    }


    public void update() {
        this.position.addScaled(this.velocity, Constants.DT);
        this.direction.rotate(this.spin * Constants.DT);
    }


    public void draw(Graphics2D g) {
        g.translate(position.x, position.y);

        AffineTransform at = g.getTransform();
        double rot = (direction.angle() + Math.PI / 2);
        g.rotate(rot);
        g.setColor(color);
        g.fillRect((int) (-size.x / 2), (int) (-size.y / 2), (int) (size.x), (int) (size.y));
        g.setColor(new Color(39, 56, 65));
        g.drawRect((int) (-size.x / 2), (int) (-size.y / 2), (int) (size.x), (int) (size.y));
        g.rotate(-rot);
        g.setTransform(at);

        g.translate(-position.x, -position.y);
    }
}

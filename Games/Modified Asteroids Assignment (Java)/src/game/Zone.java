package game;

import utilities.Vector2D;
import java.util.ArrayList;

public class Zone {
    Vector2D size, center, position;
    ArrayList<Zone> connections;
    int type;

    public Zone(){
        this.size = new Vector2D(3000, 3000);
        this.position = new Vector2D();
        this.center = new Vector2D(this.size).mult(0.5);
        this.type = 0;
        connections = new ArrayList<Zone>();

    }

    public Zone(Vector2D size, Vector2D position, int type){
        this.size = new Vector2D(size);
        this.position = new Vector2D(position);
        this.center = new Vector2D(this.size).mult(0.5);
        this.type = type;
        connections = new ArrayList<Zone>();

    }


    public void addConnection(Zone zone){
        connections.add(zone);
    }

}

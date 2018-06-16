package game;

import utilities.Vector2D;
import java.awt.*;
import java.util.ArrayList;

public class World {
    ArrayList<Zone> zones;
    Zone hub;
    Game game;

    //THIS CLASS IS NEVER USED IN THE FINAL GAME SADLY.
    //I RAN OUT OF TIME AND COULDN'T IMPLEMENT IT FULLY.
    public World(Game game){
        this.game = game;
        zones = new ArrayList<Zone>();
        hub = new Zone();
        Zone A = new Zone(new Vector2D(3000, 3000), new Vector2D(57, -57), 1);
        Zone B = new Zone(new Vector2D(3000, 3000), new Vector2D(-86, 12), 1);
        Zone C = new Zone(new Vector2D(3000, 3000), new Vector2D(20, -60), 1);
        Zone D = new Zone(new Vector2D(3000, 3000), new Vector2D(-36, -55), 1);
        Zone E = new Zone(new Vector2D(3000, 3000), new Vector2D(-16, -46), 1);
        Zone F = new Zone(new Vector2D(3000, 3000), new Vector2D(-54, 2), 1);
        Zone G = new Zone(new Vector2D(3000, 3000), new Vector2D(-10, -54), 1);
        Zone H = new Zone(new Vector2D(3000, 3000), new Vector2D(67, 11), 1);
        Zone I = new Zone(new Vector2D(3000, 3000), new Vector2D(54, 14), 1);
        Zone J = new Zone(new Vector2D(3000, 3000), new Vector2D(93, -19), 1);
        Zone K = new Zone(new Vector2D(3000, 3000), new Vector2D(11, 34), 1);
        Zone L = new Zone(new Vector2D(3000, 3000), new Vector2D(0, -7), 1);
        Zone M = new Zone(new Vector2D(3000, 3000), new Vector2D(-61, -19), 1);
        Zone N = new Zone(new Vector2D(3000, 3000), new Vector2D(34, 0), 1);
        Zone O = new Zone(new Vector2D(3000, 3000), new Vector2D(-39, 52), 1);
        Zone P = new Zone(new Vector2D(3000, 3000), new Vector2D(-1, 18), 1);
        Zone Q = new Zone(new Vector2D(3000, 3000), new Vector2D(-11, 41), 1);
        Zone R = new Zone(new Vector2D(3000, 3000), new Vector2D(-18, -7), 1);
        Zone S = new Zone(new Vector2D(3000, 3000), new Vector2D(-94, 19), 1);

        addConnection(hub, L);
        addConnection(hub, N);
        addConnection(hub, P);
        addConnection(hub, R);
        addConnection(R, L);
        addConnection(L, N);
        addConnection(N, P);
        addConnection(P, F);
        addConnection(F, R);
        addConnection(P, K);
        addConnection(P, Q);
        addConnection(Q, O);
        addConnection(N, A);
        addConnection(N, I);
        addConnection(I, H);
        addConnection(N, J);
        addConnection(R, M);
        addConnection(F, B);
        addConnection(B, S);
        addConnection(R, E);
        addConnection(E, D);
        addConnection(E, G);
        addConnection(L, E);
        addConnection(G, C);


        zones.add(hub);
        zones.add(A);
        zones.add(B);
        zones.add(C);
        zones.add(D);
        zones.add(E);
        zones.add(F);
        zones.add(G);
        zones.add(H);
        zones.add(I);
        zones.add(J);
        zones.add(K);
        zones.add(L);
        zones.add(M);
        zones.add(N);
        zones.add(O);
        zones.add(P);
        zones.add(Q);
        zones.add(R);
        zones.add(S);

    }


    public Zone getHub(){
        return hub;
    }


    public void addConnection(Zone a, Zone b){
        a.addConnection(b);
        b.addConnection(a);
    }


    public void draw(Graphics g){
        double multiplier = 5;
        g.setColor(Color.BLACK);
        g.translate((int)multiplier * 100, (int)multiplier * 100);
        g.fillRect(-100 * (int)multiplier, -100 * (int)multiplier, 200 * (int)multiplier, 200 * (int)multiplier);

        //Connections
        g.setColor(new Color(190, 190, 190));
        for(Zone z : zones)
            for(Zone zc : z.connections)
                g.drawLine((int)(z.position.x * multiplier), (int)(z.position.y * multiplier), (int)(zc.position.x * multiplier), (int)(zc.position.y * multiplier));

        //Zones
        for(Zone z : zones)
            g.fillOval((int)(z.position.x * multiplier - 4), (int)(z.position.y * multiplier - 4), (int)(8), (int)(8));

        //Connecting Zones
        Zone currentZone = game.currentZone;
        for(Zone z : currentZone.connections){
            g.setColor(new Color(0, 191,255));
            g.drawLine((int)(currentZone.position.x * multiplier), (int)(currentZone.position.y * multiplier), (int)(z.position.x * multiplier), (int)(z.position.y * multiplier));
            g.fillOval((int)(z.position.x * multiplier - 4), (int)(z.position.y * multiplier - 4), (int)(8), (int)(8));
        }

        //Current Zone
        g.setColor(new Color(255, 135, 0));
        g.fillOval((int)(currentZone.position.x * multiplier - 4), (int)(currentZone.position.y * multiplier - 4), (int)(8), (int)(8));

        g.translate(-(int)multiplier * 100, -(int)multiplier * 100);
    }
}

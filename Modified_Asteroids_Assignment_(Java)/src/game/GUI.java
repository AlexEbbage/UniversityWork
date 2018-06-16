package game;

import utilities.Vector2D;
import java.awt.*;
import java.util.Map;

public class GUI {
    Game game;

    public GUI(Game game){
        this.game = game;
    }


    public void drawMiniMap(Graphics g){
        double radius = 100;
        double scale = 10;
        double dotRadius = 2;
        double dotDiameter = dotRadius * 2;
        Vector2D size = new Vector2D(radius * 2,radius * 2);
        Vector2D border = new Vector2D(50,50);
        Vector2D position = new Vector2D(game.viewSize).subtract(size).subtract(border);
        Vector2D center = new Vector2D(position).addScaled(new Vector2D(size), 0.5);

        g.setColor(new Color(33, 33, 33));
        g.fillOval((int)position.x, (int)position.y, (int)size.x, (int)size.y);
        g.setColor(new Color(255,255,255));
        g.drawOval((int)position.x, (int)position.y, (int)size.x, (int)size.y);

        g.setColor(new Color(255, 255, 255));
        g.fillOval((int)(center.x - dotRadius), (int)(center.y - dotRadius), (int)dotDiameter, (int)dotDiameter);
        Vector2D playerPosition = new Vector2D(game.ship.position);

        g.setColor(new Color(188, 157, 129));
        for(Asteroid a: game.asteroids){
            Vector2D asteroidPosition = new Vector2D(a.position);
            Vector2D direction = new Vector2D(asteroidPosition).subtract(playerPosition).normalise();
            double distance = playerPosition.dist(asteroidPosition)/scale;
            if(distance > radius) distance = radius;
            Vector2D miniMapPosition = direction.mult(distance);
            g.fillOval((int)(center.x + miniMapPosition.x - dotRadius), (int)(center.y + miniMapPosition.y - dotRadius), (int)dotDiameter, (int)dotDiameter);
        }
    }


    public void drawStats(Graphics g){
        //SPEED
        double border = 100;
        Vector2D size = new Vector2D(400,20);
        Vector2D position = new Vector2D(game.viewSize.x / 2 - size.x / 2, game.viewSize.y - size.y / 2 - border);

        g.setColor(new Color(28, 28, 28));
        g.fillRect((int)position.x, (int)position.y, (int)size.x, (int)size.y);

        double speedPercentage = game.ship.targetSpeed / game.ship.MAX_SPEED;
        g.setColor(new Color(236, 184, 0));
        g.fillRect((int)(position.x), (int)(position.y), (int)(size.x * speedPercentage), (int)(size.y));

        int sections = game.ship.MAX_SPEED / 10;
        g.setColor(new Color(221, 221, 221));
        for(int i = 1; i < sections; i++) g.drawLine((int)(position.x + (i * size.x/sections)), (int)(position.y), (int)(position.x + (i * size.x/sections)), (int)(position.y + size.y));

        g.setColor(new Color(255, 255, 255));
        g.drawRect((int)position.x, (int)position.y, (int)size.x, (int)size.y);


        //ARMOUR
        border = 10;
        size = new Vector2D(400,20);
        position.subtract(0, border + size.y);

        g.setColor(new Color(28, 28, 28));
        g.fillRect((int)position.x, (int)position.y, (int)size.x, (int)size.y);

        double hullPercentage = game.ship.hull / game.ship.MAX_HULL;
        g.setColor(new Color(83, 83, 83));
        g.fillRect((int)(position.x), (int)(position.y), (int)(size.x * hullPercentage), (int)(size.y));

        sections = game.ship.MAX_HULL / 10;
        g.setColor(new Color(221, 221, 221));
        for(int i = 1; i < sections; i++) g.drawLine((int)(position.x + (i * size.x/sections)), (int)(position.y), (int)(position.x + (i * size.x/sections)), (int)(position.y + size.y));

        g.setColor(new Color(255, 255, 255));
        g.drawRect((int)position.x, (int)position.y, (int)size.x, (int)size.y);


        //SHIELD
        border = 0;
        size = new Vector2D(400,20);
        position.subtract(0, border + size.y);

        g.setColor(new Color(28, 28, 28));
        g.fillRect((int)position.x, (int)position.y, (int)size.x, (int)size.y);

        double shieldPercentage = game.ship.shield / game.ship.MAX_SHIELD;
        g.setColor(new Color(0, 207, 237));
        g.fillRect((int)(position.x), (int)(position.y), (int)(size.x * shieldPercentage), (int)(size.y));

        sections = game.ship.MAX_SHIELD/10;
        g.setColor(new Color(221, 221, 221));
        for(int i = 1; i < sections; i++) g.drawLine((int)(position.x + (i * size.x/sections)), (int)(position.y), (int)(position.x + (i * size.x/sections)), (int)(position.y + size.y));

        g.setColor(new Color(255, 255, 255));
        g.drawRect((int)position.x, (int)position.y, (int)size.x, (int)size.y);

    }


    public void drawScore(Graphics g){
        double border = 50;
        Vector2D size = new Vector2D(240,90);
        Vector2D position = new Vector2D( border , game.viewSize.y - size.y - 100 );

        g.setColor(new Color(28, 28, 28));
        g.fillRect((int)position.x, (int)position.y, (int)size.x, (int)size.y);
        g.setColor(new Color(255, 255, 255));
        g.drawRect((int)position.x, (int)position.y, (int)size.x, (int)size.y);
        int score = 0;
        for(Map.Entry<String, Integer> entry : game.ship.inventory.entrySet()){
            Loot loot = Loot.valueOf(entry.getKey());
            score += loot.value * entry.getValue();
        }
        Font font = new Font("Arial", Font.PLAIN, 30);
        g.setFont(font);
        g.drawString("Score: " + score, (int)(position.x + 10), (int)(position.y + size.y/2 - 10 ));
        g.drawString("Lives: " + game.ship.lives, (int)(position.x + 10), (int)(position.y + size.y/2 + 30 ));
        if(game.ship.lives == 0){
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0,0,1920, 1080);

            Font title = new Font("Arial", Font.PLAIN, 50);
            g.drawString("GAME OVER", 300, 1080/2 - 400);
            
            if(game.ship.lives == 0) game.isGameOver = true;

        }
    }


    public void draw(Graphics g){
        drawMiniMap(g);
        drawStats(g);
        drawScore(g);

    }
}

package game;

import utilities.Vector2D;

public class Camera {
    Vector2D position, viewSize, zoneSize, minPosition, maxPosition;
    Vector2D margin = new Vector2D(0, 0);

    public Camera(Vector2D position, Vector2D size, Vector2D zoneSize){
        Vector2D halfViewSize = new Vector2D( size ).mult( 0.5 );
        this.position = new Vector2D(position).subtract(halfViewSize);
        this.viewSize = new Vector2D(size);
        this.zoneSize = new Vector2D(zoneSize);
        this.minPosition = new Vector2D().subtract(margin);
        this.maxPosition = new Vector2D(zoneSize).subtract(size).add(margin);
    }

    public void setZoneSize(Vector2D zoneSize){
        this.zoneSize = new Vector2D(zoneSize);
    }

    public void update(Vector2D newPosition){
        Vector2D halfViewSize = new Vector2D( viewSize ).mult( 0.5 );
        position = new Vector2D( newPosition ).subtract( halfViewSize );

        if(position.x > maxPosition.x)
            position.x = maxPosition.x;
        else if(position.x < minPosition.x)
            position.x = minPosition.x;
        if(position.y > maxPosition.y)
            position.y = maxPosition.y;
        else if(position.y < minPosition.y)
            position.y = minPosition.y;
    }
}

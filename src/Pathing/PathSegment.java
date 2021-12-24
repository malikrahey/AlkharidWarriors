package Pathing;

import org.osbot.rs07.api.map.Position;

import static org.osbot.rs07.script.MethodProvider.random;

public class PathSegment {

    private Position center;

    public PathSegment(Position center)
    {
        this.center = center;
    }

    public Position getCenter() {
        return center;
    }

    public Position getPosition()
    {
        int xOffset = random(-1,1);
        int yOffset = random(-1,1);
        Position position = new Position(center.getX() + xOffset, center.getY() + yOffset, center.getZ());
        return position;
    }
}

package Pathing;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import java.util.ArrayList;
import java.util.List;

public class Pather {


    public static final Area ALKHARID_PALACE = new Area(3282, 3177, 3302, 3167);;
    public static final Area GOBLINS_AREA = new Area(3242, 3242, 3257, 3229);
    public static final Area LUMBRIDGE_AREA = new Area(3218, 3222, 3225, 3213);

    public static final Area ALKHARID_AREA = new Area(3268, 3231, 3312, 3180);

    public static final Position[] PATH_TO_GOBLINS = {
            new Position(3226, 3218, 0),
            new Position(3235, 3220, 0),
            new Position(3236, 3225, 0),
            new Position(3253, 3225, 0),
            new Position(3251, 3233, 0)
    };

    public static final Position[] PATH_TO_GATE = {
            new Position(3254, 3232, 0),
            new Position(3265, 3227, 0)
    };

    public static final Position[] PATH_TO_WARRIORS = {
            new Position(3268, 3227, 0),
            new Position(3277, 3224, 0),
            new Position(3284, 3217, 0),
            new Position(3296, 3216, 0),
            new Position(3300, 3207, 0),
            new Position(3298, 3197, 0),
            new Position(3293, 3190, 0),
            new Position(3293, 3183, 0),
            new Position(3292, 3173, 0)
    };

    //Generates a randomized path
    //Each position will be within a 3x3 square of the centre
    public static List<Position> generatePath(Position[] path)
    {
        List<Position> randomPath = new ArrayList<Position>();
        for(Position position : path) {
            PathSegment segment = new PathSegment(position);
            randomPath.add(segment.getPosition());
        }
        return randomPath;
    }

    //Simply converts a position array to a list
    public static List<Position> getPrecisePath(Position[] path){
        List<Position> listPath = new ArrayList<>();
        for(Position position : path) {
            listPath.add(position);
        }
        return listPath;
    }

}

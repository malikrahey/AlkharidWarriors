package Pathing;

import org.osbot.rs07.api.map.Position;

import java.util.ArrayList;
import java.util.List;

public class Pather {

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

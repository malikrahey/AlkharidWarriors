package Tasks;

import Pathing.Pather;
import States.State;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

import java.util.List;

public class WalkToGoblinsTask extends Task{

    private final Position[] PATH_TO_GOBLINS = {
            new Position(3226, 3218, 0),
            new Position(3235, 3220, 0),
            new Position(3236, 3225, 0),
            new Position(3253, 3225, 0),
            new Position(3251, 3233, 0)
    };

    public WalkToGoblinsTask(Script script) {
        super(script);
    }

    @Override
    public State execute() {
        State state = State.WALKING_TO_GOBLINS;
        List<Position> path = Pather.generatePath(PATH_TO_GOBLINS);
        if(script.getWalking().walkPath(path)) {
            state = State.GOBLINS;
        }

        return state;
    }
}

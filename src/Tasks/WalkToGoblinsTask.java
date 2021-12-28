package Tasks;

import Pathing.Pather;
import States.State;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

import java.util.List;

public class WalkToGoblinsTask extends Task{



    public WalkToGoblinsTask(Script script) {
        super(script);
    }

    @Override
    public State execute() {
        State state = State.WALKING_TO_GOBLINS;
        List<Position> path = Pather.getPrecisePath(Pather.PATH_TO_GOBLINS);
        if(script.getWalking().walkPath(path)) {
            state = State.GOBLINS;
        }

        return state;
    }
}

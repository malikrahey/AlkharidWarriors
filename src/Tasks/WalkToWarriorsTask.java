package Tasks;

import Pathing.Pather;
import Sleep.Sleep;
import States.State;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;

import java.util.List;

public class WalkToWarriorsTask extends Task {

    private final Position[] PATH_TO_GATE = {
            new Position(3254, 3232, 0),
            new Position(3265, 3227, 0)
    };
    private final Position[] PATH_TO_WARRIORS = {
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


    public WalkToWarriorsTask(Script script) {
        super(script);
    }

    @Override
    public State execute() {
        State state = State.WALKING_TO_WARRIORS;

        List<Position> pathToGate = Pather.generatePath(PATH_TO_GATE);
        script.getWalking().walkPath(pathToGate);

        for(RS2Object object : script.getObjects().getAll()) {
            if(object.getName().equals("Gate")) {
                if(object.interact("Pay-toll(10gp)")) {
                    break;
                }
            }
        }

        Sleep.sleepUntil(() -> !script.myPlayer().isAnimating(), 3000);
        List<Position> pathToWarriors = Pather.generatePath(PATH_TO_WARRIORS);
        script.getWalking().walkPath(pathToWarriors);
        state = State.ATTACK;
        return state;
    }
}

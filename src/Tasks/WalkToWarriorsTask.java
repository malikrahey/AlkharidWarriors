package Tasks;

import Pathing.Pather;
import Sleep.Sleep;
import States.State;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;

import java.util.List;

public class WalkToWarriorsTask extends Task {


    public WalkToWarriorsTask(Script script) {
        super(script);
    }

    @Override
    public State execute() {
        State state = State.WALKING_TO_WARRIORS;

        List<Position> pathToGate = Pather.getPrecisePath(Pather.PATH_TO_GATE);

            script.getWalking().walkPath(pathToGate);
            for(RS2Object object : script.getObjects().getAll()) {
                if(object.getName().equals("Gate")) {
                    if(object.interact("Pay-toll(10gp)")) {
                        Sleep.sleepUntil(() -> (!script.myPlayer().isAnimating() && !script.myPlayer().isMoving()), 3000);
                        break;
                    }
                }
            }





        Sleep.sleepUntil(() -> !script.myPlayer().isAnimating(), 3000);
        List<Position> pathToWarriors = Pather.getPrecisePath(Pather.PATH_TO_WARRIORS);
        script.getWalking().walkPath(pathToWarriors);
        state = State.ATTACK;
        return state;
    }
}

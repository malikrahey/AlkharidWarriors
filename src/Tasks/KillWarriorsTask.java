package Tasks;

import Pathing.Pather;
import Sleep.Sleep;
import States.State;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;

import java.util.Arrays;
import java.util.List;

public class KillWarriorsTask extends Task{


    private final Area LUMBRIDGE_AREA = new Area(3218, 3222, 3225, 3213);
    private final Area ALKHARID_AREA = new Area(3268, 3231, 3312, 3180);
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


    public KillWarriorsTask(Script script) {
        super(script);
    }

    @Override
    public State execute() {
        State state = State.ATTACK;
        if(LUMBRIDGE_AREA.contains(script.myPosition())) {
            onDeath();
            state = State.WALKING_TO_GOBLINS;
            return state;
        } else if(ALKHARID_AREA.contains(script.myPosition())) {
            List<Position> path = Pather.generatePath(PATH_TO_WARRIORS);
            script.getWalking().walkPath(path);
        }


        if(!script.getCombat().isFighting() && !script.myPlayer().isMoving()) {
            NPC warrior = script.getNpcs().closest(npc -> npc.getName().equals("Al-kharid warrior") && npc.isAttackable() && script.getMap().canReach(npc));
            warrior.interact("Attack");
            Sleep.sleepUntil(() -> !script.getCombat().isFighting() , 60000);
            script.getMouse().moveOutsideScreen();
        }
        return state;
    }


    private void onDeath() {
        Arrays.asList(script.getInventory().getItems()).forEach(item -> {
            List<String> actions = Arrays.asList(item.getActions());
            if(actions.contains("Equip")) {
                item.interact("Equip");
            } else if(actions.contains("Wield")) {
                item.interact("Wield");
            }
        });
    }

}

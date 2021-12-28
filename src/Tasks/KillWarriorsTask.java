package Tasks;

import Pathing.Pather;
import Sleep.Sleep;
import States.State;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;

import java.util.Arrays;
import java.util.List;

public class KillWarriorsTask extends Task{


    public KillWarriorsTask(Script script) {
        super(script);
    }

    @Override
    public State execute() {
        State state = State.ATTACK;
        if(Pather.LUMBRIDGE_AREA.contains(script.myPosition())) {
            onDeath();
            state = State.WALKING_TO_GOBLINS;
            return state;
        } else if(Pather.ALKHARID_AREA.contains(script.myPosition())) {
            List<Position> path = Pather.generatePath(Pather.PATH_TO_WARRIORS);
            script.getWalking().walkPath(path);
        }


        if(!script.getCombat().isFighting() && !script.myPlayer().isMoving()) {
            NPC warrior = script.getNpcs().closest(npc -> npc.getName().equals("Al-Kharid warrior") && npc.isAttackable() && script.getMap().canReach(npc));
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

package Tasks;

import Sleep.Sleep;
import org.osbot.rs07.api.GroundItems;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;
import States.State;

public class KillGoblinsTask extends Task {

    public KillGoblinsTask(Script script)
    {
        super(script);
    }



    @Override
    public State execute() {

        State state = State.GOBLINS;
        long amountOfCoins = script.getInventory().getAmount("Coins");
        if(amountOfCoins >= 10) {
            state = State.WALKING_TO_WARRIORS;
        } else {
            if(!script.getCombat().isFighting() && !script.myPlayer().isMoving()) {

                GroundItems items = script.getGroundItems();
                items.getAll().forEach(item -> {
                    if(item.getName().equals("Coins")) {
                        item.interact("Take");
                        Sleep.sleepUntil(() -> !item.exists() || !script.myPlayer().isAnimating(), 5000);
                    }
                });
                NPC goblin = script.getNpcs().closest(npc -> npc.getName().equals("Goblin") && npc.isAttackable() && !npc.isUnderAttack());
                goblin.interact("Attack");
                script.getMouse().moveOutsideScreen();
                Sleep.sleepUntil(() ->  (!script.getCombat().isFighting() && !script.myPlayer().isMoving()),60000);
            }
        }

        return state;
    }

}

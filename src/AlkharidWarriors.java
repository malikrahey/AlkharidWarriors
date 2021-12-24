import Pathing.Pather;
import Sleep.Sleep;
import States.State;
import Tasks.KillGoblinsTask;
import Tasks.KillWarriorsTask;
import Tasks.WalkToGoblinsTask;
import Tasks.WalkToWarriorsTask;
import org.osbot.rs07.api.Magic;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

@ScriptManifest(name = "PyNN's Alkharid Warriors", author = "PyNN",info="Kills Alkharid Warriors, walks back on death",version = 0.1,logo = "")
public class AlkharidWarriors extends Script {


    private State state;


    private final Area ALKHARID_PALACE = new Area(3282, 3177, 3302, 3167);;
    private final Area GOBLINS_AREA = new Area(3242, 3242, 3257, 3229);
    private final Area LUMBRIDGE_AREA = new Area(3218, 3222, 3225, 3213);


    private final BasicStroke stroke1 = new BasicStroke(1);
    private final Font font1 = new Font("Arial", 0, 13);
    private final Font font2 = new Font("Arial Black", 0, 18);


    private long startTime;

    private int startingAttackLevel;
    private int startingStrengthLevel;
    private int startingDefenceLevel;

    private KillWarriorsTask warriorsTask = new KillWarriorsTask(this);
    private KillGoblinsTask goblinsTask = new KillGoblinsTask(this);
    private WalkToGoblinsTask walkToGoblinsTask = new WalkToGoblinsTask(this);
    private WalkToWarriorsTask walkToWarriorsTask = new WalkToWarriorsTask(this);


    @Override
    public void onStart() throws InterruptedException {
        super.onStart();
        initializeState();

        startingAttackLevel = skills.getStatic(Skill.ATTACK);
        startingStrengthLevel = skills.getStatic(Skill.STRENGTH);
        startingDefenceLevel = skills.getStatic(Skill.DEFENCE);


        startTime = System.currentTimeMillis();
    }

    @Override
    public int onLoop() throws InterruptedException {

        switch(state)
        {
            case ATTACK:
                state = warriorsTask.execute();
                break;
            case WALKING_TO_GOBLINS:
                state = walkToGoblinsTask.execute();
                break;
            case WALKING_TO_WARRIORS:
                state = walkToWarriorsTask.execute();
                break;
            case GOBLINS:
                state = goblinsTask.execute();
                break;
            default:
                initializeState();
                break;
        }

        return random(200,800);
    }


    @Override
    public void onPaint(Graphics2D g) {
        super.onPaint(g);

        int attackLevel = getSkills().getStatic(Skill.ATTACK);
        int strengthLevel = getSkills().getStatic(Skill.STRENGTH);
        int defenceLevel = getSkills().getStatic(Skill.DEFENCE);

        int attackDifference = attackLevel - startingAttackLevel;
        int strengthDifference = strengthLevel - startingStrengthLevel;
        int defenceDifference = defenceLevel - startingDefenceLevel;

        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, 500, 140, 16, 16);
        g.setColor(Color.BLACK);
        g.setStroke(stroke1);
        g.drawRoundRect(0, 0, 500, 140, 16, 16);
        g.setFont(font1);
        g.drawString("Time Running: " + getRunTime(), 50, 50);
        g.drawString("Attack Level: " + String.valueOf(attackLevel) + "(+" + String.valueOf(attackDifference) + ")", 50, 70);
        g.drawString("Strength Level: " + String.valueOf(strengthLevel) + "(+" + String.valueOf(strengthDifference) + ")", 50, 90);
        g.drawString("Defence Level: " + String.valueOf(defenceLevel) + "(+" + String.valueOf(defenceDifference) + ")", 50, 110);
        g.drawString("Status : " + state.toString(), 50, 130);
        g.setFont(font2);

        Point m = getMouse().getPosition();
        g.setStroke(new BasicStroke(10));
        g.drawLine(m.x -5, m.y + 5, m.x + 5, m.y - 5);
        g.drawLine(m.x -5, m.y - 5, m.x + 5, m.y + 5);
    }

    private void initializeState() {

        Position position = myPosition();
        if(LUMBRIDGE_AREA.contains(position)) {
            state = State.WALKING_TO_GOBLINS;
        } else if(ALKHARID_PALACE.contains(position)) {
            state = State.ATTACK;
        } else if(GOBLINS_AREA.contains(position)) {
            state = State.GOBLINS;
        }
        else {
            if(getMagic().getCurrentBook().equals(Magic.Book.NORMAL)) {
                if(getMagic().castSpell(Spells.NormalSpells.HOME_TELEPORT)){
                    state = State.WALKING_TO_GOBLINS;
                } else {
                    getWalking().webWalk(ALKHARID_PALACE);
                    state = State.ATTACK;
                }
            }
        }
    }

    public final String getRunTime(){
        long ms = System.currentTimeMillis() - startTime;
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60; m %= 60; h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

}

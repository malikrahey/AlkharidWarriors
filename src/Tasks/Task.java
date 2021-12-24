package Tasks;


import States.State;
import org.osbot.rs07.script.Script;

public abstract class Task {


    protected Script script;

    public Task(Script script) {
        this.script = script;
    }

    public abstract State execute();

}

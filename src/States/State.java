package States;

public enum State {
    ATTACK,
    WALKING_TO_GOBLINS,
    WALKING_TO_WARRIORS,
    GOBLINS;

    @Override
    public String toString() {
        switch (this) {
            case ATTACK:
                return "Attacking Warriors in Palace";
            case GOBLINS:
                return "Attacking Goblins (10gp for Gate)";
            case WALKING_TO_WARRIORS:
                return "Walking back to Warriors...";
            case WALKING_TO_GOBLINS:
                return "Walking to Goblins...";
            default:
                return "Initializing...";
        }
    }
}

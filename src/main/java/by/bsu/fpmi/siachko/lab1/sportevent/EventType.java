package by.bsu.fpmi.siachko.lab1.sportevent;

public enum EventType {

    GAME, MATCH, RACE;

    @Override
    public String toString() {
        if (this == GAME){
            return "Game";
        }
        else if (this == MATCH){
            return "Match";
        }
        else if (this == RACE){
            return "Race";
        }
        else {
            return "---";
        }
    }
}

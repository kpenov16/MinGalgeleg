package dk.kaloyan.fsm;

public interface HangGameState {
    void start(HangGameFSM fsm);
    void guess(HangGameFSM fsm);
    void back(HangGameFSM fsm);
}

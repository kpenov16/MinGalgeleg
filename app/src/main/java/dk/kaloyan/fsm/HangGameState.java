package dk.kaloyan.fsm;

public interface HangGameState {
    void start(HangGameFSM hangGameFSM);
    void guess(HangGameFSM hangGameFSM);
    void back(HangGameFSM hangGameFSM);
}

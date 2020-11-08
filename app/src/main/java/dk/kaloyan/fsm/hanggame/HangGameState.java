package dk.kaloyan.fsm.hanggame;

import dk.kaloyan.fsm.hanggame.HangGameFSM;

public interface HangGameState {
    void start(HangGameFSM fsm);
    void guess(HangGameFSM fsm);
    void back(HangGameFSM fsm);
}

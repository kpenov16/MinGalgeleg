package dk.kaloyan.core.usecases.startgame;

import dk.kaloyan.fsm.start.StartGameFSM;

public interface StartGameState {
    void startPressed(StartGameFSM fsm);
    void yesName(StartGameFSM fsm);
    void yesCategory(StartGameFSM fsm);
    void noName(StartGameFSM fsm);
    void noCategory(StartGameFSM fsm);
}

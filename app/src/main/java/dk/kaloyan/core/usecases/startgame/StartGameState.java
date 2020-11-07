package dk.kaloyan.core.usecases.startgame;

import dk.kaloyan.fsm.start.StartGameFSM;

public interface StartGameState {
    void startPressed(StartGameFSM fsm);
    void categoryAndNameProvided(StartGameFSM fsm);
    void categoryOrNameRemoved(StartGameFSM fsm);
}

package dk.kaloyan.fsm.hanggame;

import dk.kaloyan.fsm.hanggame.HangGameFSM;
import dk.kaloyan.fsm.hanggame.HangGameState;
import dk.kaloyan.fsm.hanggame.HangGameStateBase;

public class PlayingState extends HangGameStateBase {
    @Override
    public void start(HangGameFSM fsm) {

    }

    @Override
    public void guess(HangGameFSM fsm) {

    }

    @Override
    public void back(HangGameFSM fsm) {

    }

    HangGameState context;
    @Override
    public void setContext(HangGameState context) {
        this.context = context;
    }
}

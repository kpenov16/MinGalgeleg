package dk.kaloyan.fsm.hanggame;

import dk.kaloyan.fsm.hanggame.HangGameFSM;
import dk.kaloyan.fsm.hanggame.HangGameState;
import dk.kaloyan.fsm.hanggame.HangGameStateBase;

public class LoseState extends HangGameStateBase {
    @Override
    public void start(HangGameFSM hangGameFSM) {

    }

    @Override
    public void guess(HangGameFSM hangGameFSM) {

    }

    @Override
    public void back(HangGameFSM hangGameFSM) {

    }

    HangGameState context;
    @Override
    public void setContext(HangGameState context) {
        this.context = context;
    }
}

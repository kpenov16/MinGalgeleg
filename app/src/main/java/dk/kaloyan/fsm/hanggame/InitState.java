package dk.kaloyan.fsm.hanggame;

import dk.kaloyan.fsm.hanggame.HangGameFSM;
import dk.kaloyan.fsm.hanggame.HangGameState;
import dk.kaloyan.fsm.hanggame.HangGameStateBase;

public class InitState extends HangGameStateBase {
    HangGameState context;
    @Override
    public void setContext(HangGameState context) {
        this.context = context;
    }
    @Override
    public void start(HangGameFSM fsm) {
        context.start(fsm);
        fsm.setState(PLAYING);
        fsm.showCurrentGame();
    }

    @Override
    public void guess(HangGameFSM fsm) {

    }

    @Override
    public void back(HangGameFSM fsm) {

    }


}

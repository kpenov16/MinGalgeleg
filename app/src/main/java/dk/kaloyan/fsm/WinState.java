package dk.kaloyan.fsm;

public class WinState extends HangGameStateBase {
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

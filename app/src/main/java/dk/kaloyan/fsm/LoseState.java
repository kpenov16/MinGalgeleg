package dk.kaloyan.fsm;

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

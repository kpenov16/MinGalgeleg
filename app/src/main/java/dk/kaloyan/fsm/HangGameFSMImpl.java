package dk.kaloyan.fsm;

public class HangGameFSMImpl extends HangGameFSM {

    private static HangGameFSM hangGameFSM = new HangGameFSMImpl();
    public static HangGameFSM getInstance(){
        return hangGameFSM;
    }
    private HangGameFSMImpl(){}

    @Override
    public void showCurrentGame() {

    }

    @Override
    public void showLastTopScore() {

    }

    @Override
    public void showYouLose() {

    }

    @Override
    public void showYouWin() {

    }

    @Override
    public void showTopScores() {

    }
}

package dk.kaloyan.fsm;

public abstract class HangGameFSM {
    private HangGameState state;
    public void start() {
        state.start(this);
    }
    public void guess() {
        state.guess(this);
    }
    public void back(){
        state.back(this);
    }
    public void setState(HangGameState state){
        this.state = state;
    }

    //| hang game final state machine transition table          |
    //|=========================================================|
    //| current state | event | new state |      action         |
    //|=========================================================|
    //|    init       | back  |     -     |         -           |
    //|    init       | start | playing   | show current game   |
    //|    playing    | back  | init      | show last top score |
    //|    playing    | guess | playing   | show current game   |
    //|    playing    | guess | win       | show you win        |
    //|    win        | back  | init      | show new top score  |
    //|    playing    | guess | lose      | show you lose       |
    //|    lose       | back  | init      | show last top score |
    //|=========================================================|

    public abstract void showCurrentGame();
    public abstract void showLastTopScore();
    public abstract void showYouLose();
    public abstract void showYouWin();
    public abstract void showTopScores();
}

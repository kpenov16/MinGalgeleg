package dk.kaloyan.fsm;

public abstract class TurnstileFSM {
    private TurnstileState state;
    public void start() {
        state.start(this);
    }
    public void guess() {
        state.guess(this);
    }
    public void back(){
        state.back(this);
    }
    public void setState(TurnstileState state){
        this.state = state;
    }

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

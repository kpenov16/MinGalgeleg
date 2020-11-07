package dk.kaloyan.fsm.start;

import dk.kaloyan.core.usecases.startgame.StartGameState;

public abstract class StartGameFSM {

    private StartGameState state;

    public void setState(StartGameState state) {
        this.state = state;
    }

    public void startPressed(){
        state.startPressed(this);
    }
    public void categoryAndNameProvided(){
        state.categoryAndNameProvided(this);
    }
    public void categoryOrNameRemoved(){
        state.categoryAndNameProvided(this);
    }

    public abstract void DoCategoryAndNameProvided();
    public abstract void DoCategoryAndNameNeeded();
    public abstract void DoCategoryAndNameLoading();
    public abstract void DoGameStarting();


    public abstract static class SimpleStartGameState implements StartGameState{
        public static StartReadyState StartReadyState = new StartReadyState();
        public static StartNeedNameAndCategoryState StartNeedNameAndCategoryState = new StartNeedNameAndCategoryState();
    }
    private static class StartReadyState extends SimpleStartGameState{
        @Override public void startPressed(StartGameFSM fsm) {
            fsm.DoGameStarting();
        }
        @Override public void categoryAndNameProvided(StartGameFSM fsm) {
            //stay in the same state and
            fsm.DoCategoryAndNameProvided();
        }
        @Override public void categoryOrNameRemoved(StartGameFSM fsm) {
            fsm.setState(StartNeedNameAndCategoryState);
            fsm.DoCategoryAndNameNeeded();
        }
    }
    private static class StartNeedNameAndCategoryState extends SimpleStartGameState{
        @Override public void startPressed(StartGameFSM fsm) {
            //cannot be done in this state
        }
        @Override public void categoryAndNameProvided(StartGameFSM fsm) {
            fsm.setState(StartReadyState);
            fsm.DoCategoryAndNameLoading();
        }
        @Override public void categoryOrNameRemoved(StartGameFSM fsm) {
            //do nothing - stay in the same state
        }
    }




}


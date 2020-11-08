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
    public void yesName(){
        state.yesName(this);
    }
    public void noName(){ state.noName(this); }
    public void yesCategory(){
        state.yesCategory(this);
    }
    public void noCategory(){
        state.noCategory(this);
    }


    public abstract void DoNameProvided();
    public abstract void DoNameRemoved();
    public abstract void DoCategoryProvided();
    public abstract void DoCategoryRemoved();
    public abstract void DoGameStarting();
    public abstract void DoEnableStart();
    public abstract void DoDisableStart();

    public abstract static class SimpleStartGameState implements StartGameState{
        public static YesNameYesCategory YesNameYesCategory = new YesNameYesCategory();
        public static NoNameYesCategoryState NoNameYesCategoryState = new NoNameYesCategoryState();
        public static YesNameNoCategoryState YesNameNoCategoryState = new YesNameNoCategoryState();
        public static NoNameNoCategoryState NoNameNoCategoryState = new NoNameNoCategoryState();
    }
    private static class YesNameYesCategory extends SimpleStartGameState{
        @Override public void startPressed(StartGameFSM fsm) {
            fsm.DoDisableStart();
            fsm.DoGameStarting();
        }
        @Override public void yesName(StartGameFSM fsm) {
            //stay in the same state and
            fsm.DoNameProvided();
        }
        @Override public void yesCategory(StartGameFSM fsm) {
            //stay in the same state
            fsm.DoCategoryProvided();
        }
        @Override
        public void noName(StartGameFSM fsm) {
            fsm.setState(NoNameYesCategoryState);
            fsm.DoNameRemoved();
            fsm.DoDisableStart();
        }
        @Override
        public void noCategory(StartGameFSM fsm) {
            fsm.setState(YesNameNoCategoryState);
            fsm.DoCategoryRemoved();
            fsm.DoDisableStart();
        }
    }
    private static class NoNameYesCategoryState extends SimpleStartGameState{
        @Override public void startPressed(StartGameFSM fsm) {
            //cannot be done in this state
        }
        @Override public void yesName(StartGameFSM fsm) {
            fsm.setState(YesNameYesCategory);
            fsm.DoNameProvided();
            fsm.DoEnableStart();
        }
        @Override public void yesCategory(StartGameFSM fsm) {
            //stay in the same state, update the category
            fsm.DoCategoryProvided();
        }
        @Override
        public void noName(StartGameFSM fsm) {
            //do nothing
            fsm.DoNameRemoved();
        }
        @Override
        public void noCategory(StartGameFSM fsm) {
            fsm.setState(NoNameNoCategoryState);
            fsm.DoCategoryRemoved();
        }
    }
    private static class YesNameNoCategoryState extends SimpleStartGameState{
        @Override public void startPressed(StartGameFSM fsm) {
            //cannot be done in this state
        }
        @Override public void yesName(StartGameFSM fsm) {
            //stay in the same state
            fsm.DoNameProvided();
        }
        @Override public void yesCategory(StartGameFSM fsm) {
            fsm.setState(YesNameYesCategory);
            fsm.DoCategoryProvided();
            fsm.DoEnableStart();
        }
        @Override
        public void noName(StartGameFSM fsm) {
            fsm.setState(NoNameNoCategoryState);
            fsm.DoNameRemoved();
        }
        @Override
        public void noCategory(StartGameFSM fsm) {
            //stay in the same state
            fsm.DoCategoryRemoved();
        }
    }
    private static class NoNameNoCategoryState extends SimpleStartGameState{
        @Override public void startPressed(StartGameFSM fsm) {
            //cannot be done in this state
        }
        @Override public void yesName(StartGameFSM fsm) {
            fsm.setState(YesNameNoCategoryState);
            fsm.DoNameProvided();
        }
        @Override public void yesCategory(StartGameFSM fsm) {
            fsm.setState(NoNameYesCategoryState);
            fsm.DoCategoryProvided();
        }
        @Override
        public void noName(StartGameFSM fsm) {
            //stay in the same state
            fsm.DoNameRemoved();
        }
        @Override
        public void noCategory(StartGameFSM fsm) {
            //stay in the same state
            fsm.DoCategoryRemoved();
        }
    }




}


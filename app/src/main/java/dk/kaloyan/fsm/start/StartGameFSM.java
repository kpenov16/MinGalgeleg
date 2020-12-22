package dk.kaloyan.fsm.start;

import dk.kaloyan.core.usecases.startgame.StartGameState;

public abstract class StartGameFSM implements StartGameFSMFacade {

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

/*
    public abstract void DoNameProvided();
    public abstract void DoNameRemoved();
    public abstract void DoCategoryProvided();
    public abstract void DoCategoryRemoved();
    public abstract void DoGameStarting();
    public abstract void DoEnableStart();
    public abstract void DoDisableStart();*/
}


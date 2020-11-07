package dk.kaloyan.fsm.start;

public interface StartGameFSMFacade{
    void DoCategoryAndNameProvided();
    void DoCategoryAndNameNeeded();
    void DoCategoryAndNameLoading();
    void DoGameStarting();

    //void startPressed(StartGameFSM fsm);
    //void categoryAndNameProvided(StartGameFSM fsm);
    //void categoryOrNameRemoved(StartGameFSM fsm);
}


package dk.kaloyan.fsm.start;

public interface StartGameFSMFacade{
    void DoNameProvided();
    void DoNameRemoved();
    void DoCategoryProvided();
    void DoCategoryRemoved();
    void DoGameStarting();
    void DoEnableStart();
    void DoDisableStart();
    //void startPressed(StartGameFSM fsm);
    //void categoryAndNameProvided(StartGameFSM fsm);
    //void categoryOrNameRemoved(StartGameFSM fsm);
}


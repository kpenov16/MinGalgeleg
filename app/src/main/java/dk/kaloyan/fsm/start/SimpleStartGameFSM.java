package dk.kaloyan.fsm.start;

public class SimpleStartGameFSM extends StartGameFSM{
    public StartGameFSMFacade thisFSM;


    @Override
    public void DoCategoryAndNameProvided() {
        thisFSM.DoCategoryAndNameProvided();

    }

    @Override
    public void DoCategoryAndNameNeeded() {
        thisFSM.DoCategoryAndNameNeeded();
    }

    @Override
    public void DoCategoryAndNameLoading() {
        thisFSM.DoCategoryAndNameLoading();
    }

    @Override
    public void DoGameStarting() {
        thisFSM.DoGameStarting();
    }


}

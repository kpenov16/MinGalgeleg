package dk.kaloyan.fsm.start;

public class SimpleStartGameFSM extends StartGameFSM{
    public StartGameFSMFacade thisFSM;
    @Override public void DoNameProvided() {
        thisFSM.DoNameProvided();
    }
    @Override public void DoNameRemoved() {
        thisFSM.DoNameRemoved();
    }
    @Override public void DoCategoryProvided() {
        thisFSM.DoCategoryProvided();
    }
    @Override public void DoCategoryRemoved() {
        thisFSM.DoCategoryRemoved();
    }
    @Override public void DoGameStarting() {
        thisFSM.DoGameStarting();
    }
    @Override public void DoEnableStart() {
        thisFSM.DoEnableStart();
    }
    @Override public void DoDisableStart() {
        thisFSM.DoDisableStart();
    }
}

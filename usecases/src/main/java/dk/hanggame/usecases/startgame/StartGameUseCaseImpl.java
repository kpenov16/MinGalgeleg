package dk.hanggame.usecases.startgame;

public class StartGameUseCaseImpl implements StartInputPort {
    private StartOutputPort outputPort;

    @Override
    public void setup() {
        //do some setup stuff
        outputPort.presentSetup();
    }


    public StartOutputPort getOutputPort() {
        return outputPort;
    }

    public void setOutputPort(StartOutputPort outputPort) {
        this.outputPort = outputPort;
    }

}

package dk.kaloyan.mingalgeleg;

import dk.kaloyan.core.InputPort;

public class InputWorkerImpl {
    private InputPort inputPort;

    public InputWorkerImpl(InputPort inputPort) {
        this.inputPort = inputPort;
    }

    public void setup(String playerName) {
        inputPort.setup(playerName);
    }

    public void play(String guess) {
        inputPort.play(guess);
    }
}

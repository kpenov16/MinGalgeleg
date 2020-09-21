package dk.kaloyan.mingalgeleg;

import dk.kaloyan.core.GameInteractorImpl;
import dk.kaloyan.core.InputPort;

public class InputWorkerImpl {
    private InputPort inputPort;

    public InputWorkerImpl(InputPort inputPort) {
        this.inputPort = inputPort;
    }

    public void setup() {
        inputPort.setup();
    }

    public void play(String guess) {
        inputPort.play(guess);
    }
}

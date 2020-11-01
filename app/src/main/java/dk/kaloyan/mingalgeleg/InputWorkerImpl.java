package dk.kaloyan.mingalgeleg;

import dk.kaloyan.core.usecases.playgame.InputPort;
import dk.kaloyan.entities.Game;
import dk.kaloyan.entities.Player;

public class InputWorkerImpl {
    private InputPort inputPort;

    public InputWorkerImpl(InputPort inputPort) {
        this.inputPort = inputPort;
    }

    public void setup(String nickname) {
        Player player = Player.Builder().withNickname(nickname).build();
        Game game = Game.Builder().withPlayer(player).build();
        inputPort.setup(game);
    }

    public void play(String guess) {
        inputPort.play(guess);
    }
}

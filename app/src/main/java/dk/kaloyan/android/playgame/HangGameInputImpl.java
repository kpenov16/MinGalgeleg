package dk.kaloyan.android.playgame;

import dk.hanggame.usecases.playgame.HangGameInputPort;
import dk.hanggame.entities.Game;
import dk.hanggame.entities.Player;

public class HangGameInputImpl {
    private HangGameInputPort inputPort;

    public HangGameInputImpl(HangGameInputPort inputPort) {
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

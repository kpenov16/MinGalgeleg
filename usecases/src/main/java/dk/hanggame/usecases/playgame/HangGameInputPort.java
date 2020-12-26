package dk.hanggame.usecases.playgame;

import dk.hanggame.entities.Game;

public interface HangGameInputPort {
    void setup(Game game);

    void play(String guess);

}

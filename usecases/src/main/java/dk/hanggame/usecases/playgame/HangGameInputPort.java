package dk.hanggame.usecases.playgame;

import dk.hanggame.usecases.entities.Game;

public interface HangGameInputPort {
    void setup(Game game);

    void play(String guess);


}

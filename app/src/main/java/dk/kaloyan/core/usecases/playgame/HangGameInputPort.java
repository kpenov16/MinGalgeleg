package dk.kaloyan.core.usecases.playgame;

import dk.kaloyan.entities.Game;

public interface HangGameInputPort {
    void setup(Game game);

    void play(String guess);
}

package dk.kaloyan.core;

import dk.kaloyan.entities.Game;

public interface InputPort {
    void setup(Game game);

    void play(String guess);
}

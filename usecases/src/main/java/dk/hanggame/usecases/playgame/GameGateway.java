package dk.hanggame.usecases.playgame;

import java.util.List;

import dk.hanggame.entities.Game;

public interface GameGateway {
    void save(Game game);

    List<Game> getAll(String nickname);
}

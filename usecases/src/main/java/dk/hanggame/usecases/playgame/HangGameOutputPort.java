package dk.hanggame.usecases.playgame;

import java.util.ArrayList;

import dk.hanggame.entities.Game;


public interface HangGameOutputPort {
    void present(Game game);

    void presentWin(Game word);

    void presentLose(Game game);
}

package dk.hanggame.usecases.playgame;

import java.util.ArrayList;

import dk.hanggame.entities.Game;


public interface HangGameOutputPort {
    void present(Game game);

    void presentWin(String word);

    void presentLose(String word, int countWrongLetters, ArrayList<String> usedLetters);
}

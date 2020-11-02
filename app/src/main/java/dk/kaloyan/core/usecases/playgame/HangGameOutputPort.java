package dk.kaloyan.core.usecases.playgame;

import java.util.ArrayList;

import dk.kaloyan.entities.Game;
import dk.kaloyan.entities.Player;

public interface HangGameOutputPort {
    void present(Game game);

    void presentWin(String word);

    void presentLose(String word, int countWrongLetters, ArrayList<String> usedLetters);
}

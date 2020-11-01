package dk.kaloyan.core.usecases.playgame;

import java.util.ArrayList;


public interface HangGameLogicGateway {
    void setup(String wordToGuess);
    void tearDown();
    void guess(String letter);
    String getWordToGuess();
    int getCountWrongLetters();
    ArrayList<String> getUsedLetters();
    boolean isGameLost();
    boolean isGameWon();
}

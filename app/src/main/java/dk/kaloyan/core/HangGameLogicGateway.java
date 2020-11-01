package dk.kaloyan.core;

import java.util.ArrayList;


public interface HangGameLogicGateway {
    void hentOrdFraRegneark(String sværhedsgrader) throws Exception ;

    void setup(String wordToGuess);
    void tearDown();
    void guess(String letter);
    String getWordToGuess();
    int getCountWrongLetters();
    ArrayList<String> getUsedLetters();
    boolean isGameLost();
    boolean isGameWon();
}

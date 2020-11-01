package dk.kaloyan.gateways;

import java.util.ArrayList;
import java.util.HashMap;

import dk.kaloyan.core.HangGameLogicGateway;

public class OneWordHangGameLogicImpl implements HangGameLogicGateway {
    private ArrayList<String> usedLetters = new ArrayList<String>();
    private HashMap<String, String> correct = new HashMap<>();
    private boolean gameIsWon;
    private boolean gameIsLost;
    private String wordToGuess;
    private boolean lastLetterWasCorrect;
    private int countWrongLetters;

    private int maxWrongLetters = 6;
    private long correctCount = 0;

    public OneWordHangGameLogicImpl(){}
    public OneWordHangGameLogicImpl(int maxWrongLetters){
        this.maxWrongLetters = maxWrongLetters;
    }
    //wordsToGuess.get(new Random().nextInt(wordsToGuess.size()))
    public void setup(String wordToGuess){
        this.wordToGuess = wordToGuess;
    }

    public void tearDown(){
        this.wordToGuess = null;
        this.usedLetters.clear();
        this.correct.clear();
    }

    public void guess(String letter) {
        if (letter == null || letter.length() != 1) return;
        if (usedLetters.contains(letter)) return;
        if (gameIsWon || gameIsLost) return;

        usedLetters.add(letter);

        if (wordToGuess.contains(letter)) {
            if(correctCount < wordToGuess.length())
                correctCount = correctCount + wordToGuess.chars().filter(c->c==letter.charAt(0)).count();
            lastLetterWasCorrect = true;
        } else {
            lastLetterWasCorrect = false;
            ++countWrongLetters;
            if (countWrongLetters >= maxWrongLetters) {
                gameIsLost = true;
            }
        }
    }

    public String getWordToGuess() {
        return wordToGuess;
    }
    public void setWordToGuess(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }
    public ArrayList<String> getUsedLetters() {
        return usedLetters;
    }
    public void setUsedLetters(ArrayList<String> usedLetters) {
        this.usedLetters = usedLetters;
    }
    public int getCountWrongLetters() {
        return countWrongLetters;
    }
    public void setCountWrongLetters(int countWrongLetters) {
        this.countWrongLetters = countWrongLetters;
    }



    @Override
    public void hentOrdFraRegneark(String sværhedsgrader) throws Exception {

    }

    public boolean isGameLost() {
        return gameIsLost;
    }

    public boolean isGameWon() {
        System.out.println("wordToGuess: " + wordToGuess);
        gameIsWon = correctCount == wordToGuess.length();
        return gameIsWon;
    }
}

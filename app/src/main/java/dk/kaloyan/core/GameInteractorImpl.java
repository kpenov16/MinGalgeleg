package dk.kaloyan.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dk.kaloyan.entities.Game;

public class GameInteractorImpl implements InputPort{
    private OutputPort outputPort;
    private GalgelogikGateway gameLogicGateway;
    private Game game;
    private WordsGateway wordsGateway;

    class GameLogicImpl implements GalgelogikGateway{
        private ArrayList<String> usedLetters = new ArrayList<String>();
        private boolean gameIsWon;
        private boolean gameIsLost;
        private String wordToGuess;
        private boolean lastLetterWasCorrect;
        private int countWrongLetters;

        private int maxWrongLetters = 6;

        public GameLogicImpl(){}
        public GameLogicImpl(int maxWrongLetters){
            this.maxWrongLetters = maxWrongLetters;
        }
        //wordsToGuess.get(new Random().nextInt(wordsToGuess.size()))
        public void setup(String wordToGuess){
            this.wordToGuess = wordToGuess;
        }

        public void tearDown(){
            this.wordToGuess = null;
            this.usedLetters.clear();
        }

        public void guess(String letter) {
            if (letter == null || letter.length() != 1) return;
            if (usedLetters.contains(letter)) return;
            if (gameIsWon || gameIsLost) return;

            usedLetters.add(letter);

            if (wordToGuess.contains(letter)) {
                lastLetterWasCorrect = true;
            } else {
                lastLetterWasCorrect = false;
                countWrongLetters++;
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
        public ArrayList<String> getBrugteBogstaver() {
            return getUsedLetters();
        }

        @Override
        public String getSynligtOrd() {
            return null;
        }

        @Override
        public String getOrdet() {
            return null;
        }

        @Override
        public int getAntalForkerteBogstaver() {
            return 0;
        }

        @Override
        public boolean erSidsteBogstavKorrekt() {
            return false;
        }

        @Override
        public boolean erSpilletVundet() {
            return false;
        }

        @Override
        public boolean erSpilletTabt() {
            return false;
        }

        @Override
        public boolean erSpilletSlut() {
            return false;
        }

        @Override
        public void nulstil() {

        }

        @Override
        public void gætBogstav(String bogstav) {

        }

        @Override
        public void logStatus() {

        }

        @Override
        public String hentUrl(String url) throws IOException {
            return null;
        }

        @Override
        public void hentOrdFraRegneark(String sværhedsgrader) throws Exception {

        }

        @Override
        public void setWords(List<String> words) {

        }


        public boolean isGameLost() {
            return gameIsLost;
        }

        public boolean isGameWon() {
            return gameIsWon;
        }
    }

    public GameInteractorImpl() { }
    List<String> words = new ArrayList<String>();


    public void setup(Game game){
        this.game = game;

        //
        /*old
        try {
            gameLogicGateway.nulstil();
            gameLogicGateway.setWords( wordsGateway.getWords() );
        } catch (Exception e) {
            e.printStackTrace();
            gameLogicGateway.setWords( new ArrayList<String>(){{add("error");}} );
        }finally {
            //gameLogicGateway.nulstil();
        }

        //old
        gameLogicGateway.logStatus();
        game.setWordToGuess(gameLogicGateway.getOrdet());
        game.setWrongLettersCount(gameLogicGateway.getAntalForkerteBogstaver());
        game.setUsedLetters(gameLogicGateway.getBrugteBogstaver());
        outputPort.presentResult(game);

        */


        //new
        gameLogic = new GameLogicImpl();
        try {
            List<String> wordsToGuess = wordsGateway.getWords();


            gameLogic.setup(
                    wordsToGuess.get(new Random().nextInt(wordsToGuess.size()))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        game.setWordToGuess(gameLogic.getWordToGuess());
        game.setWrongLettersCount(gameLogic.getCountWrongLetters());
        game.setUsedLetters(gameLogic.getUsedLetters());

        outputPort.presentResult(game);
    }
    GameLogicImpl gameLogic;
    public void play(String guess) {
        //new
        gameLogic.guess(guess);
        final String wordToGuess = gameLogic.getWordToGuess();
        final int countWrongLetters = gameLogic.getCountWrongLetters();
        final ArrayList<String> usedLetters = gameLogic.getUsedLetters();
        if (gameLogic.isGameLost() || gameLogic.isGameWon()) {
            if (!gameLogic.isGameLost() )
                outputPort.presentWinGame(wordToGuess);
            else
                outputPort.presentLoseGame(wordToGuess, countWrongLetters, usedLetters);
        }else{
            game.setWrongLettersCount(countWrongLetters);
            game.setUsedLetters(usedLetters);
            outputPort.presentResult(game);
        }
        //old
        /*gameLogicGateway.gætBogstav(guess);
        gameLogicGateway.logStatus();

        System.out.println(gameLogicGateway.getAntalForkerteBogstaver());
        System.out.println(gameLogicGateway.getSynligtOrd());

        if (gameLogicGateway.erSpilletSlut())
            if(!gameLogicGateway.erSpilletTabt())
                outputPort.presentWinGame(gameLogicGateway.getOrdet());
            else
                outputPort.presentLoseGame(gameLogicGateway.getOrdet(), gameLogicGateway.getAntalForkerteBogstaver(), gameLogicGateway.getBrugteBogstaver());
        else{
            game.setWrongLettersCount(gameLogicGateway.getAntalForkerteBogstaver());
            game.setUsedLetters(gameLogicGateway.getBrugteBogstaver());
            outputPort.presentResult(game);
        }*/
    }

    public void setGalgelogikGateway(GalgelogikGateway game) {
        this.gameLogicGateway = game;
    }
    public GalgelogikGateway getGameLogicGateway() {
        return gameLogicGateway;
    }

    public void setWordsGateway(WordsGateway wordsGateway) {
        this.wordsGateway = wordsGateway;
    }
    public OutputPort getOutputPort() {
        return outputPort;
    }
    public void setOutputPort(OutputPort outputPort) {
        this.outputPort = outputPort;
    }

    public void setGameLogicGateway(GalgelogikGateway gameLogicGateway) {
        this.gameLogicGateway = gameLogicGateway;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public WordsGateway getWordsGateway() {
        return wordsGateway;
    }



}

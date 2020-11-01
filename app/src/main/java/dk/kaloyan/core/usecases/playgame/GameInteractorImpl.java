package dk.kaloyan.core.usecases.playgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dk.kaloyan.entities.Game;
import dk.kaloyan.gateways.OneWordHangGameLogicImpl;

public class GameInteractorImpl implements InputPort{
    private OutputPort outputPort;
    private Game game;
    private WordsGateway wordsGateway;
    private HangGameLogicGateway gameLogic;

    public GameInteractorImpl() { }
    List<String> words = new ArrayList<String>();


    public void setup(Game game){
        this.game = game;

        gameLogic = new OneWordHangGameLogicImpl();
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

    public void play(String guess) {
        gameLogic.guess(guess);
        final String wordToGuess = gameLogic.getWordToGuess();
        final int countWrongLetters = gameLogic.getCountWrongLetters();
        final ArrayList<String> usedLetters = gameLogic.getUsedLetters();

        if (gameLogic.isGameLost() || gameLogic.isGameWon()) {
            if (!gameLogic.isGameLost() )
                outputPort.presentWinGame(wordToGuess);
            else
                outputPort.presentLoseGame(wordToGuess, countWrongLetters, usedLetters);

            gameLogic.tearDown();
        }else{
            game.setWrongLettersCount(countWrongLetters);
            game.setUsedLetters(usedLetters);
            outputPort.presentResult(game);
        }
    }

    public void setGalgelogikGateway(HangGameLogicGateway gameLogic) {
        this.gameLogic = gameLogic;
    }
    public HangGameLogicGateway getGameLogicGateway() {
        return gameLogic;
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

    public void setGameLogicGateway(HangGameLogicGateway gameLogic) {
        this.gameLogic = gameLogic;
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

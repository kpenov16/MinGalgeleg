package dk.hanggame.usecases.playgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dk.hanggame.usecases.entities.Game;


public class HangGameInteractorImpl implements HangGameInputPort {
    private HangGameOutputPort outputPort;
    private Game game;
    private WordsGateway wordsGateway;
    private HangGameLogicGateway gameLogic;

    public HangGameInteractorImpl() { }
    List<String> words = new ArrayList<String>();


    public void setup(Game game){
        this.game = game;
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

        outputPort.present(game);
    }

    public void play(String guess) {
        gameLogic.guess(guess);
        final String wordToGuess = gameLogic.getWordToGuess();
        final int countWrongLetters = gameLogic.getCountWrongLetters();
        final ArrayList<String> usedLetters = gameLogic.getUsedLetters();

        if (gameLogic.isGameLost() || gameLogic.isGameWon()) {
            if (!gameLogic.isGameLost() )
                outputPort.presentWin(wordToGuess);
            else
                outputPort.presentLose(wordToGuess, countWrongLetters, usedLetters);

            gameLogic.tearDown();
            //setup(game);
        }else{
            game.setWrongLettersCount(countWrongLetters);
            game.setUsedLetters(usedLetters);
            outputPort.present(game);
        }
    }

    public HangGameLogicGateway getGameLogicGateway() {
        return gameLogic;
    }
    public void setGameLogicGateway(HangGameLogicGateway gameLogic) {
        this.gameLogic = gameLogic;
    }
    public WordsGateway getWordsGateway() {
        return wordsGateway;
    }
    public void setWordsGateway(WordsGateway wordsGateway) {
        this.wordsGateway = wordsGateway;
    }
    public HangGameOutputPort getOutputPort() {
        return outputPort;
    }
    public void setOutputPort(HangGameOutputPort outputPort) {
        this.outputPort = outputPort;
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }




}

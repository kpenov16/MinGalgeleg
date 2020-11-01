package dk.kaloyan.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dk.kaloyan.entities.Game;
import dk.kaloyan.entities.Player;
import dk.kaloyan.entities.Word;

public class GameInteractorImpl implements InputPort{
    private OutputPort outputPort;
    private GalgelogikGateway gameLogicGateway;
    private Game game;
    private WordsGateway wordsGateway;

    public GameInteractorImpl() { }
    List<String> words = new ArrayList<String>();


    public void setup(Game game){
        this.game = game;


        /*
        gameLogicGateway.nulstil();
        try {
            gameLogicGateway.hentOrdFraDr();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //new
        try {
            gameLogicGateway.nulstil();
            gameLogicGateway.setWords( wordsGateway.getWords() );
        } catch (Exception e) {
            e.printStackTrace();
            gameLogicGateway.setWords( new ArrayList<String>(){{add("error");}} );
        }finally {
            gameLogicGateway.nulstil();
        }



        gameLogicGateway.logStatus();
        game.setWordToGuess(gameLogicGateway.getOrdet());
        game.setWrongLettersCount(gameLogicGateway.getAntalForkerteBogstaver());
        game.setUsedLetters(gameLogicGateway.getBrugteBogstaver());
        outputPort.presentResult(game);
    }

    public void play(String guess) {
        gameLogicGateway.gætBogstav(guess);
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
        }
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

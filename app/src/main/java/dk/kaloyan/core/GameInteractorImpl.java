package dk.kaloyan.core;

import dk.kaloyan.entities.Game;
import dk.kaloyan.entities.Player;

public class GameInteractorImpl implements InputPort{
    private OutputPort outputPort;
    public GalgelogikGateway gameLogicGateway;
    private Game game;
    /*public GameInteractorImpl(OutputPort outputPort, GalgelogikGateway game) {
        this.outputPort = outputPort;
        this.game = game;
    }*/

    public GameInteractorImpl() {

    }
    public void setGalgelogikGateway(GalgelogikGateway game) {
        this.gameLogicGateway = game;
    }
    public GalgelogikGateway getGameLogicGateway() {
        return gameLogicGateway;
    }
    public OutputPort getOutputPort() {
        return outputPort;
    }
    public void setOutputPort(OutputPort outputPort) {
        this.outputPort = outputPort;
    }

    public void setup(Game game){
        this.game = game;

        gameLogicGateway.nulstil();

        try {
            gameLogicGateway.hentOrdFraDr();
        } catch (Exception e) {
            e.printStackTrace();
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


}

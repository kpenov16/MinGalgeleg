package dk.kaloyan.core;

import dk.kaloyan.galgeleg.MinGalgelogikImpl;

public class GameInteractorImpl implements InputPort{
    private OutputPort outputPort;
    public GalgelogikGateway game;

    public GameInteractorImpl(OutputPort outputPort, GalgelogikGateway game) {
        this.outputPort = outputPort;
        this.game = game;
    }

    public void setup(){

        game.nulstil();

        try {
            game.hentOrdFraDr();
        } catch (Exception e) {
            e.printStackTrace();
        }

        game.logStatus();

        outputPort.presentSecret(game.getSynligtOrd(), game.getAntalForkerteBogstaver(), game.getBrugteBogstaver());
    }

    public void play(String guess) {
        game.gætBogstav(guess);
        game.logStatus();

        System.out.println(game.getAntalForkerteBogstaver());
        System.out.println(game.getSynligtOrd());

        if (game.erSpilletSlut())
            if(!game.erSpilletTabt())
                outputPort.presentWinGame(game.getOrdet());
            else
                outputPort.presentLoseGame(game.getOrdet(), game.getAntalForkerteBogstaver(), game.getBrugteBogstaver());
        else
            outputPort.presentSecret(game.getSynligtOrd(), game.getAntalForkerteBogstaver(), game.getBrugteBogstaver());
    }
}

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

        outputPort.presentSecret(game.getSynligtOrd());
    }

    public void play(String guess) {
        game.gætBogstav(guess);
        game.logStatus();

        //game.erSidsteBogstavKorrekt();

        System.out.println("" + game.getAntalForkerteBogstaver());
        System.out.println("" + game.getSynligtOrd());
        if (game.erSpilletSlut()) return;
    }
}

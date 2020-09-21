package dk.kaloyan.core;

import dk.kaloyan.galgeleg.MinGalgelogikImpl;

public class GameInteractorImpl {
    public GalgelogikGateway game;

    public GameInteractorImpl(GalgelogikGateway game) {
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
    }

    public void play(String guess) {
        game.gætBogstav(guess);
        game.logStatus();
    }
}

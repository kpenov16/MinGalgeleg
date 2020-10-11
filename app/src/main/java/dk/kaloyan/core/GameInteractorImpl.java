package dk.kaloyan.core;

public class GameInteractorImpl implements InputPort{
    private OutputPort outputPort;
    public GalgelogikGateway game;
    private String playerName;
    public GameInteractorImpl(OutputPort outputPort, GalgelogikGateway game) {
        this.outputPort = outputPort;
        this.game = game;
    }

    public void setup(String playerName){
        this.playerName = playerName;

        game.nulstil();

        try {
            game.hentOrdFraDr();
        } catch (Exception e) {
            e.printStackTrace();
        }

        game.logStatus();

        outputPort.presentResult(this.playerName, game.getSynligtOrd(), game.getAntalForkerteBogstaver(), game.getBrugteBogstaver());
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
            outputPort.presentResult(this.playerName, game.getSynligtOrd(), game.getAntalForkerteBogstaver(), game.getBrugteBogstaver());
    }
}

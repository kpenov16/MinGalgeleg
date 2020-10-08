package dk.kaloyan.core;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import dk.kaloyan.galgeleg.Galgelogik;
import dk.kaloyan.galgeleg.MinGalgelogikImpl;
import dk.kaloyan.mingalgeleg.GameView;
import dk.kaloyan.mingalgeleg.GameViewModel;
import dk.kaloyan.mingalgeleg.OutputWorkerImpl;

import static org.junit.Assert.assertEquals;

public class GameInteractorImplUnitTest implements GameView {

    private GalgelogikGateway game;
    private OutputPort outputPort;

    @Test
    public void nothing(){
        setup();
        MinGalgelogikImpl gameImpl = new MinGalgelogikImpl(new Galgelogik());
        GameInteractorImpl actor = new GameInteractorImpl( new OutputWorkerImpl(this, new GameViewModel()), gameImpl);//new GameInteractorImpl(outputPort, game);
        actor.setup();

        assertEquals(gameImpl.getOrdet(),"");

    }

    public void setup(){
        outputPort = new OutputPort() {
            @Override
            public void presentSecret(String secret) {

            }
        };
        game = new GalgelogikGateway() {
            @Override
            public ArrayList<String> getBrugteBogstaver() {
                return null;
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
            public void hentOrdFraDr() throws Exception {

            }

            @Override
            public void hentOrdFraRegneark(String sværhedsgrader) throws Exception {

            }
        };

    }

    @Override
    public void show(GameViewModel viewModel) {

    }
}

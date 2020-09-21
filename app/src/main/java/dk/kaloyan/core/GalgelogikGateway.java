package dk.kaloyan.core;

import java.io.IOException;
import java.util.ArrayList;

import dk.kaloyan.galgeleg.Galgelogik;
import dk.kaloyan.galgeleg.MinGalgelogikImpl;


public interface GalgelogikGateway {
    ArrayList<String> getBrugteBogstaver();
    String getSynligtOrd();
    String getOrdet();
    int getAntalForkerteBogstaver();
    boolean erSidsteBogstavKorrekt();
    boolean erSpilletVundet();
    boolean erSpilletTabt();
    boolean erSpilletSlut();
    void nulstil();
    void gætBogstav(String bogstav);
    void logStatus();
    String hentUrl(String url) throws IOException;
    void hentOrdFraDr() throws Exception;
    void hentOrdFraRegneark(String sværhedsgrader) throws Exception ;
}

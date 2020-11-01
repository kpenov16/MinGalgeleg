package dk.kaloyan.galgeleg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dk.kaloyan.core.GalgelogikGateway;

public class MinGalgelogikImpl implements GalgelogikGateway {
    private Galgelogik galgelogik;
    public MinGalgelogikImpl(Galgelogik galgelogik){
        this.galgelogik = galgelogik;
    }

    @Override
    public ArrayList<String> getBrugteBogstaver() {
        return galgelogik.getBrugteBogstaver();
    }

    @Override
    public String getSynligtOrd() {
        return galgelogik.getSynligtOrd();
    }

    @Override
    public String getOrdet() {
        return galgelogik.getOrdet();
    }

    @Override
    public int getAntalForkerteBogstaver() {
        return galgelogik.getAntalForkerteBogstaver();
    }

    @Override
    public boolean erSidsteBogstavKorrekt() {
        return galgelogik.erSidsteBogstavKorrekt();
    }

    @Override
    public boolean erSpilletVundet() {
        return galgelogik.erSpilletVundet();
    }

    @Override
    public boolean erSpilletTabt() {
        return galgelogik.erSpilletTabt();
    }

    @Override
    public boolean erSpilletSlut() {
        return galgelogik.erSpilletSlut();
    }

    @Override
    public void nulstil() {
        galgelogik.nulstil();
    }

    @Override
    public void gætBogstav(String bogstav) {
        galgelogik.gætBogstav(bogstav);
    }

    @Override
    public void logStatus() {
        galgelogik.logStatus();
    }

    @Override
    public String hentUrl(String url) throws IOException {
        return Galgelogik.hentUrl(url);
    }



    @Override
    public void hentOrdFraRegneark(String sværhedsgrader) throws Exception {
        galgelogik.hentOrdFraRegneark(sværhedsgrader);
    }

    @Override
    public void setWords(List<String> words) {
        galgelogik.setWords((ArrayList<String>) words);
    }
}

package dk.kaloyan.core;

import java.util.ArrayList;

public interface OutputPort {

    void presentSecret(String synligtOrd, int antalForkerteBogstaver, ArrayList<String> brugteBogstaver);

    void presentWinGame(String ordet);

    void presentLoseGame(String ordet, int antalForkerteBogstaver, ArrayList<String> brugteBogstaver);
}

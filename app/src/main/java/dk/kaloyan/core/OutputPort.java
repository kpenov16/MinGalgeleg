package dk.kaloyan.core;

import java.util.ArrayList;

import dk.kaloyan.entities.Game;
import dk.kaloyan.entities.Player;

public interface OutputPort {
    void presentResult(Game game);

    void presentWinGame(String ordet);

    void presentLoseGame(String ordet, int antalForkerteBogstaver, ArrayList<String> brugteBogstaver);
}

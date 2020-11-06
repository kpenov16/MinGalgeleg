package dk.kaloyan.android.startgame;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import dk.kaloyan.android.ViewablePlayer;
import dk.kaloyan.fsm.HangGameFSM;
import dk.kaloyan.fsm.HangGameFSMImpl;

public class StartViewModel extends ViewModel {
    public static String PLAYER_NAME = "dk.kaloyan.mingalgeleg.StartViewModel.PLAYER_NAME";
    public static String SCORES = "dk.kaloyan.mingalgeleg.StartViewModel.SCORES";
    public static String VIEWABLE_PLAYERS = "dk.kaloyan.mingalgeleg.StartViewModel.VIEWABLE_PLAYERS";

    public boolean isNewlyCreated = true;
    private String playerName = "";
    public String[] scores = new String[]{};
    public HangGameFSM fsm = HangGameFSMImpl.getInstance();
    public ArrayList<ViewablePlayer> viewablePlayers = new ArrayList<>();
    private boolean isWordSourceChosen = false;
    public String chooseWordSourceMessage = "";
    public String[] wordSources = new String[]{"none"};

    public void restoreState(Bundle savedState) {
        playerName = savedState.getString(PLAYER_NAME);
        scores = savedState.getStringArray(SCORES);
        viewablePlayers = savedState.getParcelableArrayList(VIEWABLE_PLAYERS);
    }

    public void saveState(Bundle outState) {
        outState.putString(PLAYER_NAME, playerName);
        outState.putStringArray(SCORES, scores);
        outState.putParcelableArrayList(VIEWABLE_PLAYERS, viewablePlayers);
    }


    public void setWordSourceChosen(boolean wordSourceChosen) {
        isWordSourceChosen = wordSourceChosen;
        notifyUserCanStartGameSubscribers();
    }

    interface UserCanStartGameListener {
        void userCanStartGameHasChanged(boolean canStart);
    }

    private void notifyUserCanStartGameSubscribers(){
        final boolean canStart = gameCanStart();
        userCanStartGameListeners.stream().forEach(l->l.userCanStartGameHasChanged(canStart));
    }

    private boolean gameCanStart() {
        return isWordSourceChosen && (playerName != null && !playerName.isEmpty());
    }

    private List<UserCanStartGameListener> userCanStartGameListeners = new ArrayList<>();

    public void subscribeToUserCanStartGameHasChanged(UserCanStartGameListener listener){
        userCanStartGameListeners.add(listener);
    }
    public void unsubscribeFromUserCanStartGameHasChanged(UserCanStartGameListener listener){
        userCanStartGameListeners.remove(listener);
    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        notifyUserCanStartGameSubscribers();
    }
}

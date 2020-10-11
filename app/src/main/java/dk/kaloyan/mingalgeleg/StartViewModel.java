package dk.kaloyan.mingalgeleg;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class StartViewModel extends ViewModel {
    public static String PLAYER_NAME = "dk.kaloyan.mingalgeleg.StartViewModel.PLAYER_NAME";
    public static String SCORES = "dk.kaloyan.mingalgeleg.StartViewModel.SCORES";

    public boolean isNewlyCreated = true;
    public String playerName;
    public String[] scores = new String[]{};

    public void restoreState(Bundle savedState) {
        playerName = savedState.getString(PLAYER_NAME);
        scores = savedState.getStringArray(SCORES);
        ;
    }

    public void saveState(Bundle outState) {
        outState.putString(PLAYER_NAME, playerName);
        outState.putStringArray(SCORES, scores);
    }
}

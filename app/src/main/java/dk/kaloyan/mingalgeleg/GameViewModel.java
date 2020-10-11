package dk.kaloyan.mingalgeleg;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {
    public String playerName;
    public String currentGuess;
    public int wrongCount;

    public static final String KEY_CURRENT_GUESS = "dk.kaloyan.mingalgeleg.CURRENT_GUESS";
    public static final String KEY_WRONG_COUNT = "dk.kaloyan.mingalgeleg.WRONG_COUNT";

    public void saveState(Bundle outState) {
        outState.putString(KEY_CURRENT_GUESS, currentGuess);
        outState.putInt(KEY_WRONG_COUNT, wrongCount);
    }

    public void restoreState(Bundle inState) {
        currentGuess = inState.getString(KEY_CURRENT_GUESS);
        wrongCount = inState.getInt(KEY_WRONG_COUNT);

    }
}

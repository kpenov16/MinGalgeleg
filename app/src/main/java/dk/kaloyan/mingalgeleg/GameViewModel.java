package dk.kaloyan.mingalgeleg;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {
    public boolean firstGuess = true;
    public String currentGuess;


    public static final String KEY_CURRENT_GUESS = "dk.kaloyan.mingalgeleg.CURRENT_GUESS";
    public static final String KEY_FIRST_GUESS = "dk.kaloyan.mingalgeleg.FIRST_GUESS";
    public boolean isNewlyCreated = true;

    public void saveState(Bundle outState) {
        outState.putString(KEY_CURRENT_GUESS, currentGuess);
        outState.putBoolean(KEY_FIRST_GUESS, firstGuess);
    }

    public void restoreState(Bundle inState) {
        currentGuess = inState.getString(KEY_CURRENT_GUESS);
        firstGuess = inState.getBoolean(KEY_FIRST_GUESS);

    }
}

package dk.kaloyan.gateways;

import android.util.Log;


import java.util.List;

import dk.kaloyan.async.LongRunningTask;
import dk.kaloyan.async.TaskRunner;
import dk.kaloyan.core.usecases.playgame.WordsGateway;

public class GuessWordGateway implements WordsGateway {
    @Override
    public void getRandomWords(int numberOfWords, Consumable consumable) {
        try {
            new TaskRunner().executeAsync(
                    new LongRunningTask("https://kpv-events.herokuapp.com/guesswords/rand/" + numberOfWords),
                    words -> {
                        Log.i("StartActivity::executeAsync: ", "Word: " + words.toString());
                        consumable.consume(words);
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getWords() {
        return null;
    }
}

package dk.kaloyan.gateways;

import android.util.Log;


import java.util.List;

import dk.kaloyan.async.LongRunningTask;
import dk.kaloyan.async.TaskRunner;
import dk.kaloyan.core.usecases.playgame.WordsGateway;

public class HerokuWordsGatewayImpl implements WordsGateway {

    @Override
    public void getRandomWords(int numberOfWords, Consumable consumable) {
        //EpicPandaForce showed this example here https://stackoverflow.com/questions/58767733/android-asynctask-api-deprecating-in-android-11-what-are-the-alternatives
        //as an example of couping with that Android AsyncTask API deprecating in Android 11
        //I've extended it a bit with the Consumable to serve my need
        try {
            new TaskRunner().executeAsync(
                    new LongRunningTask("https://kpv-events.herokuapp.com/guesswords/rand/" + numberOfWords), //calling own rest end point at heroku to fetch some words for the game
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

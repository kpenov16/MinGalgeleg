package dk.kaloyan.gateways;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import dk.kaloyan.async.LongRunningTask;
import dk.kaloyan.async.TaskRunner;
import dk.kaloyan.core.WordsGateway;
import dk.kaloyan.entities.Word;

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

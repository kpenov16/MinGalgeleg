package dk.kaloyan.mingalgeleg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dk.kaloyan.async.LongRunningTask;
import dk.kaloyan.async.TaskRunner;
import dk.kaloyan.core.WordsGateway;
import dk.kaloyan.entities.Word;
import dk.kaloyan.gateways.GuessWordGateway;

public class StartActivity extends AppCompatActivity implements View.OnClickListener, WordsGateway.Consumable {//, HangGameState {
    public static final int RESULT_FROM_END_GAME_ACTIVITY = 0;
    public static final String PREF_SCORES = "dk.kaloyan.mingalgeleg.StartActivity.PREF_SCORES";

    private ListView listViewScore;
    private TextView textViewListElement;
    private Button buttonStartGame;
    private EditText editTextPlayerName;
    private StartViewModel viewModelStart;
    private String playerName;
    private List<String> scores = new ArrayList<>();
    private String lastScore;
    //private HangGameFSM fsm = HangGameFSMImpl.getInstance();

    @Override
    public void onClick(View view) {
        if(view.getId() == buttonStartGame.getId()){
            playerName = editTextPlayerName.getText().toString();

            viewModelStart.playerName = playerName;
            viewModelStart.scores = toStringArray(scores);
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            intent.putExtra(MainActivity.PLAYER_NAME, playerName);

            startActivityForResult(intent, StartActivity.RESULT_FROM_END_GAME_ACTIVITY);
            //fsm.start();
        }
    }
    /*
    @Override
    public void start(HangGameFSM fsm) {
        playerName = editTextPlayerName.getText().toString();
        viewModelStart.playerName = playerName;
        viewModelStart.scores = toStringArray(scores);
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        intent.putExtra(MainActivity.PLAYER_NAME, playerName);
        startActivityForResult(intent, StartActivity.RESULT_FROM_END_GAME_ACTIVITY);
    }
    @Override
    public void guess(HangGameFSM fsm) {}
    @Override
    public void back(HangGameFSM fsm) {}
    */
    private String[] toStringArray(List<String> scores) {
        return scores.toArray(new String[scores.size()]);
    }
    private ArrayList<String> toArrayList(String[] scoresArray) {
        return new ArrayList<>( Arrays.asList(scoresArray) );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == StartActivity.RESULT_FROM_END_GAME_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){

               lastScore = intent.getStringExtra(MainActivity.LAST_SCORE);
               playerName = intent.getStringExtra(MainActivity.PLAYER_NAME);

               updateScores();

               updatePreferences();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void updatePreferences() {
         // create a reference to the shared preferences object
        SharedPreferences sharedPreferences;

        // obtain an editor to add data to my SharedPreferences object
        SharedPreferences.Editor editor;

        sharedPreferences = getSharedPreferences(StartActivity.PREF_SCORES, Activity.MODE_PRIVATE);


        // using this instance you can get any value saved.
        //sharedPreferences.getInt("backColor", Color.BLACK); // default value is BLACK set here


        editor = sharedPreferences.edit();
        //edit and commit

        editor.putStringSet("SCORES", new LinkedHashSet<>(new ArrayList<String>(scores)));
        editor.commit(); //very imp.
    }

    private void initialize() {
        listViewScore = findViewById(R.id.listViewScore);
        textViewListElement = findViewById(R.id.textViewListElement);
        buttonStartGame = findViewById(R.id.buttonStartGame);
        editTextPlayerName = findViewById(R.id.editTextPlayerName);



        buttonStartGame.setOnClickListener(this);
    }

    List<Word> words = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();

        new GuessWordGateway().getRandomWords(10, this::consume);

    }

    @Override
    public void consume(List<Word> result) {
        words = result;

        Log.i("on consume: ", result.toString());

        buttonStartGame.setClickable(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        //GuessWordGateway gateway = new GuessWordGateway();
        //Toast.makeText(this, gateway.getRandomWordsAsStr(handler), Toast.LENGTH_LONG).show();

        //fsm
        //HangGameStateBase.INIT.setContext(this);
        //fsm.setState(HangGameStateBase.INIT);
        //fsm

        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        viewModelStart = viewModelProvider.get(StartViewModel.class);

        if(activityStateNeedToBeRestored(savedInstanceState)){
            viewModelStart.restoreState(savedInstanceState);

            playerName = viewModelStart.playerName;
            scores = toArrayList(viewModelStart.scores);
        }else if(activityNeedsSharedPreferences(savedInstanceState)){
            SharedPreferences sharedPreferences = getSharedPreferences(StartActivity.PREF_SCORES, Activity.MODE_PRIVATE);
            Set<String> set = new LinkedHashSet<String>(sharedPreferences.getStringSet("SCORES", new LinkedHashSet<>()));
            scores = new ArrayList<>(set);
        }
        initialize();
        updateScores();
    }

    private void updateScores() {
        if(lastScore != null)
            scores.add(playerName + "\n" + lastScore);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.highscore_list_element, R.id.textViewListElement, toStringArray(scores));
        listViewScore.setAdapter(adapter);
    }

    private boolean activityStateNeedToBeRestored(Bundle savedInstanceState) {
        return viewModelStart.isNewlyCreated && savedInstanceState != null;
    }

    private boolean activityNeedsSharedPreferences(Bundle savedInstanceState) {
        return viewModelStart.isNewlyCreated && savedInstanceState == null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(outState != null){
            viewModelStart.saveState(outState);
        }
    }


}
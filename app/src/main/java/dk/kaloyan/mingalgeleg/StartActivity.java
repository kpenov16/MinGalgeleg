package dk.kaloyan.mingalgeleg;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dk.kaloyan.core.usecases.playgame.WordsGateway;
import dk.kaloyan.entities.Word;
import dk.kaloyan.gateways.GuessWordGateway;
import dk.kaloyan.utils.JsonWorker;

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
    private ViewablePlayer viewablePlayer;
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
               viewablePlayer = intent.getParcelableExtra(ViewablePlayer.VIEWABLE_PLAYER);

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

        Set<String> jsonPlayers = new JsonWorker<ViewablePlayer>().toStringSet(new ArrayList<>(viewablePlayers.values()));

        editor.putStringSet("SCORES", new LinkedHashSet<>(new ArrayList<String>(jsonPlayers)));
        //editor.putStringSet("SCORES", new LinkedHashSet<>(new ArrayList<String>(scores)));
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

            viewablePlayers = new HashMap<>();
            for (String jsonObj : set){
                ViewablePlayer vp = null;
                try {
                    vp = new ObjectMapper().readValue(jsonObj,ViewablePlayer.class);
                    viewablePlayers.put(vp.getNickname(), vp);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

            List<ViewablePlayer> l = new ArrayList<>(viewablePlayers.values());
            Collections.sort(l, (p1,p2)->p1.compareTo(p2));
            Collections.sort(l,Collections.reverseOrder());
            scores = new ArrayList<>();
            for(ViewablePlayer vp : l){
                scores.add(String.format("%s wins: %d losses: %d", vp.getNickname(), vp.getWins(), vp.getLoses()));
            }

            //make them presentable
            //scores = new ArrayList<>(set);
        }
        initialize();
        updateScores();
    }

    private void updateScores() {

        if(lastScore != null) {
            if(viewablePlayers.get(viewablePlayer.getNickname()) != null){
                ViewablePlayer oldPlayer = viewablePlayers.get(viewablePlayer.getNickname());
                oldPlayer.setWins(viewablePlayer.getWins()+oldPlayer.getWins());
                oldPlayer.setLoses(viewablePlayer.getLoses()+oldPlayer.getLoses());
            }else {
                viewablePlayers.put(viewablePlayer.getNickname(), viewablePlayer);
            }

            List<ViewablePlayer> l = new ArrayList<>(viewablePlayers.values());
            Collections.sort(l, (p1,p2)->p1.compareTo(p2));
            Collections.sort(l,Collections.reverseOrder());
            scores = new ArrayList<>();
            for(ViewablePlayer vp : l){
                scores.add(String.format("%s wins: %d losses: %d", vp.getNickname(), vp.getWins(), vp.getLoses()));
            }

            String jsonObj = "default";
            try {
                jsonObj = new ObjectMapper().writeValueAsString(viewablePlayer);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            ViewablePlayer vp = null;
            try {
                vp = new ObjectMapper().readValue(jsonObj,ViewablePlayer.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //System.out.println("ViewablePlayer::" + vp.getNickname() + vp.getWins() + vp.getLoses());
            //System.out.println("jsonObj::" + jsonObj);

            //scores.add(playerName + "\n" + lastScore);
        }
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

    private Map<String, ViewablePlayer> viewablePlayers = new HashMap<>();


}
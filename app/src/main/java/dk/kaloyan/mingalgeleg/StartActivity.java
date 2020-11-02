package dk.kaloyan.mingalgeleg;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import dk.kaloyan.core.usecases.playgame.WordsGateway;
import dk.kaloyan.entities.Word;
import dk.kaloyan.gateways.DRWordsGatewayImpl;
import dk.kaloyan.gateways.GuessWordGateway;
import dk.kaloyan.utils.JsonWorker;

public class StartActivity extends AppCompatActivity implements View.OnClickListener, WordsGateway.Consumable, CompoundButton.OnCheckedChangeListener {//, HangGameState {
    public static final int RESULT_FROM_END_GAME_ACTIVITY = 0;
    public static final String PREF_SCORES = "dk.kaloyan.mingalgeleg.StartActivity.PREF_SCORES";
    public static final String SCORES = "dk.kaloyan.mingalgeleg.StartActivity.SCORES";

    private ListView listViewScore;
    private TextView textViewListElement;
    private Button buttonStartGame;
    private EditText editTextPlayerName;
    private StartViewModel viewModelStart;
    private String playerName;
    private List<String> scores = new ArrayList<>();
    private String lastScore;
    private ViewablePlayer viewablePlayer;
    private Map<String, ViewablePlayer> viewablePlayers = new HashMap<>();
    private CheckBox checkBoxWordsFromDR;
    //private HangGameFSM fsm = HangGameFSMImpl.getInstance();

    @Override
    public void onClick(View view) {
        if(view.getId() == buttonStartGame.getId()){
            playerName = editTextPlayerName.getText().toString();

            viewModelStart.playerName = playerName;
            viewModelStart.scores = toStringArray(scores);
            viewModelStart.viewablePlayers = new ArrayList<>(viewablePlayers.values());

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
        SharedPreferences sharedPreferences;

        SharedPreferences.Editor editor;

        sharedPreferences = getSharedPreferences(StartActivity.PREF_SCORES, Activity.MODE_PRIVATE);

        editor = sharedPreferences.edit();

        Set<String> jsonPlayers = new JsonWorker<ViewablePlayer>().toStringSet(new ArrayList<>(viewablePlayers.values()));

        editor.putStringSet(StartActivity.SCORES, new LinkedHashSet<>(new ArrayList<String>(jsonPlayers)));
        editor.commit();
    }

    private void initialize() {
        listViewScore = findViewById(R.id.listViewScore);
        textViewListElement = findViewById(R.id.textViewListElement);
        buttonStartGame = findViewById(R.id.buttonStartGame);
        editTextPlayerName = findViewById(R.id.editTextPlayerName);
        checkBoxWordsFromDR = findViewById(R.id.checkBoxWordsFromDR);

        checkBoxWordsFromDR.setOnCheckedChangeListener(this);
        editTextPlayerName.addTextChangedListener(getTextWatcherForEditTextPlayerName());
        buttonStartGame.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //I wanted to show a ProcessBar while fetching words from my rest endpoint,
        //as I use the free tier at heroku the dino kills itself after some time and it takes time to wake up again
        //so this is a good case for the process bar I think, I will start work on it after finishing the saving top score as a json story
        //new GuessWordGateway().getRandomWords(10, this::consume);
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

            viewablePlayers = viewModelStart.viewablePlayers.stream().collect(Collectors.toMap(ViewablePlayer::getNickname,vp->vp));
        }else if(activityNeedsSharedPreferences(savedInstanceState)){
            SharedPreferences sharedPreferences = getSharedPreferences(StartActivity.PREF_SCORES, Activity.MODE_PRIVATE);
            Set<String> set = new LinkedHashSet<String>(sharedPreferences.getStringSet(StartActivity.SCORES, new LinkedHashSet<>()));

            List<ViewablePlayer> list = new JsonWorker<ViewablePlayer>().toList(set, ViewablePlayer.class);
            viewablePlayers = list.stream().collect(Collectors.toMap(ViewablePlayer::getNickname,vp->vp));

            List<ViewablePlayer> l = new ArrayList<>(viewablePlayers.values());
            Collections.sort(l, (p1,p2)->p1.compareTo(p2));
            Collections.sort(l,Collections.reverseOrder());
            scores = new ArrayList<>();
            for(ViewablePlayer vp : l){
                scores.add(String.format("%s wins: %d losses: %d", vp.getNickname(), vp.getWins(), vp.getLoses()));
            }
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

    private TextWatcher getTextWatcherForEditTextPlayerName(){
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                buttonStartGame.setEnabled( !editTextPlayerName.getText().toString().trim().isEmpty() );
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(compoundButton.getId() == R.id.checkBoxWordsFromDR){
            if(!isChecked){
                new GuessWordGateway().getRandomWords(10, this::consume);
            }else {
                ((ApplicationMain)getApplication()).gameInteractor.setWordsGateway(new DRWordsGatewayImpl());
            }
        }
    }

    //private List<Word> words = new ArrayList<>();
    @Override
    synchronized public void consume(List<Word> result) {
        //words = result;
        Log.i("on consume: ", result.toString());
        ((ApplicationMain)getApplication()).gameInteractor.setWordsGateway(new WordsGateway() {
            @Override
            public void getRandomWords(int numberOfWords, Consumable consumable) {

            }

            @Override
            public List<String> getWords() throws Exception {
                return result.stream().map(w->w.getVal()).collect(Collectors.toList());
            }
        });
    }
}
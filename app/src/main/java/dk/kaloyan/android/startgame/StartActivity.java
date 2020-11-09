package dk.kaloyan.android.startgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import dk.hanggame.downloaders.ProcessObserver;
import dk.hanggame.downloaders.WordsDownloader;
import dk.hanggame.factories.WordsDownloaderFactory;
import dk.kaloyan.android.playgame.MainActivity;
import dk.kaloyan.android.R;
import dk.kaloyan.android.ViewablePlayer;
import dk.kaloyan.app.ApplicationMain;
import dk.hanggame.usecases.playgame.WordsGateway;
import dk.hanggame.usecases.startgame.StartGameUseCaseImpl;
import dk.hanggame.usecases.startgame.StartInputPort;
import dk.hanggame.usecases.startgame.StartOutputPort;
import dk.kaloyan.core.usecases.startgame.WordSource;
import dk.kaloyan.fsm.start.SimpleStartGameFSM;
import dk.kaloyan.fsm.start.SimpleStartGameState;
import dk.kaloyan.fsm.start.StartGameFSM;
import dk.kaloyan.fsm.start.StartGameFSMFacade;
import dk.kaloyan.gateways.OneWordHangGameLogicImpl;
import dk.kaloyan.utils.JsonWorker;

public class StartActivity extends AppCompatActivity implements View.OnClickListener, StartView, AdapterView.OnItemSelectedListener, StartViewModel.UserCanStartGameListener, StartGameFSMFacade {
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
    private TextView textViewMessage;
    private Spinner spinnerWordsSource;
    private ProgressBar progressBarGetWords;

    public static WordsDownloaderFactory wordsDownloaderFactory;

    @Override
    public void onClick(View view) {
        if(view.getId() == buttonStartGame.getId()){
            simpleStartGameFSM.startPressed();
        }
    }

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
        ((ApplicationMain)getApplication()).gameInteractor.setGameLogicGateway(new OneWordHangGameLogicImpl());
        //simpleStartGameFSM.setState(StartGameFSM.SimpleStartGameState.YesNameYesCategory);
        DoEnableStart();
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


        textViewMessage = findViewById(R.id.textViewMessage);

        spinnerWordsSource = findViewById(R.id.spinnerWordsSource);

        progressBarGetWords = findViewById(R.id.progressBarGetWords);


        editTextPlayerName.addTextChangedListener(getTextWatcherForEditTextPlayerName());
        buttonStartGame.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //start setup use case
    private StartViewModel startViewModel;
    @Override
    public void showChooseWordCategories(StartViewModel newStartViewModel) {
        startViewModel = newStartViewModel;
        startViewModel.subscribeToUserCanStartGameHasChanged(this);


        buttonStartGame.setEnabled(false);
        textViewMessage.setEnabled(true);
        textViewMessage.setText(startViewModel.chooseWordSourceMessage);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(StartActivity.this, android.R.layout.simple_spinner_item, startViewModel.wordCategories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWordsSource.setAdapter(adapter);
        spinnerWordsSource.setOnItemSelectedListener(this);


        
    }

    @Override
    public List<String> getCategories() {
        return wordsDownloaderFactory.getCategories();
    }

    @Override
    public void userCanStartGameHasChanged(boolean canStart) {

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            simpleStartGameFSM.noCategory();
        }else if(position == 1){
            simpleStartGameFSM.yesCategory();
        }else if(position == 2){
            simpleStartGameFSM.yesCategory();
        }else{
            simpleStartGameFSM.noCategory();
            //not known option
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //startViewModel.setWordCategoryChosen(false);
    }


    @Override
    public void DoNameProvided() {
        updatePlayerName(editTextPlayerName.getText().toString());
    }
    @Override
    public void DoNameRemoved() {
        textViewMessage.setVisibility(View.VISIBLE);
        textViewMessage.setText(startViewModel.chooseWordSourceMessage);
        updatePlayerName(editTextPlayerName.getText().toString());
        //buttonStartGame.setEnabled(false);
    }
    private void updatePlayerName(String name){
        this.playerName = editTextPlayerName.getText().toString();
    }

    @Override
    public void DoCategoryProvided() {

    }

    @Override
    public void DoCategoryRemoved() {
        textViewMessage.setVisibility(View.VISIBLE);
        textViewMessage.setText(startViewModel.chooseWordSourceMessage);
    }

    @Override
    public void DoGameStarting() {
        int position = spinnerWordsSource.getSelectedItemPosition();
        if(position == 1){
            WordsDownloader downloader = wordsDownloaderFactory.make(wordsDownloaderFactory.getCategories().get(position-1));
            downloader.addProcessObserver(new ProcessObserver() {
                public void starting() {
                    new Handler(Looper.getMainLooper()).post(()->{
                        progressBarGetWords.setVisibility(View.VISIBLE);
                        progressBarGetWords.setProgress(1);
                    });
                }
                @Override
                public void pending() {
                    new Handler(Looper.getMainLooper()).post(()->{
                        progressBarGetWords.setProgress(3);
                    });
                }
                @Override
                public void processed(ArrayList<String> words) {
                    new Handler(Looper.getMainLooper()).post(()->{
                        System.out.println("words: " + words);
                        ((ApplicationMain)getApplication()).gameInteractor.setWordsGateway(new WordsGateway() {
                            @Override
                            public List<String> getWords(){
                                return words;
                            }
                        });
                        progressBarGetWords.setProgress(4);

                        new Handler().postDelayed(()->{
                            progressBarGetWords.setVisibility(View.GONE);
                            startViewModel.setWordSource(WordSource.DR);
                            startViewModel.setWordCategoryChosen(true);

                            startNextActivity();
                        },500);
                    });
                }
            });
            downloader.execute();
        }else if(position == 2){
            //as I use the free tier at heroku the dino kills itself after some time and it takes time to wake up again
            //so this is a good case for the process bar I think. At this position I get the heroku endpoint
            WordsDownloader downloader = wordsDownloaderFactory.make(wordsDownloaderFactory.getCategories().get(position-1));
            downloader.addProcessObserver(new ProcessObserver() {
                public void starting() {
                    new Handler(Looper.getMainLooper()).post(()->{
                        progressBarGetWords.setVisibility(View.VISIBLE);
                        progressBarGetWords.setProgress(1);
                    });
                }
                @Override
                public void pending() {
                    new Handler(Looper.getMainLooper()).post(()->{
                        progressBarGetWords.setProgress(3);
                    });
                }
                @Override
                public void processed(ArrayList<String> words) {
                    new Handler(Looper.getMainLooper()).post(()->{
                        System.out.println("words: " + words);
                        ((ApplicationMain)getApplication()).gameInteractor.setWordsGateway(new WordsGateway() {
                            @Override
                            public List<String> getWords() {
                                return words;
                            }
                        });
                        progressBarGetWords.setProgress(4);
                        new Handler().postDelayed(()->{
                            progressBarGetWords.setVisibility(View.GONE);

                            startViewModel.setWordSource(WordSource.HEROKU);
                            startViewModel.setWordCategoryChosen(true);

                            startNextActivity();
                        },500);
                    });
                }
            });
            downloader.execute();
        }
    }


    @Override
    public void DoEnableStart() {
        buttonStartGame.setEnabled(true);
        textViewMessage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void DoDisableStart() {
        buttonStartGame.setEnabled(false);
        textViewMessage.setVisibility(View.VISIBLE);
    }

    synchronized private void startNextActivity(){
        playerName = editTextPlayerName.getText().toString();

        viewModelStart.setPlayerName(playerName);
        viewModelStart.scores = toStringArray(scores);
        viewModelStart.viewablePlayers = new ArrayList<>(viewablePlayers.values());

        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        intent.putExtra(MainActivity.PLAYER_NAME, playerName);

        startActivityForResult(intent, StartActivity.RESULT_FROM_END_GAME_ACTIVITY);
    }




    //end setup use case

    SimpleStartGameFSM simpleStartGameFSM = new SimpleStartGameFSM();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        //start setup the start usecase
        initialize();


        simpleStartGameFSM.thisFSM = this;
        simpleStartGameFSM.setState(SimpleStartGameState.NoNameNoCategoryState);


        StartOutputPortImpl startOutputPortImpl = new StartOutputPortImpl();
        startOutputPortImpl.setStartView(this);
        StartOutputPort startOutputPort = startOutputPortImpl;


        StartGameUseCaseImpl startUseCase = new StartGameUseCaseImpl();
        startUseCase.setOutputPort(startOutputPort);

        StartInputPort inputPort = startUseCase;
        inputPort.setup();

        //end //setup the start usecase


        //fsm
        //HangGameStateBase.INIT.setContext(this);
        //fsm.setState(HangGameStateBase.INIT);
        //fsm

        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        viewModelStart = viewModelProvider.get(StartViewModel.class);

        if(activityStateNeedToBeRestored(savedInstanceState)){
            viewModelStart.restoreState(savedInstanceState);

            playerName = viewModelStart.getPlayerName();
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
        //initialize();
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
                final String name = editTextPlayerName.getText().toString().trim();
                if(name != null && !name.isEmpty()){
                    simpleStartGameFSM.yesName();
                }else {
                    simpleStartGameFSM.noName();
                }
                //startViewModel.setPlayerName(editTextPlayerName.getText().toString().trim());
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
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}











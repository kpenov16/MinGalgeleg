package dk.kaloyan.android.startgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import dk.kaloyan.android.playgame.MainActivity;
import dk.kaloyan.android.R;
import dk.kaloyan.android.ViewablePlayer;
import dk.kaloyan.app.ApplicationMain;
import dk.kaloyan.async.ExtractWords;
import dk.kaloyan.core.usecases.playgame.WordsGateway;
import dk.kaloyan.core.usecases.startgame.StartGameUseCaseImpl;
import dk.kaloyan.core.usecases.startgame.StartInputPort;
import dk.kaloyan.core.usecases.startgame.StartOutputPort;
import dk.kaloyan.core.usecases.startgame.WordSource;
import dk.kaloyan.entities.Word;
import dk.kaloyan.gateways.DRWordsGatewayImpl;
import dk.kaloyan.gateways.HerokuWordsGatewayImpl;
import dk.kaloyan.utils.JsonWorker;

public class StartActivity extends AppCompatActivity implements View.OnClickListener, WordsGateway.Consumable, CompoundButton.OnCheckedChangeListener, StartView, AdapterView.OnItemSelectedListener, StartViewModel.UserCanStartGameListener{//, HangGameState {
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
    private TextView textViewMessage;
    private Spinner spinnerWordsSource;
    private ProgressBar progressBarGetWords;
    //private HangGameFSM fsm = HangGameFSMImpl.getInstance();

    @Override
    public void onClick(View view) {
        if(view.getId() == buttonStartGame.getId()){
            playerName = editTextPlayerName.getText().toString();

            viewModelStart.setPlayerName(playerName);
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

        textViewMessage = findViewById(R.id.textViewMessage);
        spinnerWordsSource = findViewById(R.id.spinnerWordsSource);
        progressBarGetWords = findViewById(R.id.progressBarGetWords);

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

    //start setup use case
    private StartViewModel startViewModel;
    @Override
    public void showChooseWordSource(StartViewModel newStartViewModel) {
        startViewModel = newStartViewModel;
        startViewModel.subscribeToUserCanStartGameHasChanged(this);


        buttonStartGame.setEnabled(false);
        textViewMessage.setEnabled(true);
        textViewMessage.setText(startViewModel.chooseWordSourceMessage);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(StartActivity.this, android.R.layout.simple_spinner_item, startViewModel.wordSources);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWordsSource.setAdapter(adapter);
        spinnerWordsSource.setOnItemSelectedListener(this);
        
    }

    @Override
    public void userCanStartGameHasChanged(boolean canStart) {
        if(canStart){ // && startViewModel.isUserNameChosen()
            buttonStartGame.setEnabled(true);
            textViewMessage.setVisibility(View.INVISIBLE);
        }else {
            buttonStartGame.setEnabled(false);
            textViewMessage.setVisibility(View.VISIBLE);
            textViewMessage.setText(startViewModel.chooseWordSourceMessage);
        }
    }








    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            startViewModel.setWordSourceChosen(false);
        }else if(position == 1){
            //new DRWordsGatewayImpl().getWords();
            WordsDownloader downloader = new DRWordsDownloader();
            downloader.addProcessObserver(new ProcessObserver() {
                public void starting() {
                    progressBarGetWords.setVisibility(View.VISIBLE);
                    progressBarGetWords.setProgress(1);
                }
                @Override
                public void pending() {
                    progressBarGetWords.setProgress(3);
                }
                @Override
                public void processed(ArrayList<String> words) {
                    System.out.println("words: " + words);
                    progressBarGetWords.setProgress(4);
                    startViewModel.setWordSource(WordSource.DR);
                    startViewModel.setWordSourceChosen(true);
                    new Handler().postDelayed(()->{
                        progressBarGetWords.setVisibility(View.GONE);
                    },500);
                }
            });
            downloader.execute();

        }else if(position == 2){
            WordsDownloader downloader = new HEROKUWordsDownloader();
            downloader.addProcessObserver(new ProcessObserver() {
                public void starting() {
                    progressBarGetWords.setVisibility(View.VISIBLE);
                    progressBarGetWords.setProgress(1);
                }
                @Override
                public void pending() {
                    progressBarGetWords.setProgress(3);
                }
                @Override
                public void processed(ArrayList<String> words) {
                    System.out.println("words: " + words);
                    progressBarGetWords.setProgress(4);
                    startViewModel.setWordSource(WordSource.HEROKU);
                    startViewModel.setWordSourceChosen(true);
                    new Handler().postDelayed(()->{
                        progressBarGetWords.setVisibility(View.GONE);
                    },500);
                }
            });
            downloader.execute();
        }
        else {
            //not known option
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        startViewModel.setWordSourceChosen(false);
    }
    //end setup use case

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        //start setup the start usecase
        initialize();

        StartOutputPortImpl startOutputPortImpl = new StartOutputPortImpl();
        startOutputPortImpl.setStartView(this);
        StartOutputPort startOutputPort = startOutputPortImpl;


        StartGameUseCaseImpl startUseCase = new StartGameUseCaseImpl();
        startUseCase.setOutputPort(startOutputPort);

        StartInputPort inputPort = startUseCase;
        inputPort.setup();
        //end //setup the start usecase


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
                startViewModel.setPlayerName(editTextPlayerName.getText().toString().trim());
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
                new HerokuWordsGatewayImpl().getRandomWords(10, this::consume);
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
class HEROKUWordsDownloader implements WordsDownloader {
    private  ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private ArrayList<String> words = new ArrayList<String>();

    private List<Word> extractWords(JSONArray jsonArray) {
        List<Word> responseWords = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Iterator<String> keys = json.keys();
            Word word = Word.Builder().build();
            while (keys.hasNext()) {
                String key = keys.next();
                try {
                    //System.out.println("Key :" + key + "  Value :" + json.get(key));
                    if(key.equalsIgnoreCase("id"))
                        word.setId((String) json.get(key));
                    else if(key.equalsIgnoreCase("val"))
                        word.setVal((String) json.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            responseWords.add(word);
        }
        return responseWords;
    }

    @Override
    public void execute() {
        executor.execute( ()->{
            observers.stream().forEach(o-> new Handler(Looper.getMainLooper()).post( ()-> o.starting()));
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL("https://kpv-events.herokuapp.com/guesswords/rand/10").openStream()))) {
                observers.stream().forEach( o-> new Handler(Looper.getMainLooper()).post( ()-> o.pending() ) );
                String str = br.lines().collect(Collectors.joining());
                /*
                StringBuilder sb = new StringBuilder();
                String linje = br.readLine();
                while (linje != null) {
                    sb.append(linje);
                    linje = br.readLine();
                }
                extractWords(new JSONArray(sb.toString()));
                */
                List<String> words = extractWords(new JSONArray(str)).stream().map(w->w.getVal()).collect(Collectors.toList());
                observers.stream().forEach( o-> new Handler(Looper.getMainLooper()).post( ()-> o.processed(new ArrayList<String>(words))) );
            } catch (Exception e) {
                e.printStackTrace();
                new ArrayList<Word>(){{add(Word.Builder().withVal( "activate internet").build()); }};
            }
                            }
        );
        executor.shutdown();
        //executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    List<ProcessObserver> observers = new ArrayList<>();
    @Override
    public void addProcessObserver(ProcessObserver observer){
        observers.add(observer);
    }

    @Override
    public void removeProcessObserver(ProcessObserver observer){
        observers.remove(observer);
    }
}

class DRWordsDownloader implements WordsDownloader{
    private  ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private ArrayList<String> words = new ArrayList<String>();

    private String getPageAsString(String url) throws IOException {
        InputStream inputStream = new URL(url).openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null) {
            sb.append(linje + "\n");
            linje = br.readLine();
        }
        String str = sb.toString();
        inputStream.close();
        return str;
    }

    @Override
    public void execute() {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            for (ProcessObserver o : observers){
                new Handler(Looper.getMainLooper()).post(()->o.starting());
            }
            String data = "bil computer programmering motorvej busrute gangsti skovsnegl solsort nitten";
            try {
                data = getPageAsString("https://dr.dk");

                for (ProcessObserver o : observers){
                    new Handler(Looper.getMainLooper()).post(()->o.pending());
                }

                data = data.substring(data.indexOf("<body")). // fjern headere
                        replaceAll("<.+?>", " ").toLowerCase(). // fjern tags
                        replaceAll("&#198;", "æ"). // erstat HTML-tegn
                        replaceAll("&#230;", "æ"). // erstat HTML-tegn
                        replaceAll("&#216;", "ø"). // erstat HTML-tegn
                        replaceAll("&#248;", "ø"). // erstat HTML-tegn
                        replaceAll("&oslash;", "ø"). // erstat HTML-tegn
                        replaceAll("&#229;", "å"). // erstat HTML-tegn
                        replaceAll("[^a-zæøå]", " "). // fjern tegn der ikke er bogstaver
                        replaceAll(" [a-zæøå] "," "). // fjern 1-bogstavsord
                        replaceAll(" [a-zæøå][a-zæøå] "," "); // fjern 2-bogstavsord

                setWords(new HashSet<String>( Arrays.asList(data.split(" ")).stream().filter(w->w.length()>4).collect(Collectors.toList()) ));
            } catch (IOException e) {
                e.printStackTrace();
                setWords(new HashSet<String>(){{add("activate internet");}});
            }

        });
        executor.shutdown();
        //executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    synchronized private void setWords(HashSet<String> newWords){
        words.clear();
        words.addAll(newWords);
        for (ProcessObserver o : observers){
            new Handler(Looper.getMainLooper()).post(()->o.processed(words));
        }
    }

    List<ProcessObserver> observers = new ArrayList<>();
    @Override
    public void addProcessObserver(ProcessObserver observer){
        observers.add(observer);
    }

    @Override
    public void removeProcessObserver(ProcessObserver observer){
        observers.remove(observer);
    }
}

interface ProcessObserver {
    void starting();
    void pending();
    void processed(ArrayList<String> words);
}
interface WordsDownloader extends ProcessObservable{
    void execute();
}
interface ProcessObservable{
    void addProcessObserver(ProcessObserver observer);
    void removeProcessObserver(ProcessObserver observer);
}

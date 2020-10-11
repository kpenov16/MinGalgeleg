package dk.kaloyan.mingalgeleg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RESULT_FROM_END_GAME_ACTIVITY = 0;

    private ListView listViewScore;
    private TextView textViewListElement;
    private Button buttonStartGame;
    private EditText editTextPlayerName;
    private StartViewModel viewModelStart;
    private String playerName;
    private List<String> scores = new ArrayList<>();
    private String lastScore;

    @Override
    public void onClick(View view) {
        if(view.getId() == buttonStartGame.getId()){
            playerName = editTextPlayerName.getText().toString();

            viewModelStart.playerName = playerName;
            viewModelStart.scores = toStringArray(scores);


            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            intent.putExtra(MainActivity.PLAYER_NAME, playerName);
            startActivityForResult(intent, StartActivity.RESULT_FROM_END_GAME_ACTIVITY);
        }
    }

    private String[] toStringArray(List<String> scores) {
        return scores.toArray(new String[scores.size()]);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == StartActivity.RESULT_FROM_END_GAME_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){

               lastScore = intent.getStringExtra(MainActivity.LAST_SCORE);

               updateScores();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void initialize() {
        listViewScore = findViewById(R.id.listViewScore);
        textViewListElement = findViewById(R.id.textViewListElement);
        buttonStartGame = findViewById(R.id.buttonStartGame);
        editTextPlayerName = findViewById(R.id.editTextPlayerName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        viewModelStart = viewModelProvider.get(StartViewModel.class);

        if(activityStateNeedToBeRestored(savedInstanceState)){
            viewModelStart.restoreState(savedInstanceState);
            playerName = viewModelStart.playerName;
            scores = new ArrayList<>( Arrays.asList(viewModelStart.scores) );
        }

        initialize();

        //String[] scores = new String[]{"kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3"};
        updateScores();

        buttonStartGame.setOnClickListener(this);
    }

    private void updateScores() {
        if(lastScore != null)
            scores.add(lastScore);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.highscore_list_element, R.id.textViewListElement, toStringArray(scores));
        listViewScore.setAdapter(adapter);
    }

    private boolean activityStateNeedToBeRestored(Bundle savedInstanceState) {
        return viewModelStart.isNewlyCreated && savedInstanceState != null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(outState != null){
            viewModelStart.saveState(outState);
        }
    }

}
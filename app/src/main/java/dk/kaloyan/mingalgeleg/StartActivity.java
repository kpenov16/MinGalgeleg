package dk.kaloyan.mingalgeleg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static dk.kaloyan.mingalgeleg.MainActivity.PLAYER_NAME;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listViewScore;
    private TextView textViewListElement;
    private Button buttonStartGame;
    private EditText editTextPlayerName;

    @Override
    public void onClick(View view) {
        if(view.getId() == this.buttonStartGame.getId()){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(PLAYER_NAME, this.editTextPlayerName.getText().toString());
            startActivity(intent);
        }
    }
    private void init() {
        listViewScore = findViewById(R.id.listViewScore);
        textViewListElement = findViewById(R.id.textViewListElement);
        buttonStartGame = findViewById(R.id.buttonStartGame);
        editTextPlayerName = findViewById(R.id.editTextPlayerName);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        init();


        //String[] scores = new String[]{"kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3"};
        String[] scores = new String[]{};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.highscore_list_element, R.id.textViewListElement, scores);
        listViewScore.setAdapter(adapter);

        buttonStartGame.setOnClickListener(this);

    }



}
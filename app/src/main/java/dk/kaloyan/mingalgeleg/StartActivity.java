package dk.kaloyan.mingalgeleg;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class StartActivity extends AppCompatActivity {

    private ListView listViewScore;
    private TextView textViewListElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        init();


        String[] scores = new String[]{"kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3","kaloyan: score 5","bob: score 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.highscore_list_element, R.id.textViewListElement, scores);
        listViewScore.setAdapter(adapter);

    }

    private void init() {
        listViewScore = findViewById(R.id.listViewScore);
        textViewListElement = findViewById(R.id.textViewListElement);


    }
}
package dk.kaloyan.android.showscoredetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import dk.kaloyan.android.R;
import dk.kaloyan.android.playgame.MainActivity;

public class ScoreDetailsActivity extends AppCompatActivity {

    public static final String NICKNAME = "dk.kaloyan.android.showscoredetails.ScoreDetailsActivity.NICKNAME";
    private RecyclerView recyclerViewScoreDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_details);

        initialize();

        Bundle bundleFromStartActivity = getIntent().getExtras();
        String nickname = bundleFromStartActivity.getString(NICKNAME);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewScoreDetails.setLayoutManager(linearLayoutManager);
    }

    private void initialize() {
        recyclerViewScoreDetails = findViewById(R.id.recyclerViewScoreDetails);

    }
}
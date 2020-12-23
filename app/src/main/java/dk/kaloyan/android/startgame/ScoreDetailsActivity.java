package dk.kaloyan.android.startgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import dk.kaloyan.android.R;
import dk.kaloyan.android.playgame.MainActivity;

public class ScoreDetailsActivity extends AppCompatActivity {

    public static final String NICKNAME = "dk.kaloyan.android.startgame.ScoreDetailsActivity.NICKNAME";
    private TextView textViewNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_details);

        initialize();

        Bundle bundleFromStartActivity = getIntent().getExtras();
        String nickname = bundleFromStartActivity.getString(NICKNAME);

        textViewNickname.setText(nickname);

    }

    private void initialize() {
        textViewNickname = findViewById(R.id.textViewNickname);
    }
}
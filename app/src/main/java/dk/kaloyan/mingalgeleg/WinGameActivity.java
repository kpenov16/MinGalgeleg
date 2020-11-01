package dk.kaloyan.mingalgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class WinGameActivity extends AppCompatActivity {
    private String lastScore;
    private TextView textViewResult;
    private String playerName;
    private ViewablePlayer viewablePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_game);

        Bundle bundleFromStartActivity = getIntent().getExtras();
        lastScore = bundleFromStartActivity.getString(MainActivity.LAST_SCORE);
        playerName = bundleFromStartActivity.getString(MainActivity.PLAYER_NAME);
        viewablePlayer = bundleFromStartActivity.getParcelable(ViewablePlayer.VIEWABLE_PLAYER);

        textViewResult = findViewById(R.id.textViewResult);
        textViewResult.setText(playerName + "\n" + lastScore);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.LAST_SCORE, lastScore);
        intent.putExtra(MainActivity.PLAYER_NAME, playerName);
        intent.putExtra(ViewablePlayer.VIEWABLE_PLAYER, viewablePlayer);

        setResult(Activity.RESULT_OK, intent);
        finish();
        //super.onBackPressed();
    }
}
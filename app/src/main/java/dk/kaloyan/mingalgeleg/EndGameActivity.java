package dk.kaloyan.mingalgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    private String lastScore;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Bundle bundleFromStartActivity = getIntent().getExtras();
        lastScore = bundleFromStartActivity.getString(MainActivity.LAST_SCORE);
        lastScore+="_end_game";

        textViewResult = findViewById(R.id.textViewResult);
        textViewResult.setText(lastScore);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.LAST_SCORE, lastScore+"_back_pressed");
        setResult(Activity.RESULT_OK, intent);
        finish();
        //super.onBackPressed();
    }


}
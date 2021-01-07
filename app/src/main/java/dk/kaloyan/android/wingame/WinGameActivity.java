package dk.kaloyan.android.wingame;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;

import java.util.ArrayList;
import java.util.List;

import dk.kaloyan.android.R;
import dk.kaloyan.android.ViewablePlayer;
import dk.kaloyan.android.playgame.MainActivity;

public class WinGameActivity extends AppCompatActivity {
    private String lastScore;
    private TextView textViewResult;
    private String playerName;
    private ViewablePlayer viewablePlayer;
    private CommonConfetti commonConfetti;

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

        MediaPlayer.create(this,R.raw.game_win).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(()->{
            new Handler(Looper.getMainLooper()).postDelayed(()->{
                generateConfetti();
            }, 1000);
        }).start();
    }

    // how to use the library is from the lib repo here:
    // https://github.com/jinatonic/confetti
    private void  generateConfetti(){
        ViewGroup container = findViewById(R.id.container);

        final Resources res = getResources();
        final int goldDark = res.getColor(R.color.gold_dark,null);
        final int goldMed = res.getColor(R.color.gold_med, null);
        final int gold = res.getColor(R.color.gold,null);
        final int goldLight = res.getColor(R.color.gold_light, null);
        final int[] colors = new int[] { goldDark, goldMed, gold, goldLight };

        final int size = getResources().getDimensionPixelSize(R.dimen.default_confetti_size);
        final ConfettiSource confettiSource = new ConfettiSource(-size, -size);
        commonConfetti = CommonConfetti.rainingConfetti(container, confettiSource, colors);

        final Resources resSpeed = getResources();
        final int velocitySlow = resSpeed.getDimensionPixelOffset(R.dimen.default_velocity_slow);
        final int velocityNormal = resSpeed.getDimensionPixelOffset(R.dimen.default_velocity_normal);
        final int velocityFast = resSpeed.getDimensionPixelOffset(R.dimen.default_velocity_fast);

        commonConfetti.getConfettiManager()
                .setVelocityX(velocityFast, velocityNormal)
                .setAccelerationX(-velocityNormal, velocitySlow)
                .setTargetVelocityX(0, velocitySlow / 2f)
                .setVelocityY(velocityNormal, velocitySlow);
        commonConfetti.stream(5000).animate();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.LAST_SCORE, lastScore);
        intent.putExtra(MainActivity.PLAYER_NAME, playerName);
        intent.putExtra(ViewablePlayer.VIEWABLE_PLAYER, viewablePlayer);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
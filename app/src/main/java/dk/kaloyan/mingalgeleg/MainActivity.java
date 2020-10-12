package dk.kaloyan.mingalgeleg;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dk.kaloyan.core.GameInteractorImpl;
import dk.kaloyan.fsm.HangGameFSM;
import dk.kaloyan.fsm.HangGameFSMImpl;
import dk.kaloyan.fsm.HangGameState;
import dk.kaloyan.fsm.HangGameStateBase;
import dk.kaloyan.galgeleg.Galgelogik;
import dk.kaloyan.galgeleg.MinGalgelogikImpl;

public class MainActivity extends AppCompatActivity implements GameView, View.OnClickListener{//, HangGameState {
    /*
    @Override
    public void start(HangGameFSM fsm) {}
    @Override
    public void guess(HangGameFSM fsm) {}
    @Override
    public void back(HangGameFSM fsm) {}
    */

    public static String PLAYER_NAME = "dk.kaloyan.mingalgeleg.MainActivity.PLAYER_NAME";
    public static String LAST_SCORE = "dk.kaloyan.mingalgeleg.MainActivity.LAST_SCORE";

    private InputWorkerImpl inputWorker;
    private GameViewModel viewModel;
    private TextView textViewWordToGuess;
    private ImageView imageViewHangStatus;
    private String playerName;
    private TextView textViewPlayerName;

    @Override
    public void show(GameViewModel viewModel) {

        textViewWordToGuess.setText(viewModel.currentGuess);
        textViewPlayerName.setText(viewModel.playerName);
        if(viewModel.wrongCount == 1)
            this.imageViewHangStatus.setImageResource(R.drawable.forkert1);
        else if(viewModel.wrongCount == 2)
            this.imageViewHangStatus.setImageResource(R.drawable.forkert2);
        else if(viewModel.wrongCount == 3)
            this.imageViewHangStatus.setImageResource(R.drawable.forkert3);
        else if(viewModel.wrongCount == 4)
            this.imageViewHangStatus.setImageResource(R.drawable.forkert4);
        else if(viewModel.wrongCount == 5)
            this.imageViewHangStatus.setImageResource(R.drawable.forkert5);
        else if(viewModel.wrongCount == 6){

            Intent intent = new Intent(MainActivity.this, EndGameActivity.class);
            //intent.putExtra(MainActivity.PLAYER_NAME, playerName);
            intent.putExtra(MainActivity.LAST_SCORE, viewModel.currentGuess);

            intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            startActivity(intent);
            finish();
            //startActivityForResult(intent, StartActivity.RESULT_FROM_END_GAME_ACTIVITY);
            //this.imageViewHangStatus.setImageResource(R.drawable.forkert6);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        setResult(StartActivity.RESULT_FROM_END_GAME_ACTIVITY);
        finish();
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(this, "Key: " + ((TextView)view).getText(), Toast.LENGTH_LONG).show();
        inputWorker.play(((TextView)view).getText().toString().toLowerCase());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.LAST_SCORE, viewModel.currentGuess+"_back_pressed");
        setResult(Activity.RESULT_OK, intent);

        super.onBackPressed();
    }

    private void initialize() {
        textViewPlayerName = findViewById(R.id.textViewPlayerName);
        textViewWordToGuess = findViewById(R.id.textViewWordToGuess);
        imageViewHangStatus = findViewById(R.id.imageViewHangStatus);

        $(R.id.textViewA).setOnClickListener(this);
        $(R.id.textViewB).setOnClickListener(this);
        $(R.id.textViewC).setOnClickListener(this);
        $(R.id.textViewD).setOnClickListener(this);
        $(R.id.textViewE).setOnClickListener(this);
        $(R.id.textViewF).setOnClickListener(this);
        $(R.id.textViewG).setOnClickListener(this);
        $(R.id.textViewH).setOnClickListener(this);
        $(R.id.textViewJ).setOnClickListener(this);
        $(R.id.textViewK).setOnClickListener(this);
        $(R.id.textViewL).setOnClickListener(this);
        $(R.id.textViewM).setOnClickListener(this);
        $(R.id.textViewN).setOnClickListener(this);
        $(R.id.textViewO).setOnClickListener(this);
        $(R.id.textViewP).setOnClickListener(this);
        $(R.id.textViewR).setOnClickListener(this);
        $(R.id.textViewS).setOnClickListener(this);
        $(R.id.textViewI).setOnClickListener(this);
        $(R.id.textViewV).setOnClickListener(this);
        $(R.id.textViewT).setOnClickListener(this);
        $(R.id.textViewU).setOnClickListener(this);
        $(R.id.textViewQ).setOnClickListener(this);
        $(R.id.textViewX).setOnClickListener(this);
        $(R.id.textViewY).setOnClickListener(this);
        $(R.id.textViewW).setOnClickListener(this);
        $(R.id.textViewZ).setOnClickListener(this);
        $(R.id.textViewAA).setOnClickListener(this);
        $(R.id.textViewAE).setOnClickListener(this);
        $(R.id.textViewOU).setOnClickListener(this);
    }

    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    private HangGameFSM fsm = HangGameFSMImpl.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //HangGameStateBase.PLAYING.setContext(this);

        /*
        if(savedInstanceState == null){
            Fragment mainFragmet = new MainFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayoutMainFragment, mainFragmet)
                    .commit();
        }
        */


        initialize();

        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        viewModel = viewModelProvider.get( GameViewModel.class );

        inputWorker = new InputWorkerImpl(
                new GameInteractorImpl( new OutputWorkerImpl(this, viewModel), new MinGalgelogikImpl(new Galgelogik()))
        );

        Bundle bundleFromStartActivity = getIntent().getExtras();
        playerName = bundleFromStartActivity.getString(PLAYER_NAME);


        inputWorker.setup(playerName);

        // Kommentér ind for at hente ord fra et online regneark
        /*
        try {
          spil.hentOrdFraRegneark("12");
        } catch (Exception e) {
          e.printStackTrace();
        }
        */
  }

}
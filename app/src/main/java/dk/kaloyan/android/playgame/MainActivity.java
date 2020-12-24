package dk.kaloyan.android.playgame;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dk.kaloyan.android.losegame.LoseGameActivity;
import dk.kaloyan.android.R;
import dk.kaloyan.android.ViewablePlayer;
import dk.kaloyan.android.wingame.WinGameActivity;
import dk.kaloyan.android.startgame.StartActivity;
import dk.kaloyan.app.ApplicationMain;
import dk.kaloyan.gateways.GameGatewayImpl;


public class MainActivity extends AppCompatActivity implements HangGameView, View.OnClickListener, GameGatewayImpl.SharedPreferencesSource {
    public static final String PREF_HISTORY_PLAYER = "PREF_HISTORY_%s";
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

    private HangGameInputImpl inputWorker;
    private HangGameViewModel viewModel;
    private TextView textViewWordToGuess;
    private ImageView imageViewHangStatus;
    private String playerName;
    private TextView textViewPlayerName;

    @Override
    public void show(HangGameViewModel viewModel) {
        if(viewModel.isWon){
            Intent intent = new Intent(MainActivity.this, WinGameActivity.class);
            intent.putExtra(MainActivity.LAST_SCORE, viewModel.currentGuess);
            intent.putExtra(MainActivity.PLAYER_NAME, viewModel.playerName);
            intent.putExtra(ViewablePlayer.VIEWABLE_PLAYER, viewModel.viewablePlayer);

            intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            startActivity(intent);
            finish();
        }

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

            Intent intent = new Intent(MainActivity.this, LoseGameActivity.class);
            intent.putExtra(MainActivity.LAST_SCORE, viewModel.currentGuess);
            intent.putExtra(MainActivity.PLAYER_NAME, viewModel.playerName);
            intent.putExtra(ViewablePlayer.VIEWABLE_PLAYER, viewModel.viewablePlayer);

            intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            startActivity(intent);
            finish();
            //startActivityForResult(intent, StartActivity.RESULT_FROM_END_GAME_ACTIVITY);
            //this.imageViewHangStatus.setImageResource(R.drawable.forkert6);
        }

        textViewWordToGuess.setText(viewModel.currentGuess);
        textViewPlayerName.setText(viewModel.playerName);
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
        view.setClickable(false);
        view.setEnabled(false);
        inputWorker.play(((TextView)view).getText().toString().toLowerCase());
    }

    //private HangGameFSM fsm = HangGameFSMImpl.getInstance();
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

        viewModel = viewModelProvider.get( HangGameViewModel.class );

        Bundle bundleFromStartActivity = getIntent().getExtras();
        playerName = bundleFromStartActivity.getString(PLAYER_NAME);

        ((ApplicationMain)getApplication()).gameInteractor.setOutputPort(new HangGameOutputImpl(this, viewModel));
        inputWorker = new HangGameInputImpl(((ApplicationMain)getApplication()).gameInteractor);

        ((ApplicationMain)getApplication()).gameInteractor.setGameGateway( new GameGatewayImpl(this));
        inputWorker.setup(playerName);

        /*
        inputWorker = new InputWorkerImpl(
                new GameInteractorImpl( new OutputWorkerImpl(this, viewModel), new MinGalgelogikImpl(new Galgelogik()))
        );
        */
    }

    @Override
    public SharedPreferences getSharedPreferences(String KEY) {
        return getSharedPreferences(KEY, Activity.MODE_PRIVATE);
    }

    private void initialize() {
        textViewPlayerName = findViewById(R.id.textViewPlayerName);
        textViewWordToGuess = findViewById(R.id.textViewWordToGuess);
        imageViewHangStatus = findViewById(R.id.imageViewHangStatus);

        https://stackoverflow.com/questions/21872150/android-custom-numeric-keyboard
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

}
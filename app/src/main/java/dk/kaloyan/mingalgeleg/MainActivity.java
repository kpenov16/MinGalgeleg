package dk.kaloyan.mingalgeleg;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import dk.kaloyan.core.GameInteractorImpl;
import dk.kaloyan.galgeleg.Galgelogik;
import dk.kaloyan.galgeleg.MinGalgelogikImpl;

public class MainActivity extends AppCompatActivity implements GameView, View.OnClickListener {
    public static String PLAYER_NAME = "dk.kaloyan.mingalgeleg.MainActivity.PLAYER_NAME";

    private InputWorkerImpl inputWorker;
    private GameViewModel viewModel;
    private TextView textViewWordToGuess;
    private ImageView imageViewHangStatus;
    private Button buttonRestart;
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
            this.imageViewHangStatus.setImageResource(R.drawable.forkert6);
        }

        if(viewModel.restartButton) {
            setRestartButtonVisibility(Button.VISIBLE);
        } else
            setRestartButtonVisibility(Button.INVISIBLE);
    }

    private void setRestartButtonVisibility(int visible) {
        buttonRestart.setVisibility(visible);
    }

    @Override
    public void onClick(View view) {
        if(buttonRestart.getId() == view.getId())
            restartGame();
        else {
            //Toast.makeText(this, "Key: " + ((TextView)view).getText(), Toast.LENGTH_LONG).show();
            inputWorker.play(((TextView)view).getText().toString().toLowerCase());
        }
    }
    private void restartGame() {
        inputWorker.setup(playerName);
        setRestartButtonVisibility(Button.INVISIBLE);
        viewModel.restartButton = false;
        this.imageViewHangStatus.setImageResource(R.drawable.galge);
    }


    private void initialize() {
        textViewPlayerName = findViewById(R.id.textViewPlayerName);
        textViewWordToGuess = findViewById(R.id.textViewWordToGuess);
        imageViewHangStatus = findViewById(R.id.imageViewHangStatus);

        buttonRestart = findViewById(R.id.buttonRestart);
        buttonRestart.setOnClickListener(this);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
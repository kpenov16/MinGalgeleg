package dk.kaloyan.mingalgeleg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import dk.kaloyan.core.GameInteractorImpl;
import dk.kaloyan.core.OutputPort;
import dk.kaloyan.galgeleg.Galgelogik;
import dk.kaloyan.galgeleg.MinGalgelogikImpl;

public class MainActivity extends AppCompatActivity implements GameView{
    private InputWorkerImpl inputWorker;
    private GameViewModel viewModel;
    private TextView textViewWordToGuess;

    @Override
    public void show(GameViewModel viewModel) {
        textViewWordToGuess.setText(viewModel.currentGuess);
    }

    private void initialize() {
        textViewWordToGuess = findViewById(R.id.textViewWordToGuess);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            Fragment mainFragmet = new MainFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayoutMainFragment, mainFragmet)
                    .commit();
        }

        /* //uncomment after fragment test is done
        initialize();

        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        viewModel = viewModelProvider.get( GameViewModel.class );

        inputWorker = new InputWorkerImpl(
                new GameInteractorImpl( new OutputWorkerImpl(this, viewModel), new MinGalgelogikImpl(new Galgelogik()))
        );

        inputWorker.setup();

        */
        //next step is this one TODO
        //inputWorker.play("e");



        //spil.nulstil();

        // Kommentér ind for at hente ord fra DR
        /*
        try {
            spil.hentOrdFraDr();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */


        // Kommentér ind for at hente ord fra et online regneark
        /*
        try {
          spil.hentOrdFraRegneark("12");
        } catch (Exception e) {
          e.printStackTrace();
        }
        */


        //spil.logStatus();



        //spil.gætBogstav("e");
        //spil.logStatus();

        /*
        spil.gætBogstav("a");
        spil.logStatus();
        System.out.println("" + spil.getAntalForkerteBogstaver());
        System.out.println("" + spil.getSynligtOrd());
        if (spil.erSpilletSlut()) return;

        spil.gætBogstav("i");
        spil.logStatus();
        if (spil.erSpilletSlut()) return;

        spil.gætBogstav("s");
        spil.logStatus();
        if (spil.erSpilletSlut()) return;

        spil.gætBogstav("r");
        spil.logStatus();
        if (spil.erSpilletSlut()) return;

        spil.gætBogstav("l");
        spil.logStatus();
        if (spil.erSpilletSlut()) return;

        spil.gætBogstav("b");
        spil.logStatus();
        if (spil.erSpilletSlut()) return;

        spil.gætBogstav("o");
        spil.logStatus();
        if (spil.erSpilletSlut()) return;

        spil.gætBogstav("t");
        spil.logStatus();
        if (spil.erSpilletSlut()) return;

        spil.gætBogstav("n");
        spil.logStatus();
        if (spil.erSpilletSlut()) return;

        spil.gætBogstav("m");
        spil.logStatus();
        if (spil.erSpilletSlut()) return;

        spil.gætBogstav("y");
        spil.logStatus();
        if (spil.erSpilletSlut()) return;

        spil.gætBogstav("p");
        spil.logStatus();
        if (spil.erSpilletSlut()) return;

        spil.gætBogstav("g");
        spil.logStatus();
        if (spil.erSpilletSlut()) return;

*/

    }


}
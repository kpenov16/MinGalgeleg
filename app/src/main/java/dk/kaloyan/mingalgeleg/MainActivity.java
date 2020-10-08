package dk.kaloyan.mingalgeleg;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dk.kaloyan.core.GameInteractorImpl;
import dk.kaloyan.core.OutputPort;
import dk.kaloyan.galgeleg.Galgelogik;
import dk.kaloyan.galgeleg.MinGalgelogikImpl;

public class MainActivity extends AppCompatActivity implements GameView, View.OnClickListener {
    private InputWorkerImpl inputWorker;
    private GameViewModel viewModel;
    private TextView textViewWordToGuess;
    private EditText editTextLetterToGuess;
    private ImageView imageViewHangStatus;
    private Button buttonGuess;

    @Override
    public void show(GameViewModel viewModel) {
        textViewWordToGuess.setText(viewModel.currentGuess);
    }

    @Override
    public void onClick(View view) {
        final int ID = view.getId();
        //if(buttonGuess.getId() == ID){
          //  Toast.makeText(this, "Key: " + editTextLetterToGuess.getText(), Toast.LENGTH_LONG).show();
        //}
        Toast.makeText(this, "Key: " + ((TextView)view).getText(), Toast.LENGTH_LONG).show();
        inputWorker.play(((TextView)view).getText().toString().toLowerCase());
    }

    private void initialize() {
        textViewWordToGuess = findViewById(R.id.textViewWordToGuess);
        //editTextLetterToGuess = findViewById(R.id.editTextLetterToGuess);
        //editTextLetterToGuess.setInputType(InputType.TYPE_NULL);
        imageViewHangStatus = findViewById(R.id.imageViewHangStatus);
        //buttonGuess = findViewById(R.id.buttonGuess);
        //buttonGuess.setOnClickListener(this);

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

        //uncomment after fragment test is done
        initialize();



        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        viewModel = viewModelProvider.get( GameViewModel.class );

        inputWorker = new InputWorkerImpl(
                new GameInteractorImpl( new OutputWorkerImpl(this, viewModel), new MinGalgelogikImpl(new Galgelogik()))
        );

        inputWorker.setup();


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
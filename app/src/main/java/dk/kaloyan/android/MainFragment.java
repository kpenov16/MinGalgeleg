package dk.kaloyan.android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View mainFragment;
    private TextView textViewWordToGuess;
    private ImageView imageViewHangStatus;
    private Button buttonGuess;
    private EditText editTextLetterToGuess;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainFragment = inflater.inflate(R.layout.fragment_main, container, false);
        initialize();



        // Inflate the layout for this fragment
        return mainFragment;

    }

    private void initialize() {
        textViewWordToGuess = mainFragment.findViewById(R.id.textViewWordToGuess);
        imageViewHangStatus = mainFragment.findViewById(R.id.imageViewHangStatus);
        buttonGuess = mainFragment.findViewById(R.id.buttonGuess);
        editTextLetterToGuess = mainFragment.findViewById(R.id.editTextLetterToGuess);
        editTextLetterToGuess.setInputType(0);
    }

    @Override
    public void onClick(View view) {
        final int ID = view.getId();
        if(textViewWordToGuess.getId() == ID){

        }else if(imageViewHangStatus.getId() == ID){

        }else if(buttonGuess.getId() == ID){
            Toast.makeText(getActivity(), "Guess: " + editTextLetterToGuess.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
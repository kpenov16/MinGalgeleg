package dk.kaloyan.android.showscoredetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.hanggame.entities.Game;
import dk.kaloyan.android.R;

public class ScoreDetailsRecyclerAdapter extends RecyclerView.Adapter<ScoreDetailsRecyclerAdapter.ViewHolder>{
    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<Game> games;

    public ScoreDetailsRecyclerAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.score_details_element, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = games.get(position);
        holder.textViewWord.setText("Word to guess: "+game.getWordToGuess());
        holder.textViewWrongLetters.setText("Wrong letters: " + game.getUsedLetters());
        holder.textViewGuessed.setText(game.getIsWon()?"Game Won":"Game Lost");
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected final TextView textViewWord;
        protected final TextView textViewWrongLetters;
        protected final TextView textViewGuessed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWord = itemView.findViewById(R.id.textViewWord);
            textViewWrongLetters = itemView.findViewById(R.id.textViewWrongLetters);
            textViewGuessed = itemView.findViewById(R.id.textViewGuessed);
        }
    }
}

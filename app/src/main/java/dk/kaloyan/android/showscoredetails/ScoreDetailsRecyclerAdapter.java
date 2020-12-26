package dk.kaloyan.android.showscoredetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import dk.hanggame.entities.Game;
import dk.kaloyan.android.R;

public class ScoreDetailsRecyclerAdapter extends RecyclerView.Adapter<ScoreDetailsRecyclerAdapter.ViewHolder>{
    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<Game> games;

    public ScoreDetailsRecyclerAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games.stream().sorted(Comparator.comparing(Game::getStartTimeMilliseconds).reversed()).collect(Collectors.toList());
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
        holder.textViewWrongLetters.setText("Used letters: " + game.getUsedLetters());
        holder.textViewGuessed.setText(game.getIsWon()?"Game Won":"Game Lost");
        holder.textViewGameStarted.setText("Start time: " + convertTimeWithTimeZone(game.getStartTimeMilliseconds()));
        holder.textViewGameDuration.setText("Game duration: " + formatTime(game.getElapsedTimeMilliseconds()));
    }

    //https://stackoverflow.com/questions/6782185/convert-timestamp-long-to-normal-date-format/6782571
    public String convertTimeWithTimeZone(long time){
        Calendar cal = Calendar.getInstance();
        //cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(time);
        return String.format("%d/%d/%d %d:%d",
                cal.get(Calendar.DAY_OF_MONTH), (cal.get(Calendar.MONTH) + 1), cal.get(Calendar.YEAR),
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
    }

    //https://stackoverflow.com/questions/6710094/how-to-format-an-elapsed-time-interval-in-hhmmss-sss-format-in-java
    private static String formatTime(final long duration) {
        final long hr = TimeUnit.MILLISECONDS.toHours(duration);
        final long min = TimeUnit.MILLISECONDS.toMinutes(duration - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(duration - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        final long ms = TimeUnit.MILLISECONDS.toMillis(duration - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));
        return String.format("%02d:%02d:%02d.%03d", hr, min, sec, ms);
    }
    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected final TextView textViewWord;
        protected final TextView textViewWrongLetters;
        protected final TextView textViewGuessed;
        protected final TextView textViewGameStarted;
        protected final TextView textViewGameDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWord = itemView.findViewById(R.id.textViewWord);
            textViewWrongLetters = itemView.findViewById(R.id.textViewUsedLetters);
            textViewGuessed = itemView.findViewById(R.id.textViewGuessed);
            textViewGameStarted = itemView.findViewById(R.id.textViewGameStarted);
            textViewGameDuration = itemView.findViewById(R.id.textViewGameDuration);
        }
    }
}

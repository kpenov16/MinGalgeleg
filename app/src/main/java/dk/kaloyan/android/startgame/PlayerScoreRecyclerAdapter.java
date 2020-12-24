package dk.kaloyan.android.startgame;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.kaloyan.android.R;
import dk.kaloyan.android.showscoredetails.ScoreDetailsActivity;

public class PlayerScoreRecyclerAdapter extends RecyclerView.Adapter<PlayerScoreRecyclerAdapter.ViewHolder>{
    private final Context context;
    private final LayoutInflater layoutInflater;
    private final List<ViewablePlayerScore> viewablePlayerScoreList;
    public PlayerScoreRecyclerAdapter(Context context, List<ViewablePlayerScore> viewablePlayerScoreList) {
        this.context = context;
        this.viewablePlayerScoreList = viewablePlayerScoreList;
        this.layoutInflater = LayoutInflater.from(this.context);
     }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.player_score_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewablePlayerScore viewablePlayerScore = viewablePlayerScoreList.get(position);
        holder.textViewName.setText(viewablePlayerScore.viewablePlayerName);
        holder.textViewWins.setText(viewablePlayerScore.wins);
        holder.textViewLosses.setText(viewablePlayerScore.looses);

        holder.currentPosition = position;
        holder.nickname = viewablePlayerScore.nickname;
    }

    @Override
    public int getItemCount() {
        return viewablePlayerScoreList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView textViewName;
        public final TextView textViewWins;
        public final TextView textViewLosses;

        public int currentPosition;
        public String nickname;

        public ViewHolder(@NonNull View view) {
            super(view);
            textViewName = view.findViewById(R.id.textViewName);
            textViewWins = view.findViewById(R.id.textViewWins);
            textViewLosses = view.findViewById(R.id.textViewLosses);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ScoreDetailsActivity.class);
                    intent.putExtra(ScoreDetailsActivity.NICKNAME, nickname);
                    context.startActivity(intent);
                }
            });
        }
    }
}
